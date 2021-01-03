package com.example.demo.service;

import com.example.demo.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private WebClient webClient;

    @Value("${app.get-roles}")
    private String urlGetRoles;

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

    public void getListeRoles() {
        String rolesUrl;
        rolesUrl = urlGetRoles;
        LOGGER.info("get all rolesUrl from {}", rolesUrl);
        webClient.get()
                .uri(rolesUrl)
                .retrieve()
                .bodyToMono(String.class)
                .map(string
                        -> "Retrieved using Client Credentials Grant Type: " + string)
                .subscribe(LOGGER::info,
                        error -> LOGGER.error("Erreur", error));
    }
}
