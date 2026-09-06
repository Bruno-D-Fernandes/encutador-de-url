package edu.encurtaUrl.web;

import edu.encurtaUrl.controller.AuthController;
import edu.encurtaUrl.dto.request.RegisterRequestDto;
import edu.encurtaUrl.infra.security.JwtService;
import edu.encurtaUrl.infra.security.WebSecurityConfig;
import edu.encurtaUrl.repository.UserBaRepository;
import edu.encurtaUrl.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@Import(WebSecurityConfig.class)
public class AuthControllerTest {

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserBaRepository userBaRepository;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class registerTests {

        private RegisterRequestDto registerRequestDtoMockado;

//        @BeforeEach
//        void setup() {
//            registerRequestDtoMockado = new RegisterRequestDto(
//                    "Bruno",
//                    "Bruno@gmail.com",
//                    "Bruno2310"
//            );
//        }

        @Test
        @DisplayName("Must register successfully")
        void successfulRegister() throws Exception {
            doNothing().when(authService).register(any(RegisterRequestDto.class));

            mockMvc.perform(
                    post("/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    """
                                    {"name":"Bruno",
                                    "email":"Bruno@gmail.com",
                                    "password":"Bruno2310"}
                                    """
                            )
            ).andExpect(status().isOk());
        }
    }
}