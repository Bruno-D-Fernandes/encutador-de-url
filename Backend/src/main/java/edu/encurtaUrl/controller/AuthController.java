package edu.encurtaUrl.controller;

import edu.encurtaUrl.dto.request.LoginRequest;
import edu.encurtaUrl.dto.request.RegisterRequest;
import edu.encurtaUrl.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest){
        authService.login(loginRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest){
        authService.register(registerRequest);

        return ResponseEntity.ok().build();
    }

}
