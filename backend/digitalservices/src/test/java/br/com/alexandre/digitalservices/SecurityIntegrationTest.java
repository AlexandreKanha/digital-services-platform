package br.com.alexandre.digitalservices;

import br.com.alexandre.digitalservices.security.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Test
    @DisplayName("Deve retornar 401 quando acessar sem token")
    public void getUsers_withoutToken_returns401() throws Exception {
        mockMvc.perform(get("/users"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Deve retornar 403 quando o usuário não for ADMIN")
    public void getUsers_withNonAdminToken_returns403() throws Exception {
        // Verifique se o seu jwtService exige o prefixo "ROLE_" ou se ele adiciona automaticamente
        String token = jwtService.generateToken("user@example.com", "USER");

        mockMvc.perform(get("/users")
               .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
               .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Deve retornar 200 quando o usuário for ADMIN")
    public void getUsers_withAdminToken_returns200() throws Exception {
        String token = jwtService.generateToken("admin@example.com", "ROLE_ADMIN");

        mockMvc.perform(get("/users")
               .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
               .andExpect(status().isOk());
    }
}