package edu.encurtaUrl.web;

import edu.encurtaUrl.controller.AuthController;
import edu.encurtaUrl.dto.request.RegisterRequestDto;
import edu.encurtaUrl.infra.security.JwtService;
import edu.encurtaUrl.infra.security.WebSecurityConfig;
import edu.encurtaUrl.repository.UserBaRepository;
import edu.encurtaUrl.service.AuthService;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

    @Autowired
    private ObjectMapper objectMapper;

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

    @Nested
    class loginTestes{

        @Test
        @DisplayName("Must receive a JWT token")
        void loginSuccess() throws Exception {

            when(authService.login(any())).thenReturn("um-token-fake-qualquer");

            String contentAsString = mockMvc.perform(
                            post("/auth/login")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                    {
                    "email":"Bruno@gmail.com",
                    "password":"Bruno2310"
                    }
                    """)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            Map<String, String> hashMap = objectMapper.readValue(contentAsString, Map.class);
            String token = hashMap.get("token:");

            Assertions.assertFalse(token.isBlank());
        }

    }
}