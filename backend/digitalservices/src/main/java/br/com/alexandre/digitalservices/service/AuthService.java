package br.com.alexandre.digitalservices.service;

import br.com.alexandre.digitalservices.dto.LoginRequest;
import br.com.alexandre.digitalservices.dto.LoginResponse;
import br.com.alexandre.digitalservices.dto.MeResponse;
import br.com.alexandre.digitalservices.repository.UserRepository;
import br.com.alexandre.digitalservices.security.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
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
            .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Gera o token JWT dinamicamente no login
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new LoginResponse(token);
    }

    public MeResponse me() {

        Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getPrincipal().toString();

        String role = auth.getAuthorities()
            .stream()
            .findFirst()
            .map(a -> a.getAuthority())
            .orElse("UNKNOWN");

        return new MeResponse(email, role);
    }
}
