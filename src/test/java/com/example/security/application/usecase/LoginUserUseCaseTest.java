package com.example.security.application.usecase;

import com.example.security.application.dto.LoginCommand;
import com.example.security.application.dto.TokenResult;
import com.example.security.domain.exception.InvalidCredentialsException;
import com.example.security.domain.model.Email;
import com.example.security.domain.model.Password;
import com.example.security.domain.model.Role;
import com.example.security.domain.model.Token;
import com.example.security.domain.model.User;
import com.example.security.domain.port.output.PasswordEncoderPort;
import com.example.security.domain.port.output.TokenGeneratorPort;
import com.example.security.domain.port.output.TokenRepositoryPort;
import com.example.security.domain.port.output.UserRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoginUserUseCaseTest {

    @ParameterizedTest(name = "login succeeds for {0}")
    @CsvFileSource(resources = "/testdata/valid-logins.csv", numLinesToSkip = 1)
    @DisplayName("debe autenticar credenciales válidas y emitir tokens")
    void shouldAuthenticateValidCredentialsAndIssueTokens(String email, String password) {
        // Arrange
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemoryTokenRepository tokenRepository = new InMemoryTokenRepository();
        FakeTokenGenerator tokenGenerator = new FakeTokenGenerator();
        FakePasswordEncoder passwordEncoder = new FakePasswordEncoder();
        LoginUserUseCase useCase = new LoginUserUseCase(userRepository, tokenRepository, tokenGenerator, passwordEncoder);

        User user = User.builder()
                .id(1L)
                .name("Test User")
                .email(Email.of(email))
                .password(Password.ofHashed("hashed:" + password))
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);

        // Act
        TokenResult result = useCase.login(new LoginCommand(email, password));

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("access-token", result.accessToken()),
                () -> assertEquals("refresh-token", result.refreshToken()),
                () -> assertEquals("Bearer", result.tokenType()),
                () -> assertEquals(3600L, result.expiresInSeconds()),
                () -> assertEquals(1, tokenRepository.revocations),
                () -> assertEquals(2, tokenRepository.savedTokens.size())
        );
    }

    @ParameterizedTest(name = "login fails for {0}")
    @CsvFileSource(resources = "/testdata/invalid-logins.csv", numLinesToSkip = 1)
    @DisplayName("debe rechazar credenciales inválidas")
    void shouldRejectInvalidCredentials(String email, String password, String expectedError) {
        // Arrange
        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        InMemoryTokenRepository tokenRepository = new InMemoryTokenRepository();
        FakeTokenGenerator tokenGenerator = new FakeTokenGenerator();
        FakePasswordEncoder passwordEncoder = new FakePasswordEncoder();
        LoginUserUseCase useCase = new LoginUserUseCase(userRepository, tokenRepository, tokenGenerator, passwordEncoder);

        User user = User.builder()
                .id(1L)
                .name("Test User")
                .email(Email.of("juan@example.com"))
                .password(Password.ofHashed("hashed:password123"))
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);

        // Act
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class,
                () -> useCase.login(new LoginCommand(email, password)));

        // Assert
        assertEquals(expectedError, exception.getMessage());
        assertEquals(0, tokenRepository.savedTokens.size());
    }

    private static class InMemoryUserRepository implements UserRepositoryPort {
        private final List<User> users = new ArrayList<>();

        @Override
        public User save(User user) {
            users.add(user);
            return user;
        }

        @Override
        public Optional<User> findByEmail(Email email) {
            return users.stream().filter(user -> user.getEmail().equals(email)).findFirst();
        }

        @Override
        public boolean existsByEmail(Email email) {
            return users.stream().anyMatch(user -> user.getEmail().equals(email));
        }
    }

    private static class InMemoryTokenRepository implements TokenRepositoryPort {
        private final List<Token> savedTokens = new ArrayList<>();
        private int revocations = 0;

        @Override
        public Token save(Token token) {
            savedTokens.add(token);
            return token;
        }

        @Override
        public List<Token> findAllValidTokensByUserId(Long userId) {
            return List.of();
        }

        @Override
        public void revokeAllUserTokens(Long userId) {
            revocations++;
        }
    }

    private static class FakeTokenGenerator implements TokenGeneratorPort {
        @Override
        public String generateAccessToken(User user) {
            return "access-token";
        }

        @Override
        public String generateRefreshToken(User user) {
            return "refresh-token";
        }

        @Override
        public long getAccessTokenExpirationSeconds() {
            return 3600L;
        }

        @Override
        public long getRefreshTokenExpirationSeconds() {
            return 7200L;
        }
    }

    private static class FakePasswordEncoder implements PasswordEncoderPort {
        @Override
        public Password encode(Password plainPassword) {
            return Password.ofHashed("hashed:" + plainPassword.getValue());
        }

        @Override
        public boolean matches(Password plainPassword, Password hashedPassword) {
            return hashedPassword.getValue().equals("hashed:" + plainPassword.getValue());
        }
    }
}
