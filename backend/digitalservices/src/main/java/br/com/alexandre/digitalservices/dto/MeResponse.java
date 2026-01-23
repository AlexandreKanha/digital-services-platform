package br.com.alexandre.digitalservices.dto;

public class MeResponse {

    private String email;
    private String role;

    public MeResponse(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
