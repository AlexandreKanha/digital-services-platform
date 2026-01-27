package br.com.alexandre.digitalservices.controller;

import br.com.alexandre.digitalservices.domain.User;
import br.com.alexandre.digitalservices.dto.LoginRequest;
import br.com.alexandre.digitalservices.dto.LoginResponse;
import br.com.alexandre.digitalservices.dto.MeResponse;
import br.com.alexandre.digitalservices.repository.UserRepository;
import br.com.alexandre.digitalservices.security.JwtService;
import br.com.alexandre.digitalservices.security.Role;
import br.com.alexandre.digitalservices.service.AuthService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder encoder;

    @Mock
    JwtService jwtService;

    @InjectMocks
    AuthService authService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        var request = new LoginRequest();
        request.setEmail("missing@example.com");
        request.setPassword("pwd");

        assertThrows(BadCredentialsException.class, () -> authService.login(request));
        verify(repository).findByEmail("missing@example.com");
    }

    @Test
    void shouldThrowWhenPasswordDoesNotMatch() {
        var user = new User("Name", "user@example.com", "encoded", Role.ROLE_USER);
        when(repository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(encoder.matches(anyString(), anyString())).thenReturn(false);

        var request = new LoginRequest();
        request.setEmail("user@example.com");
        request.setPassword("wrong");

        assertThrows(BadCredentialsException.class, () -> authService.login(request));
        verify(encoder).matches("wrong", "encoded");
    }

    @Test
    void shouldReturnTokenWhenCredentialsValid() {
        var user = new User("Name", "user@example.com", "encoded", Role.ROLE_ADMIN);
        when(repository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(encoder.matches("secret", "encoded")).thenReturn(true);
        when(jwtService.generateToken(user.getEmail(), user.getRole().name())).thenReturn("tok-123");

        var request = new LoginRequest();
        request.setEmail("user@example.com");
        request.setPassword("secret");

        LoginResponse resp = authService.login(request);
        assertNotNull(resp);
        assertEquals("tok-123", resp.getToken());
    }

    @Test
    void shouldReturnMeResponseFromSecurityContext() {
        Authentication auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn("me@example.com");
        doReturn(List.<org.springframework.security.core.GrantedAuthority>of(new SimpleGrantedAuthority("ROLE_USER")))
            .when(auth).getAuthorities();

        SecurityContext ctx = mock(SecurityContext.class);
        when(ctx.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(ctx);

        MeResponse me = authService.me();
        assertEquals("me@example.com", me.getEmail());
        assertEquals("ROLE_USER", me.getRole());
    }
}
