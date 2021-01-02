package com.example.demo.service;

import com.example.demo.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    public Optional<User> getUserConnecte() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof Jwt) {
                Jwt jwt = (Jwt) authentication.getPrincipal();
                if (jwt.getClaims() != null) {
                    Map<String, Object> map = jwt.getClaims();
                    User user = new User();
                    user.setId(getString(map, "sub"));
                    user.setNom(getString(map, "family_name"));
                    user.setPrenom(getString(map, "given_name"));
                    return Optional.of(user);
                }
            }
        }
        return Optional.empty();
    }

    private String getString(Map<String, Object> map, String key) {
        if (map != null && map.containsKey(key)) {
            Object o = map.get(key);
            if (o != null && o instanceof String) {
                return o.toString();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
}
