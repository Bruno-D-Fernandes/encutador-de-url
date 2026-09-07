package edu.encurtaUrl.integration;


import edu.encurtaUrl.controller.UrlController;
import edu.encurtaUrl.model.UserBa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.util.HashMap;

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

    @Nested
    class noFilter{

        @Test
        @DisplayName("Must generate a short url successfully")
        void createShortUriTest () throws Exception {
            UserBa userBa = new UserBa("Bruno", "Bruno@gmail.com", "Bruno2310", BigDecimal.TEN);

            String contentAsString = mockMvc.perform(
                            post("/encUrl")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(
                                            """
                                                    {
                                                    "originalUri":"www.google.com"
                                                    }
                                                    """
                                    )
                                    .with(SecurityMockMvcRequestPostProcessors.user(userBa))
                    ).andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            HashMap<String, Object> o = objectMapper.readValue(contentAsString, HashMap.class);

            Assertions.assertAll(
                    () -> Assertions.assertFalse(o.isEmpty()),
                    () -> Assertions.assertTrue(o.containsKey("Shortened URL")),
                    () -> Assertions.assertFalse(o.get("Shortened URL").toString().isBlank())
            );


        }
    }



}
