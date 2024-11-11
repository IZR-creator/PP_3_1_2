package springbootsecurity.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }

    @Override
    public String toString() {
        return name.startsWith("ROLE_") ? name.substring(5) : name;
    }
}
