package edu.encurtaUrl.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterRequestDto {

    @NotBlank(message = "Name cant be blank")
    @Size(min = 5, max = 70, message = "Invalid size")
    private String name;

    @NotBlank(message = "Email cant be blank")
    private String email;

    @NotBlank(message = "Password cant be blank")
    // to do @Pattern(regexp = "[a-zA-Z!@#$-_][@][a-zA-Z]{4,6}")
    private String password;
}
