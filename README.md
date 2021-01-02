# spring-boot-oauth-client

Implémentation d'OpenId Connect avec KeyCloak

C'est avec KeyCloak 11.0.3.

Pour Spring, il faut ajouter la dependance :
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```
Il faut aussi ajouter en configuration :

```yml
spring:
    security:
        oauth2:
            resourceserver:
                jwt:
                    issuer-uri: http://localhost:8180/auth/realms/test1
                    jwk-set-uri: http://localhost:8180/auth/realms/test1/protocol/openid-connect/certs
```

Pour Angular, il faut utiliser la librairie angular-oauth2-oidc.
Sites :
https://manfredsteyer.github.io/angular-oauth2-oidc/docs/
https://github.com/manfredsteyer/angular-oauth2-oidc
https://www.npmjs.com/package/angular-oauth2-oidc

L'authentifiation avec Angular doit être fait avec code flow + PKCE, et pas avec implicit flow, qui est moins sécurisé.

Pour l'authentification, :
1) Angular redirige vers KeyCloak,
2) KeyCloak fait l'authentification et redirige vers le navigateur
3) le navigateur enregistre le token et l'utilise pour appeler le endpoind spring
4) Spring vérifie le token et récupère de keycloak les informations de l'utilisateur et crée la session
5) le endpoint fait son traitement

Le vériviation du token dans Spring n'est fait qu'une seule fois, elle n'est plus faite apres.
Dans Angular, il faut lui dire de faire le rafrechissement régulièrement.
https://manfredsteyer.github.io/angular-oauth2-oidc/docs/additional-documentation/refreshing-a-token.html

Dans KeyCloak, il faut définir le temps maximum de rafrechissement du token

Si Spring a besoin d'accéder a des info de KeyCloak, il ne doit pas utiliser le token venant d'Angular,
mais faire la propre connection avec login/mot de passe.

Aides :


