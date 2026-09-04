package edu.encurtaUrl.infra.security;

import edu.encurtaUrl.repository.UserBaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtFilter jwtFilter;

    public WebSecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    // Fazer 3 security filter

    // Um com jwt Cookie
    // Um com jwt Authorization
    // Um com HttpSession

    // estudar a arquitetura do spring boot, aquela questão de proxy
    // e consolidar mais conhecimento

    // adicionar auth por oAuth2

    @Bean
    // @Order (1)
                                                        // Builder
    public SecurityFilterChain filterChainAuthorizarion(HttpSecurity http) throws Exception{
        return http

            .securityMatcher("/authorization/**")

        //  estudar como csrf funciona
            .csrf((csrf) -> csrf.disable())
                //  estudar como cors funciona
            .cors(Customizer.withDefaults())
                // desativando o manager de Sessões
                // estudar como esse parceiro funciona
            .sessionManagement((sessionManager) ->
                    sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests((auth) ->
                    auth
                            .requestMatchers("/auth/login").permitAll()
                            .requestMatchers("/auth/register").permitAll()
                            .anyRequest().authenticated()
                    )
                .build();

    }

    // Builder
//    public SecurityFilterChain filterChain(HttpSecurity http){
//        http
//
//                .securityMatcher("/cookie/**")
//                .csrf(Customizer.withDefaults())
//                .
//
//    }
//

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
