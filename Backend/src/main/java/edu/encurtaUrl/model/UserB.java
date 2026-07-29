package edu.encurtaUrl.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users_tb")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserB {

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

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UrlB> urls;

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserB userB = (UserB) o;
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
}
