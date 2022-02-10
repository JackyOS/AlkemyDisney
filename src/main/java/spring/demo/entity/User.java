package spring.demo.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})})
public class User {

    @Id //con esto le indicamos que este id va a ser la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //.IDENTITY EN VEZ DE AUTO
    private Long id;

    private String name;
    private String lastname;

    private String username;
    private String email;
    private String password;


                //eager => ansiosa, siempre al tanto
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable( //creamos una tabla intermedia que crea una relacion de muchos a muchos entre usuarios y roles
            name = "user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles;


}
