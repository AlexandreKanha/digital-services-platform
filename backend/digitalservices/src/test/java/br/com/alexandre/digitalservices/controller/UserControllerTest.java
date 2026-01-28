package br.com.alexandre.digitalservices.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @DisplayName("Should return 401 Unauthorized when accessing /users without a token")
    @Test
    void shouldReturn403WithoutToken() {
        RestClient restClient = RestClient.create();
        
        HttpClientErrorException exception = assertThrows(
            HttpClientErrorException.class,
            () -> restClient.get()
                .uri("http://localhost:" + port + "/users")
                .retrieve()
                .body(String.class)
        );
        
assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }
}
