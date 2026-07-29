package edu.encurtaUrl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "urls_tb")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UrlBa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(name = "original_uri", length = 450, nullable = false)
    private String originalUri;

    @Column(name = "short_uri", length = 450, nullable = false)
    private String shortUri;

    @ManyToOne(fetch = FetchType.EAGER,  cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_owner", nullable = false)
    private UserBa owner;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlBa urlBa = (UrlBa) o;
        return Objects.equals(uuid, urlBa.uuid) && Objects.equals(originalUri, urlBa.originalUri) && Objects.equals(shortUri, urlBa.shortUri) && Objects.equals(createdAt, urlBa.createdAt) && Objects.equals(expiresAt, urlBa.expiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, originalUri, shortUri, createdAt, expiresAt);
    }

    @Override
    public String toString() {
        return "UrlB{" +
                "shortUri='" + shortUri + '\'' +
                ", originalUri='" + originalUri + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                ", owner=" + owner +
                '}';
    }
}
