package br.com.alexandre.digitalservices.controller;

import br.com.alexandre.digitalservices.domain.User;
import br.com.alexandre.digitalservices.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Listar todos usuários
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Criar novo usuário
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
