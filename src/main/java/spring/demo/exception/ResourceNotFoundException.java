package spring.demo.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {



    private String nombreDelRecurso;
    private String nombreDelCampo;
    private long valorDelRecurso;


    public ResourceNotFoundException(String nombreDelRecurso, String nombreDelCampo, long valorDelRecurso) {
        super(String.format("%s No encontrado con : '%s' : '%s' ", nombreDelRecurso, nombreDelCampo, valorDelRecurso));
        // %s es el parametro. hay 3 %S, para los 3 parametros que tenemos
        this.nombreDelRecurso = nombreDelRecurso;
        this.nombreDelCampo = nombreDelCampo;
        this.valorDelRecurso = valorDelRecurso;
    }

}
