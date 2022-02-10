package spring.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genre", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Genre {


    @Id //con esto le indicamos que este id va a ser la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;

    @JsonBackReference
    @ManyToMany(mappedBy = "genres", cascade = CascadeType.ALL)
    private Set<Movie> movies = new HashSet<Movie>();



}
