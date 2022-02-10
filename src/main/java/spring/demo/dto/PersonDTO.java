package spring.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.demo.entity.Movie;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonDTO {

    private Long id;
    private String image;
    private String name;
    private Integer age;
    private Integer weight;
    private String history;
    private Set<Movie> movies;
}
