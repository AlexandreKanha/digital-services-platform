package br.com.alexandre.digitalservices.controller;

import br.com.alexandre.digitalservices.dto.LoginRequest;
import br.com.alexandre.digitalservices.dto.LoginResponse;
import br.com.alexandre.digitalservices.dto.MeResponse;
import br.com.alexandre.digitalservices.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Authentication endpoints")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
        System.out.println(">>> AUTH CONTROLLER CARREGADO <<<");
    }

    @Operation(summary = "Login and generate JWT token")
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }

    @Operation(summary = "Get current authenticated user info")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public MeResponse me() {
        return service.me();
    }
}
