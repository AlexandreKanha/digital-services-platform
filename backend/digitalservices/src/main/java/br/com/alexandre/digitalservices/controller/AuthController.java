package br.com.alexandre.digitalservices.controller;

import br.com.alexandre.digitalservices.dto.LoginRequest;
import br.com.alexandre.digitalservices.dto.LoginResponse;
import br.com.alexandre.digitalservices.dto.MeResponse;
import br.com.alexandre.digitalservices.service.AuthService;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

    @GetMapping("/me")
    public MeResponse me() {
        return service.me();
    }
}
