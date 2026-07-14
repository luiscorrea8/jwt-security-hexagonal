package com.example.security.application.usecase;

import com.example.security.application.dto.RegisterCommand;
import com.example.security.application.dto.TokenResult;
import com.example.security.domain.port.input.RegisterUserPort;
import com.example.security.domain.port.output.*;

public class RegisterUserUseCase implements RegisterUserPort {
    public RegisterUserUseCase(UserRepositoryPort userRepository, TokenRepositoryPort tokenRepository, TokenGeneratorPort tokenGenerator, PasswordEncoderPort passwordEncoder) {
        // provisional constructor - real implementation is responsibility of other teammate
    }

    @Override
    public TokenResult register(RegisterCommand command) {
        throw new UnsupportedOperationException("Provisional stub: register not implemented");
    }
}
