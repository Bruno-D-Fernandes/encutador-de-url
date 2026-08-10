package edu.encurtaUrl.repository;

import edu.encurtaUrl.model.UrlBa;
import edu.encurtaUrl.model.UserBa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlBaRepository extends JpaRepository<UrlBa, Long> {
    List<UrlBa> findByOwner(UserBa user);

    Optional<UrlBa> findByShortUri(String uri);

    boolean existsByShortUri(String shortUri);
}
