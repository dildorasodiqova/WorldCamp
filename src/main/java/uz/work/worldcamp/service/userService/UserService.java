package uz.work.worldcamp.service.userService;

import org.apache.coyote.BadRequestException;
import uz.work.worldcamp.dtos.createDto.ForgetDto;
import uz.work.worldcamp.dtos.createDto.LoginDto;
import uz.work.worldcamp.dtos.createDto.UserCreateDTO;
import uz.work.worldcamp.dtos.createDto.VerifyDto;
import uz.work.worldcamp.dtos.responceDto.JwtResponse;
import uz.work.worldcamp.dtos.responceDto.UserResponseDTO;
import uz.work.worldcamp.entities.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    void emailSend(UserEntity userEntity);

    UserResponseDTO signUp(UserCreateDTO dto) throws BadRequestException;
    JwtResponse signIn(LoginDto loginDto);

    String getVerificationCode(String email);

    UserResponseDTO verify(VerifyDto verifyDto);
    void smsSend(UserEntity userEntity);

    UserResponseDTO updateUser(UUID id, UserCreateDTO userCreateDTO);
    UserResponseDTO getById(UUID userId);

    List<UserResponseDTO> getAllUsers();

    String deleteUser(UUID userId);


    String getAccessToken(String refreshToken, UUID userId);

    String forgetPassword(ForgetDto forgetDto);

}
