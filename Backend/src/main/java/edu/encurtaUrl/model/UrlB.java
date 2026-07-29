package edu.encurtaUrl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "urls_tb")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UrlB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "original_uri", length = 450, nullable = false)
    private String originalUri;

    @Column(name = "short_uri", length = 450, nullable = false)
    private String shortUri;

    @ManyToOne(fetch = FetchType.EAGER,  cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_owner", nullable = false)
    private User owner;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlB urlB = (UrlB) o;
        return Objects.equals(id, urlB.id) && Objects.equals(originalUri, urlB.originalUri) && Objects.equals(shortUri, urlB.shortUri) && Objects.equals(createdAt, urlB.createdAt) && Objects.equals(expiresAt, urlB.expiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalUri, shortUri, createdAt, expiresAt);
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
