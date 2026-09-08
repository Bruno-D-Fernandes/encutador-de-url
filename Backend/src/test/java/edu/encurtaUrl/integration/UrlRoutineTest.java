package edu.encurtaUrl.integration;


import edu.encurtaUrl.controller.UrlController;
import edu.encurtaUrl.model.UserBa;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UrlRoutineTest {

    @Autowired
    private UrlController urlController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private RestTemplate restTemplate;


    private UserBa userBa;

    @BeforeEach
    void setUp() {
        userBa = new UserBa("Bruno", "Bruno@gmail.com", "Bruno2310", BigDecimal.TEN);
    }

    @Nested
    class Mock{

        private record ShortUrl(String ShortenedURL){};

        ShortUrl wrapperShortUrl(String json){
            return objectMapper.readValue(json, ShortUrl.class);
        }

        @Test
        @DisplayName("Must generate a short url successfully")
        void createShortUriTest () throws Exception{
            // todo Esse mockMvc aqui é temp, a ideia desse @Nested é eu usar o RestTemplate
            String json = mockMvc.perform(
                            post("/encUrl")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(Map.of("originalUri", "www.google.com")))
                                    .with(SecurityMockMvcRequestPostProcessors.user(userBa))
                    ).andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            ShortUrl shortUrl = wrapperShortUrl(json);

            Assertions.assertFalse(shortUrl.ShortenedURL().isBlank());
        }
    }

    @Nested
    class ResTemp{

        @Test
        @DisplayName("Must throw an invalid url exception")
        void mustThrowAnInvalidUrlException() throws Exception{

        }

    }



}
