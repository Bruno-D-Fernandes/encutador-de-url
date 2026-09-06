package edu.encurtaUrl.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginRequest {

    @NotBlank(message = "Email cant be blank")
    private String email;

    @NotBlank(message = "Password cant be blank")
    // to do @Pattern(regexp = "[a-zA-Z!@#$-_][@][a-zA-Z]{4,6}")
    private String password;
}
