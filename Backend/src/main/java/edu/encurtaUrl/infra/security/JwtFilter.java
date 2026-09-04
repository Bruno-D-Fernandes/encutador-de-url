package edu.encurtaUrl.infra.security;

import edu.encurtaUrl.model.UserBa;
import edu.encurtaUrl.repository.UserBaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserBaRepository userBaRepository;

    @Autowired
    public JwtFilter(JwtService jwtService, UserBaRepository userBaRepository) {
        this.jwtService = jwtService;
        this.userBaRepository = userBaRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = getTokenFromAuthorization(authorization);

        if (!token.isEmpty()) {
            String email = jwtService.decodeSubjectToken(token);
            if (!email.isEmpty()) {
                UserBa userByEmail = userBaRepository.findByEmail(email);
                if (userByEmail != null) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userByEmail, null, userByEmail.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    public String getTokenFromAuthorization(String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return "";
        }

        return authorization.substring(7).trim();
    }

}
