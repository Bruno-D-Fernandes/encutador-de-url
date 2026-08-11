package edu.encurtaUrl.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users_tb")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserBa implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36)
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 150, unique = true, nullable = false)
    private String email;

    @Column(length = 250, nullable = false)
    private String password;

    @Column(nullable = false)
    private BigDecimal credit;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UrlBa> urls;

    public UserBa(@NotBlank(message = "Name cant be blank") @Size(min = 5, max = 70, message = "Invalid size") String name, @NotBlank(message = "Email cant be blank") String email, @NotBlank(message = "Password cant be blank") String password, BigDecimal credit) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBa userB = (UserBa) o;
        return Objects.equals(id, userB.id) && Objects.equals(name, userB.name) && Objects.equals(email, userB.email) && Objects.equals(password, userB.password) && Objects.equals(credit, userB.credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, credit);
    }

    @Override
    public String toString() {
        return "UserB{" +
                "credit=" + credit +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Implementar níveis de conta a partir de crédito
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
