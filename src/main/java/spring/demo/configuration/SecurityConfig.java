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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import spring.demo.security.CustomUserDetailsService;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //lo desabilitamos porque spring security ya tiene uno
                //cualquier usuario pude hacer la peticion get
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/**")
                .permitAll()

                //api/auth va a ser nuestro rest controller
                .antMatchers("/api/auth/**").permitAll()

                //cualquier otra peticion, va a tener que ser autenticada
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic(); //una autentificacion basica
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
