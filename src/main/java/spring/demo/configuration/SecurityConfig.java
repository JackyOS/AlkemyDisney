package spring.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import spring.demo.security.CustomUserDetailsService;
import spring.demo.security.JwtAuthenticationEntryPoint;
import spring.demo.security.JwtAuthenticationFilter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity //para poder crear una clase de seguridad personalizada
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //usamos esa extension para poder redefinir alguno de los metodos de spring security
    //vamos a hashear las contraseñas para que no se guarden en texto plano

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

   //clase que se encarga de manejar los errores de cuando un usuario no está autorizado
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //clase que se encarga de establecer un filtro para ver si el token esta correcto, antes de acceder a los recursos
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //lo desabilitamos porque spring security ya tiene uno

                //establecemos una excepcion
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) //maneja los errores

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //sin estado
                .and()

                //api/auth va a ser nuestro rest controller
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()

                //cualquier usuario pude hacer la peticion get
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                //.permitAll()
                .antMatchers(POST,"/api/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers(DELETE,"/api/**").hasAnyAuthority("ROLE_ADMIN")

                //cualquier otra peticion, va a tener que ser autenticada
                .anyRequest()
                .authenticated();
                //.and()
                //.httpBasic(); //una autentificacion basica
        //hacemos una autenticacion mejor -> le pasamos el filtro que se encarga de validar el token
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    //construimos nuestros usuarios
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /*
    cargamos usuarios en memoria
    @Override
    @Bean //se lo agregamos para que quede registrado
    protected UserDetailsService userDetailsService() {
        //creamos un usuario
       UserDetails jacky = User.builder()
               .username("jacky").password(passwordEncoder().encode("jacky"))
               .roles("USER").build();

       UserDetails admin = User.builder()
                .username("admin").password(passwordEncoder().encode("admin"))
                .roles("ADMIN").build();

       //retonarmos una nueva instancia en memoria, se guardan los usuarios en memoria
       return new InMemoryUserDetailsManager(jacky, admin);
    }
*/
}
