package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/connecte")
    public User getUserConnecte() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            LOGGER.info("authentication={}", authentication);
        }
        Optional<User> userOptional = userService.getUserConnecte();
        User user = new User();
        if (userOptional.isPresent()) {
            user.setConnecte(true);
            userOptional.ifPresent(u -> {
                user.setNom(u.getNom());
                user.setPrenom(u.getPrenom());
            });
            LOGGER.info("utilisateur connecte");
        } else {
            user.setConnecte(false);
            LOGGER.info("utilisateur non connecte");
        }
        return user;
    }

    @GetMapping("/connecte2")
    public User getUserConnecte2() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            LOGGER.info("authentication={}", authentication);
        }
        Optional<User> userOptional = userService.getUserConnecte();
        User user = new User();
        if (userOptional.isPresent()) {
            user.setConnecte(true);
            userOptional.ifPresent(u -> {
                user.setId(u.getId());
                user.setNom(u.getNom());
                user.setPrenom(u.getPrenom());
            });
            LOGGER.info("utilisateur connecte");
        } else {
            user.setConnecte(false);
            LOGGER.info("utilisateur non connecte");
        }
        return user;
    }
}
