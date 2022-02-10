package spring.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.demo.entity.Movie;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenreDTO {

    private Long id;
    private String name;
    private String image;
    private Set<Movie> movies = new HashSet<Movie>();

}
