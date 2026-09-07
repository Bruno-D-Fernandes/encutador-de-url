package edu.encurtaUrl.repository;

import edu.encurtaUrl.model.UrlBa;
import edu.encurtaUrl.model.UserBa;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@ActiveProfiles("test")
@DataJpaTest
public class UrlBaRepositoryTest {

    @Autowired
    private UrlBaRepository urlBaRepository;

    @Autowired
    private EntityManager entityManager;

    private UrlBa urlBaMockadoUm;

    private UserBa userBaMockadoUm;

    @BeforeEach
    void setup(){
        userBaMockadoUm = new UserBa("Bruno", "Bruno@gmail.com", "Bruno2310", BigDecimal.TEN);
        urlBaMockadoUm = new UrlBa(null, "www.google.com", "hjkliop", userBaMockadoUm, Instant.now(), ZonedDateTime.now().plusHours(10).toInstant());
    }

    @Test
    @DisplayName("Must find all url by owner")
    void findUrlByOwner(){

        // Sim, eu poderia só usar o persist, mas assim é bom para treinar o lifecycle das entidades
        UrlBa urlManaged = entityManager.merge(urlBaMockadoUm);
        entityManager.flush();

        UserBa ownerManaged = urlManaged.getOwner();

        List<UrlBa> byOwner = urlBaRepository.findByOwner(ownerManaged);

        Assertions.assertAll(
                () -> Assertions.assertFalse(byOwner.isEmpty(), "The list should not be empty"),
                () -> Assertions.assertEquals(urlManaged, byOwner.get(0))
        );

    }


}

