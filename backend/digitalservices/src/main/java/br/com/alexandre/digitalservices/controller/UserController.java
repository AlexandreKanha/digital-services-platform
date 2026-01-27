package br.com.alexandre.digitalservices.controller;

import br.com.alexandre.digitalservices.dto.CreateUserRequest;
import br.com.alexandre.digitalservices.dto.UserResponse;
import br.com.alexandre.digitalservices.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "User management endpoints")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
        System.out.println(">>> USER CONTROLLER CARREGADO <<<");
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        return service.create(request);
    }

    @Operation(summary = "List all users (ADMIN only)")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public List<UserResponse> list() {
        return service.list();
    }
}
