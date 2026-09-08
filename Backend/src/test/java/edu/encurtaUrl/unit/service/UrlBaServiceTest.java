package edu.encurtaUrl.unit.service;


import edu.encurtaUrl.exception.urlRoutine.ExpiredUrl;
import edu.encurtaUrl.exception.urlRoutine.InvalidUrlException;
import edu.encurtaUrl.model.UrlBa;
import edu.encurtaUrl.model.UserBa;
import edu.encurtaUrl.repository.UrlBaRepository;
import edu.encurtaUrl.service.UrlBaService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UrlBaServiceTest {

    @Mock
    EntityManager entityManager;

    @InjectMocks
    UrlBaService urlBaService;

    @Mock
    UrlBaRepository urlBaRepository;

    private UserBa userMockado;
    private UrlBa urlMockada;

    @BeforeEach
    void setUp() {
        userMockado = new UserBa("ASRCC-asccas-cacscas-ascasc", "Bruno", "Brun@gmail.com", "Bruno123", BigDecimal.valueOf(0L), null);
        urlMockada = new UrlBa(null, "www.gloogle.com" , "abcdefg", userMockado, Instant.now(),ZonedDateTime.now().plusHours(1).toInstant());

    }

    @Nested
    class createShortUri{

        @Test
        @DisplayName("Must do the operation with success")
        void SuccesCase1(){
            String shortUri = urlBaService.createShortUri("https://www.youtube.com/", userMockado);

            assertAll(
                    ()-> assertTrue(shortUri.length() >= 7),
                    () -> verify(urlBaRepository, times(1)).save(any())
            );
        }

        @Test
        @DisplayName("Must throw invalid exception")
        void ThrowCase1(){
            assertThrows(InvalidUrlException.class, () ->{
                urlBaService.createShortUri("//huodwdaoiwdhoihh", userMockado);
            });
        }

    }

    @Nested
    class redirectMeTests{

        @Test
        @DisplayName("Must throw expired url exception")
        void mustThrowInvalidUrlException() {
            urlMockada.setExpiresAt(ZonedDateTime.now().minusHours(2).toInstant());

            when(urlBaRepository.findByShortUri(any())).thenReturn(Optional.of(urlMockada));

            Assertions.assertThrows(ExpiredUrl.class,
                    () -> urlBaService.redirectMeUri(any()));
        }
    }


}
