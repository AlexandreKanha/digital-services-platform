package br.com.alexandre.digitalservices.service;

import br.com.alexandre.digitalservices.domain.User;
import br.com.alexandre.digitalservices.dto.CreateUserRequest;
import br.com.alexandre.digitalservices.dto.UserResponse;
import br.com.alexandre.digitalservices.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse create(CreateUserRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
            request.getName(),
            request.getEmail(),
            hashedPassword
        );
        User saved = repository.save(user);

        return new UserResponse(
            saved.getId(),
            saved.getName(),
            saved.getEmail()
        );
    }

    public List<UserResponse> list() {
        return repository.findAll()
            .stream()
            .map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail()))
            .toList();
    }
}
