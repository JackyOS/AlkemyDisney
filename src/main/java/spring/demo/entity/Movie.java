package spring.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Movie  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    private String title;
    @DateTimeFormat(pattern ="yyyy/mm/dd")
    private LocalDate date;
    private Integer rating;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable( //creamos una tabla intermedia que crea una relacion de muchos a muchos entre usuarios y roles
            name = "movie_person",
            joinColumns = @JoinColumn(name="movie_id"),
            inverseJoinColumns = @JoinColumn(name="person_id")
    )
    //@JsonIgnore //con esto soluciono el problema de la recursion infinita
    private Set<Person> persons = new HashSet<Person>();
    //usamos set para que no hayan valores repetidos


    //lazy => carga perezosa/vaga => solo carga los datos cuando lo necesitamos que se cargue / cuando se lo pedimos
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable( //creamos una tabla intermedia que crea una relacion de muchos a muchos entre usuarios y roles
            name = "movie_genre",
            joinColumns = @JoinColumn(name="movie_id"),
            inverseJoinColumns = @JoinColumn(name="genre_id")
    )
    //@JsonIgnore //ponemos esto aca o ponemos @JsonBackReference en la entidad asociada
    private Set<Genre> genres = new HashSet<Genre>();
    //usamos set para que no hayan valores repetidos


    //orphanRemoval = true => si elimino la pelicula, se eliminan los comentarios (los comentarios dependen de la pelicula). Se eliminan, los objetos asociados a Movie, cuando se elimina la movie
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL) //, orphanRemoval = true)
    @JsonBackReference //me soluciona el problema de recursion infinita cuando hay relaciones bidireccionales
    private Set<Review> reviews = new HashSet<>();

    //@JsonBackReference = me soluciona el problema de recursion infinita cuando hay relaciones bidireccionales
    //se ignora el contenido cuando se serializa. Los datos json(el response), no va a contener este atributo review y evita el problema de la recursion (eso de convertir de entidad a dto y de dto a entidad => serializacion)

    public void addCharacter(Person person) {
        persons.add(person);
        person.getMovies().add(this);
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
        genre.getMovies().add(this);
    }


}
