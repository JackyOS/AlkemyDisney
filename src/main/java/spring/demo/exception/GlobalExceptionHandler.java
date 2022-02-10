package spring.demo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.demo.dto.DetailsErrors;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //lo ponemos para que esta clase pueda manejar todas las excepciones de la app
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler { //agregp este reponse entity excep..porque:
                                        //voy a añadir un metodo para manejar las excepciones cuando
                                        //los datos que se pasan en los input no son validos

    @ExceptionHandler(ResourceNotFoundException.class) //se encarga de manejar las excepciones detalladas => las de la clase ResourceNotFoundException
    public ResponseEntity<DetailsErrors> handlerResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        DetailsErrors detailsErrors = new DetailsErrors(new Date(), exception.getMessage(), webRequest.getDescription(false));
                //detailErrror(timemark=> fecha actual, exeption => message, web Resquest => que no muestre la descripcion
        return new ResponseEntity<>(detailsErrors, HttpStatus.NOT_FOUND);
                                    //retorna los errores y un not found
    }


    @ExceptionHandler(AppException.class) //se encarga de manejar las excepciones detalladas => las de la clase AppException
    public ResponseEntity<DetailsErrors> handlerAppException(AppException exception, WebRequest webRequest){
        DetailsErrors detailsErrors = new DetailsErrors(new Date(), exception.getMessage(), webRequest.getDescription(false));
        //detailErrror(timemark=> fecha actual, exeption => message, web Resquest => que no muestre la descripcion
        return new ResponseEntity<>(detailsErrors, HttpStatus.BAD_REQUEST);
        //retorna los errores y un bad request
    }


    @ExceptionHandler(Exception.class) //se encarga de manejar las excepciones globales
    public ResponseEntity<DetailsErrors> handlerGlobalException(Exception exception, WebRequest webRequest){
        DetailsErrors detailsErrors = new DetailsErrors(new Date(), exception.getMessage(), webRequest.getDescription(false));
        //detailErrror(timemark=> fecha actual, exeption => message, web Resquest => que no muestre la descripcion
        return new ResponseEntity<>(detailsErrors, HttpStatus.INTERNAL_SERVER_ERROR);
        //retorna los errores y un internal server error
    }


    //metodo que sacamos del extend ResponseEntityExceptionHandler
    //para manejar excepciones en caso de que se pasen datos no validos en los input
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> { //tomo todos los errores y los recorro
            String fieldName = ((FieldError)error).getField(); //obtengo el campo donde esta marcando el error
            String message = error.getDefaultMessage(); //mensaje del error que pusimos en los dto=> ej: "el titulo debe tener mas de dos caracteres"

            errors.put(fieldName,message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }



}
