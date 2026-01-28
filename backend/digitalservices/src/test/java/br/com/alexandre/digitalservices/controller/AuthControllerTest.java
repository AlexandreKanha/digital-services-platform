package br.com.alexandre.digitalservices.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.com.alexandre.digitalservices.domain.User;
import br.com.alexandre.digitalservices.repository.UserRepository;
import br.com.alexandre.digitalservices.security.Role;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
class AuthControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;


  @BeforeEach
  @SuppressWarnings("null")
  void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }

  @AfterEach
  void cleanup() {
    userRepository.deleteAll();
  }

  @DisplayName("Should login successfully and return a valid token")
  @Test
  @SuppressWarnings("null")
  void shouldLoginAndReturnToken() throws Exception {


        // persist test user so authentication succeeds
        User user = new User(
            "Alexandre",
            "alexandre@email.com",
            passwordEncoder.encode("123456"),
            Role.ROLE_ADMIN
        );
        userRepository.save(user);

        Map<String, String> body = Map.of(
          "email", "alexandre@email.com",
          "password", "123456"
        );
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.token").exists());
  }
}
