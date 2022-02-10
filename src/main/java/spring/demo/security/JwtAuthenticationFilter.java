package spring.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//con un filtro, validamos que el token esté correcto para permitirnos acceder a los recursos solicitados

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //obtenemos el token de la solicitud HTTP
        String token = getJWTFromRequest(request); //obtenemos el token de la solicitud

        //validamos el token
        //si tiene el texto y si es valido el token...
        if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            //obtenemos el username del token
            String username = jwtTokenProvider.getUsernameDelJWT(token);

            //cargamos el usuario asociado al token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            //lo autenticamos, le pasamos las credenciales
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //establecemos la seguridad
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    //Bearer token de acceso
    //bearer => es un formato que nos permite la autorizacion de un usuario
    //vamos a obtener el token
    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        //si tiene el beare token y si empieza con "Bearer"...
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7,bearerToken.length());
                            //recorto y obtengo el token, sacando el texto "bearer"(6 caracteres) + 1 espacio = 7
        }
        return null;
    }


}
