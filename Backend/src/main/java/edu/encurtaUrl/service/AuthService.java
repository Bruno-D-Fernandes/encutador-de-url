package edu.encurtaUrl.service;

import edu.encurtaUrl.dto.request.LoginRequest;
import edu.encurtaUrl.dto.request.RegisterRequest;
import edu.encurtaUrl.infra.security.JwtService;
import edu.encurtaUrl.model.UserBa;
import edu.encurtaUrl.repository.UserBaRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {

    private UserBaRepository userBaRepository;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public AuthService(UserBaRepository userBaRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userBaRepository = userBaRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public String login(LoginRequest loginRequest){
        Authentication user = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        authenticationManager.authenticate(user);

        String token = jwtService.createToken(loginRequest.getEmail());
        return token;
    }


    @Transactional
    public void register(RegisterRequest registerRequest){
        // do validation above
        UserBa userBa = new UserBa(
                registerRequest.getName(), registerRequest.getEmail(), new BCryptPasswordEncoder().encode(registerRequest.getPassword()), BigDecimal.valueOf(0L)
        );

        userBaRepository.save(userBa);
    }


}
