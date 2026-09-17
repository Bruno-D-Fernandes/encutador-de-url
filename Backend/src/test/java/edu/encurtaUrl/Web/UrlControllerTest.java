package edu.encurtaUrl.web;


import edu.encurtaUrl.controller.UrlController;
import edu.encurtaUrl.service.UrlBaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@WebMvcTest
public class UrlControllerTest {


    @MockitoBean
    private UrlBaService urlBaService;

    @InjectMocks
    private UrlController urlController;

    @Nested
    public class createShortUriTests {

        @BeforeEach
        void setup(){


        }


    }



}
