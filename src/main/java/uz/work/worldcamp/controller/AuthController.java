package uz.work.worldcamp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.work.worldcamp.dtos.createDto.ForgetDto;
import uz.work.worldcamp.dtos.createDto.LoginDto;
import uz.work.worldcamp.dtos.createDto.UserCreateDTO;
import uz.work.worldcamp.dtos.createDto.VerifyDto;
import uz.work.worldcamp.dtos.responceDto.JwtResponse;
import uz.work.worldcamp.dtos.responceDto.UserResponseDTO;
import uz.work.worldcamp.service.userService.UserService;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag( name = "Auth Management")
@SecurityRequirement( name = "Bearer Authentication")
public class AuthController {
    private final UserService userService;


    @PostMapping("/access-token")
    public String getAccessToken(@RequestBody String refreshToken, Principal principal) {
        return userService.getAccessToken(refreshToken, UUID.fromString(principal.getName()));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetDto forgetDto) {
        return ResponseEntity.ok(userService.forgetPassword(forgetDto));
    }

    @PermitAll
    @PostMapping("/sign-up")
    public UserResponseDTO signUp(@Valid @RequestBody UserCreateDTO dto) {
        return userService.signUp(dto);
    }

    @PermitAll
    @PostMapping("/sign-in")
    public JwtResponse signIn(@RequestBody LoginDto loginDto) {
        return userService.signIn(loginDto);
    }

    @PermitAll
    @PostMapping("/get-verification-code")
    public String sendVerifyCode(@RequestBody String email) {
        return userService.getVerificationCode(email);
    }

    @Operation(
            description = "This API is used for verifying",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @PermitAll
    @PostMapping("/verify")
    public UserResponseDTO verify(@RequestBody VerifyDto verifyDto) {
        return userService.verify(verifyDto);
    }



}
