package uz.work.worldcamp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.repositories.PasswordRepository;

@Service
@RequiredArgsConstructor
public class PasswordService {
    private final PasswordRepository passwordRepository;
}
