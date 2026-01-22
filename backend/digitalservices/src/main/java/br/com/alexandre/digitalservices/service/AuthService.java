package br.com.alexandre.digitalservices.service;

import br.com.alexandre.digitalservices.dto.LoginRequest;
import br.com.alexandre.digitalservices.dto.LoginResponse;
import br.com.alexandre.digitalservices.repository.UserRepository;
import br.com.alexandre.digitalservices.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        var user = repository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuario ou senha invalidos"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Usuario ou senha invalidos");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new LoginResponse(token);
    }
}
