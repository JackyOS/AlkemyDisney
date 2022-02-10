package spring.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Person {

    @Id //con esto le indicamos que este id va a ser la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    private String name;
    private Integer age;
    private Integer weight;
    private String history;

    @JsonBackReference
    @ManyToMany(mappedBy = "persons", cascade = CascadeType.ALL)
    private Set<Movie> movies = new HashSet<Movie>();


}
