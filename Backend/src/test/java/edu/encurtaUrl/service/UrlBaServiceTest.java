package edu.encurtaUrl.service;


import edu.encurtaUrl.model.UserBa;
import edu.encurtaUrl.repository.UrlBaRepository;
import org.h2.command.dml.MergeUsing;
import org.h2.engine.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.InvalidUrlException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class UrlBaServiceTest {

    @InjectMocks
    UrlBaService urlBaService;

    @Mock
    UrlBaRepository urlBaRepository;

    private UserBa userMockado;

    @Nested
    class createShortUri{

        @BeforeEach
        void setUp() {
            userMockado = new UserBa("ASRCC-asccas-cacscas-ascasc", "Bruno", "Brun@gmail.com", "Bruno123", BigDecimal.valueOf(0L), null);
        }

        @Test
        @DisplayName("Should do the operation with success")
        void SuccesCase1(){
            String shortUri = urlBaService.createShortUri("https://www.youtube.com/", userMockado);

            assertAll(
                    ()-> assertTrue(shortUri.length() > 7),
                    () -> verify(urlBaRepository, times(1)).save(any())
            );
        }

        @Test
        @DisplayName("Should throw invalid exception")
        void ThrowCase1(){
            assertThrows(InvalidUrlException.class, () ->{
                urlBaService.createShortUri("https:////", userMockado);
            });
        }

    }




}
