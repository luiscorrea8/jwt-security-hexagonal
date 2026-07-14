package com.example.security.infrastructure.adapter.input.rest;

import com.example.security.application.dto.RegisterCommand;
import com.example.security.application.dto.TokenResult;
import com.example.security.domain.port.input.RegisterUserPort;
import com.example.security.infrastructure.adapter.input.rest.dto.RegisterRequestDto;
import com.example.security.infrastructure.adapter.input.rest.dto.TokenResponseDto;
import com.example.security.infrastructure.adapter.input.rest.mapper.AuthMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegisterUserPort registerUserPort;

    public AuthController(RegisterUserPort registerUserPort) {
        this.registerUserPort = registerUserPort;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        RegisterCommand command = AuthMapper.toCommand(request);
        TokenResult result = registerUserPort.register(command);
        TokenResponseDto response = AuthMapper.toResponseDto(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}