package spring.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.demo.entity.Role;
import spring.demo.entity.User;
import spring.demo.repo.UserRepo;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;


    //carga o busca un usuario por su username o email
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
       User user = userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
               .orElseThrow(()-> new UsernameNotFoundException("User not found with that username or email : " + usernameOrEmail));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapperRoles(user.getRoles()));
    }

    //tratamos los roles que son los que dan las autorizaciones a los usuarios
    private Collection<? extends GrantedAuthority> mapperRoles(Set<Role> roles){
        //mapeamos los roles
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


}
