package spring.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.demo.dto.LoginDTO;
import spring.demo.dto.RegisterDTO;
import spring.demo.entity.Role;
import spring.demo.entity.User;
import spring.demo.repo.RoleRepo;
import spring.demo.repo.UserRepo;

import java.util.Collections;

//vamos a iniciar sesion, pasar la credenciales y establecer  la autenticacion

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder; //como ya fue registrado con un bean se puede utilizar aca

    //creamos un usuario autenticado con el username o email y el password que obtenemos
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        //establecemos el contexto y la autentificacion
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Log in successful", HttpStatus.OK);
    }

    //REGISTRAMOS EL USUARIO
    @PostMapping("/register")
    private ResponseEntity<?> userRegister(@RequestBody RegisterDTO registerDTO){
        //si el username que le pasamos ya existe en la base de datos:
        if(userRepo.existsByUsername(registerDTO.getUsername())){
            return new ResponseEntity<>("The username is already exist", HttpStatus.BAD_REQUEST);
        }

        //si el email que le pasamos ya existe en la base de datos:
        if(userRepo.existsByEmail(registerDTO.getEmail())){
            return new ResponseEntity<>("The email is already exist", HttpStatus.BAD_REQUEST);
        }

        //si no existe el username o el email en la base de datos, lo guardamos:
        User user = new User();
        user.setName(registerDTO.getName());
        user.setLastname(registerDTO.getLastname());
        user.setEmail(registerDTO.getEmail());
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // establecemos los roles a este usuario
        Role roles = roleRepo.findByName("ROLE_ADMIN").get();
        //singleton => establecemos un solo rol al usuario
        user.setRoles(Collections.singleton(roles));

        userRepo.save(user);
        return new ResponseEntity<>("Registration successful", HttpStatus.OK);
    }


}
