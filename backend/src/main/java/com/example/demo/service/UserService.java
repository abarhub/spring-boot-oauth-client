package com.example.demo.service;

import com.example.demo.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    public Optional<User> getUserConnecte(){
        User user=new User();
        user.setId("123");
        user.setNom("Dupuis");
        user.setPrenom("Paul");
        return Optional.of(user);
    }

}
