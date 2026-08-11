package edu.encurtaUrl.infra.security;

import edu.encurtaUrl.model.UserBa;
import edu.encurtaUrl.repository.UserBaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserBaRepository userBaRepository;

    @Autowired
    public JwtFilter(JwtService jwtService, UserBaRepository userBaRepository) {
        this.jwtService = jwtService;
        this.userBaRepository = userBaRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = getTokenFromAuthorization(authorization);

        if(token != null){
            String email = jwtService.decodeSubjectToken(token);
            UserBa userByEmail = userBaRepository.findByEmail(email);

            Authentication authentication = new UsernamePasswordAuthenticationToken(userByEmail, null, userByEmail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request,response);
    }

    public String getTokenFromAuthorization(String authorization){
        if(authorization.length() < 7 || null == null) return null;

        String token = authorization.replace("Bearer: ", "");
        return token;
    }

}
