package edu.encurtaUrl.web;

import edu.encurtaUrl.service.FileService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(FileController.class)
@AutoConfigureMockMvc
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FileService fileService;

//    @BeforeEach
//    void setUp() {
//
//    }

    @Nested
    class createShortUrl{

        @Test
        void successCase1() {


            mockMvc.perform(
                    post("shortFile")
                            .contentType(MediaType.IMAGE_JPEG)
                            .content()
            )

        }
    }

    @Nested
    class redirectMe{

    }
}
