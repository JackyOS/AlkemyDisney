package spring.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.demo.entity.Movie;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDTO {

    private Long id;

    @NotEmpty
    private String name;

    @Email(message = "El email no debe ser vacio o nulo")
    private String email;

    @NotEmpty(message = "El contenido no debe estar vacio")
    @Size(min= 10, message = "El contenido debe tener al menos diez caracteres")
    private String content;


}
