package uz.work.worldcamp.service.userService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.*;
import uz.work.worldcamp.dtos.responceDto.JwtResponse;
import uz.work.worldcamp.dtos.responceDto.UserResponseDTO;
import uz.work.worldcamp.entities.UserEntity;
import uz.work.worldcamp.entities.UserPassword;
import uz.work.worldcamp.enums.SmsType;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.PasswordRepository;
import uz.work.worldcamp.repositories.UserRepository;
import uz.work.worldcamp.service.MailService;
import uz.work.worldcamp.service.PasswordService;
import uz.work.worldcamp.service.SmsApiService;
import uz.work.worldcamp.service.jwt.JwtService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService{
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PasswordRepository passwordRepository;
    private final SmsApiService smsService;

    @Override
    public UserResponseDTO signUp(UserCreateDTO userCreateDTO)  {
        if ((userCreateDTO.getEmail() == null || userCreateDTO.getEmail().isEmpty()) &&
                (userCreateDTO.getPhoneNumber() == null || userCreateDTO.getPhoneNumber().isEmpty())) {
            try {
                throw new BadRequestException("Either email or phone number must be provided");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }

        UserEntity user = new UserEntity();
        user.setPhoneNumber(userCreateDTO.getPhoneNumber());
        user.setEmail(userCreateDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setIsActive(false); // User must verify to become active
        UserEntity savedUser = userRepository.save(user);

        // Send verification code
        sendVerificationCode(savedUser);

        return mapToResponseDTO(savedUser);
    }
    @Override
    public JwtResponse signIn(LoginDto loginDto) {
        UserEntity userEntity = userRepository.findByEmailOrPhoneNumber(loginDto.getEmail(), loginDto.getPhoneNumber())
                .orElseThrow(() -> new DataNotFoundException("User not found with provided credentials"));

        if (userEntity.getIsActive()) {
            if (passwordEncoder.matches(loginDto.getPassword(), userEntity.getPassword())) {
                return new JwtResponse(jwtService.generateAccessToken(userEntity), jwtService.generateRefreshToken(userEntity));
            }
            throw new AuthenticationCredentialsNotFoundException("Password didn't match");
        }
        throw new AuthenticationCredentialsNotFoundException("Account not verified");
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAllByIsActiveTrue().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteUser(UUID userId) {
        return null;
    }

    @Override
    public String getAccessToken(String refreshToken, UUID userId) {
        try{

            Jws<Claims> claimsJws = jwtService.extractToken(refreshToken);
            Claims claims = claimsJws.getBody();
            String subject = claims.getSubject();

            UserEntity userEntity = userRepository.findById(UUID.fromString(subject))
                    .orElseThrow(() -> new DataNotFoundException("User not found with id: " + userId));
            return jwtService.generateAccessToken(userEntity);

        }catch (ExpiredJwtException e) {
            throw new AuthenticationCredentialsNotFoundException("Token expired");
        }
    }
    @Override
    public String forgetPassword(ForgetDto forgetDto) {
        return null;
    }

    @Override
    public UserResponseDTO getById(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponseDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserCreateDTO userCreateDTO) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        user.setAvatar(userCreateDTO.getAvatar());
        user.setFullName(userCreateDTO.getFullName());
        user.setEmail(userCreateDTO.getEmail());
        user.setPassword(userCreateDTO.getPassword());

        UserEntity updatedUser = userRepository.save(user);
        return mapToResponseDTO(updatedUser);
    }


    @Override
    public String getVerificationCode(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found with email: " + email));
        emailSend(user);
        return "Verify code sent";
    }

    @Override
    public void emailSend(UserEntity userEntity) {
        if(!userEntity.getIsActive()) {
            throw new AuthenticationCredentialsNotFoundException("User is not active");
        }
        String generatedString = RandomStringUtils.randomAlphanumeric(5);
        System.err.println("generatedString = " + generatedString);
        MailDto mailDto = new MailDto(generatedString, userEntity.getEmail());
        mailService.sendMail(mailDto);
        passwordRepository.save(new UserPassword(generatedString,userEntity, LocalDateTime.now(),3));
    }



    @Override
    public UserResponseDTO verify(VerifyDto verifyDto) {
        UserEntity userEntity = userRepository.findByEmailOrPhoneNumber(verifyDto.getEmail(), verifyDto.getPhoneNumber())
                .orElseThrow(() -> new DataNotFoundException("User not found with provided email or phone number"));
        UserPassword passwords = passwordRepository.getUserPasswordById(userEntity.getId(), verifyDto.getCode())
                .orElseThrow(() -> new DataNotFoundException("Code is not found"));
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime sentDate = passwords.getSentDate();
        Duration duration = Duration.between(sentDate, currentTime);
        long minutes = duration.toMinutes();
        if (minutes <= passwords.getExpiry()) {
            userEntity.setIsActive(true);
            userRepository.save(userEntity);
            return mapToResponseDTO(userEntity);
        }
        throw new AuthenticationCredentialsNotFoundException("Code is expired");
    }


    @Override
    public void smsSend(UserEntity userEntity) {
        if (!userEntity.getIsActive()) {
            throw new AuthenticationCredentialsNotFoundException("User is not active");
        }
        String generatedString = RandomStringUtils.randomAlphanumeric(5);
        SmsDto smsDto = new SmsDto(generatedString, userEntity.getPhoneNumber());
        /// shuyerda registration deganim togrmi ?
        smsService.sendMessage(smsDto, SmsType.REGISTRATION, userEntity.getId());
        passwordRepository.save(new UserPassword(generatedString, userEntity, LocalDateTime.now(), 3));
    }

    private void sendVerificationCode(UserEntity user) {
        if (user.getEmail() != null) {
            emailSend(user);
        } else if (user.getPhoneNumber() != null) {
            smsSend(user);
        }
    }

    private UserResponseDTO mapToResponseDTO(UserEntity user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setPhoneNumber(user.getPhoneNumber());
        userResponseDTO.setEmail(user.getEmail());
        return userResponseDTO;
    }

}
