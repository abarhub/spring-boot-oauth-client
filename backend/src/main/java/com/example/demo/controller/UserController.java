package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/connecte")
    public User getUserConnecte() {
        Optional<User> userOptional = userService.getUserConnecte();
        User user = new User();
        if (userOptional.isPresent()) {
            user.setConnecte(true);
            userOptional.ifPresent(u -> {
                user.setNom(u.getNom());
                user.setPrenom(u.getPrenom());
            });
        } else {
            user.setConnecte(false);
        }
        return user;
    }
}
