package spring.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.demo.entity.User;
import spring.demo.repo.UserRepo;

import java.util.List;

@Service

public class UserService{

    @Autowired
    UserRepo userRepo;

    public List<User> getUsers(){
        return userRepo.findAll();
    }

/*
    public User saveUser(User user){
        return userRepo.save(user);
    }

    public Role saveRole(Role role){
        return roleRepo.save(role);
    }

    public void addRoleToUser(String username, String roleName){
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    public User getUserById(Long id){
        return userRepo.findById(id).orElse(null);
    }

    public User getUser(String username){
        return userRepo.findByUsername(username);
    }

    public void deleteUserById(Long id){
        userRepo.deleteById(id);
    }
*/

}
