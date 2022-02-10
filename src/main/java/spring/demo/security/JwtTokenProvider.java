package spring.demo.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import spring.demo.exception.AppException;

import java.util.Date;

//vamos a generar el token, obtener los claims y validar el token
//los claims son los datos del token => usuario, fecha de caducidad, roles
//nos aseguramos que el token esté correcto.

@Component
public class JwtTokenProvider {

    //usamos los valores que pusimos en application property
    @Value("${app.jwt-secret}") //con value obtenemos el valor de una propiedad
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;


    //generamos el token con su clave secreta y su usuario
    public String createToken(Authentication authentication) {
        String username = authentication.getName(); //obtenemos el username del usuario
        Date currentDate = new Date(); //fecha actual
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationInMs); //fecha de expiracion del token

        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact(); //le indicamos el algoritmo con el que vamos a trabajar

        return token;
    }

    //obtiene el usuario
    public String getUsernameDelJWT(String token) { //le pasamos el token que generamos
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject(); //obtenemos el username del token
    }

    public boolean validateToken(String token) {
        try {//intentamos validar el token con su clave secreta
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;

            // si no se puede validar el token con su clave, manejamos los errores:

        }catch (SignatureException ex) {
            throw new AppException(HttpStatus.BAD_REQUEST,"Firma JWT no valida");
        }
        catch (MalformedJwtException ex) {
            throw new AppException(HttpStatus.BAD_REQUEST,"Token JWT no valida");
        }
        catch (ExpiredJwtException ex) {
            throw new AppException(HttpStatus.BAD_REQUEST,"Token JWT caducado");
        }
        catch (UnsupportedJwtException ex) {
            throw new AppException(HttpStatus.BAD_REQUEST,"Token JWT no compatible");
        }
        catch (IllegalArgumentException ex) {
            throw new AppException(HttpStatus.BAD_REQUEST,"La cadena claims JWT esta vacia");
        }
    }


}
