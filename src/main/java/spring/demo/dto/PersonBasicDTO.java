package spring.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonBasicDTO {

    private Long id;
    private String image;
    private String name;
    //private Integer age;
    //private Integer weight;
    //private String history;
    //private Set<Movie> movies;
}
