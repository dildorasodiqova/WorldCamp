package uz.work.worldcamp.service;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import uz.work.worldcamp.dtos.createDto.SmsDto;
import uz.work.worldcamp.dtos.createDto.VerifyDto;
import uz.work.worldcamp.dtos.responceDto.ApiResponse;
import uz.work.worldcamp.dtos.responceDto.UserResponseDTO;
import uz.work.worldcamp.entities.SmsHistoryEntity;
import uz.work.worldcamp.entities.SmsTokenEntity;
import uz.work.worldcamp.entities.UserEntity;
import uz.work.worldcamp.enums.SmsStatus;
import uz.work.worldcamp.enums.SmsType;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.SmsHistoryRepository;
import uz.work.worldcamp.repositories.SmsStatusRepository;
import uz.work.worldcamp.repositories.SmsTokenRepository;
import uz.work.worldcamp.repositories.UserRepository;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
public class SmsApiService {
    private final RestTemplate restTemplate;
    private final SmsTokenRepository smsTokenRepository;
    private final SmsHistoryRepository smsHistoryRepository;
    private final UserRepository userRepository;

    @Value("${my.eskiz.uz.email}")
    private String myEskizUzEmail;

    @Value("${my.eskiz.uz.password}")
    private String myEskizUzPassword;

    public SmsApiService(@Qualifier("eskizRestTemplate") RestTemplate restTemplate,
                         SmsTokenRepository smsTokenRepository,
                         SmsHistoryRepository smsHistoryRepository,
                         SmsStatusRepository smsStatusRepository,
                         UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.smsTokenRepository = smsTokenRepository;
        this.smsHistoryRepository = smsHistoryRepository;
        this.userRepository = userRepository;
    }

    public ApiResponse<?> sendMessage(SmsDto smsDto, SmsType type, UUID clientLogin) {

        SmsHistoryEntity build = SmsHistoryEntity
                .builder()
                .message(smsDto.getMessage())
                .type(type)
                .smsCount(0)
                .status(SmsStatus.SEND)
                .phone(smsDto.getPhoneNumber())
                .clientLogin(clientLogin)
                .build();
        SmsHistoryEntity smsHistory = smsHistoryRepository.save(build);

        sendSmsHTTP(smsHistory);
        return ApiResponse.success();

    }

    public UserResponseDTO verifyCode(VerifyDto verifyDto){
        Optional<SmsHistoryEntity> desc = smsHistoryRepository.findTop1ByPhoneAndStatusOrderByCreatedDateDesc(verifyDto.getPhoneNumber(), SmsStatus.SEND);
        UserEntity user = null;
        if (desc.isPresent()){
           SmsHistoryEntity entity = desc.get();
           if (Objects.equals(entity.getMessage(), verifyDto.getCode())){
               UUID userId = entity.getClientLogin();
               user = userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found !"));
               user.setIsActive(true);
               userRepository.save(user);

               entity.setStatus(SmsStatus.IS_USED);
               smsHistoryRepository.save(entity);
           }
       }
        assert user != null;
        return new UserResponseDTO(user.getId(),user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getAvatar(), user.getEducationLevel(),user.getCreatedDate(), user.getUpdateDate());
    }

    private void sendSmsHTTP(SmsHistoryEntity smsHistory) {
        String token = "Bearer " + getSmsToken();
        String prPhone = smsHistory.getPhone();

        if (prPhone.startsWith("+")) {
            prPhone = prPhone.substring(1);
        }

        MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("mobile_phone", prPhone);
        bodyMap.add("message", smsHistory.getMessage());
        bodyMap.add("from", "4546");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(bodyMap, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "/api/message/sms/send",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                System.out.println(responseEntity.getBody());
                smsHistoryRepository.updateMessageId(smsHistory.getId(), responseEntity.getBody());
            } else {
                System.out.println(responseEntity.getBody());
                log.info("Sms Token Exception");
                throw new RuntimeException();
            }
        } catch (Exception e) {
            log.error("Send sms error = {}", e.getMessage());

        }
    }

    private String getSmsToken() {
        Optional<SmsTokenEntity> smsToken = smsTokenRepository.findByEmail(myEskizUzEmail);
        if (smsToken.isEmpty()) {
            String token = myEskizUzLogin();
            SmsTokenEntity smsEntity = new SmsTokenEntity();
            smsEntity.setEmail(myEskizUzEmail);
            smsEntity.setCreatedDate(LocalDate.now());
            smsEntity.setToken(token);
            smsTokenRepository.save(smsEntity);
            return token;
        }

        if (smsToken.get().getCreatedDate().plusDays(25).isAfter(LocalDate.now())) {
            return smsToken.get().getToken();
        } else { // Refresh TOKEN
            String token = "Bearer " + smsToken.get().getToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            headers.setContentType(MediaType.TEXT_PLAIN);

            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

            try {
                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        "/api/auth/refresh",
                        HttpMethod.PATCH,
                        requestEntity,
                        String.class
                );

                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    // Token refreshed successfully, you can return the updated token if needed.
                    SmsTokenEntity smsTokenEntity = smsToken.get();
                    smsTokenEntity.setCreatedDate(LocalDate.now().plusDays(25));
                    smsTokenRepository.save(smsTokenEntity);
                    return smsToken.get().getToken();
                } else if (responseEntity.getStatusCode().is4xxClientError()) {
                    if (responseEntity.getStatusCode().value() == 401) {
                        SmsTokenEntity smsTokenEntity = refreshToken();
                        return smsTokenEntity.getToken();
                    }
                    log.info("Sms Token Exception");
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                return refreshToken().getToken();
            }
        }
        return "";
    }

    public SmsTokenEntity refreshToken() {
        Optional<SmsTokenEntity> smsToken = smsTokenRepository.findByEmail(myEskizUzEmail);

        String token = myEskizUzLogin();
        SmsTokenEntity smsEntity = new SmsTokenEntity();
        smsEntity.setEmail(myEskizUzEmail);
        smsEntity.setCreatedDate(LocalDate.now());
        smsEntity.setToken(token);

        smsToken.ifPresent(smsTokenRepository::delete);

        return smsTokenRepository.save(smsEntity);
    }

    public String myEskizUzLogin() {
        MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("email", myEskizUzEmail);
        bodyMap.add("password", myEskizUzPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(bodyMap, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "https://notify.eskiz.uz/api/auth/login",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String responseBody = responseEntity.getBody();
                JSONObject object = new JSONObject(responseBody);
                JSONObject data = object.getJSONObject("data");
                Object token = data.get("token");
                return token.toString();
            } else {
                log.info("Sms Token Exception");
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }
}


