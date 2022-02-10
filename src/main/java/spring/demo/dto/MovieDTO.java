package spring.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.demo.entity.Genre;
import spring.demo.entity.Person;
import spring.demo.entity.Review;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieDTO {

    private Long id;

    @NotEmpty //validaciones => dependencia validation en el pom => no debe estar vacio este atributo
    private String image;

    @NotEmpty(message = "Title is mandatory")
    @Size(min= 2, message = "El titulo de la publicacion deberia tener al menos dos caracteres")
    private String title;

    //@NotEmpty
    private LocalDate date;

    //@NotEmpty
    @Min(1)
    @Max(5)
    private Integer rating;


    private Set<Person> persons;

    private Set<Genre> genres;

    private Set<Review> reviews;

}
