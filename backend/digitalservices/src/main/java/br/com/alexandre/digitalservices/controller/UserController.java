package br.com.alexandre.digitalservices.controller;

import br.com.alexandre.digitalservices.dto.CreateUserRequest;
import br.com.alexandre.digitalservices.dto.UserResponse;
import br.com.alexandre.digitalservices.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponse create(@RequestBody CreateUserRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<UserResponse> list() {
        return service.list();
    }
}
