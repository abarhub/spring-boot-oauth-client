package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.UnAuthenticatedServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/info", "/api/foos/**")
                .hasAuthority("SCOPE_read")
                .antMatchers(HttpMethod.POST, "/api/foos")
                .hasAuthority("SCOPE_write")
                .antMatchers(HttpMethod.GET, "/api/produits")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/connecte")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/users/connecte2")
                .authenticated()
                .antMatchers(HttpMethod.GET, "/api/users/liste-roles")
                .authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }

    @Bean
    public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        clientRegistrations,
                        new UnAuthenticatedServerOAuth2AuthorizedClientRepository());
                        //new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager());
        oauth.setDefaultClientRegistrationId("backend");
        return WebClient.builder()
                .filter(oauth)
                .build();
    }

//    @Bean
//    public ReactiveClientRegistrationRepository clientRegistrations(
//            @Value("${spring.security.oauth2.client.provider.my-platform.token-uri}") String tokenUri,
//            @Value("${spring.security.oauth2.client.registration.my-platform.client-id}") String clientId,
//            @Value("${spring.security.oauth2.client.registration.my-platform.client-secret}") String clientSecret,
//            @Value("${spring.security.oauth2.client.registration.my-platform.scopes}") String scope
//    ) {
//        ClientRegistration registration = ClientRegistration
//                .withRegistrationId("my-platform")
//                .tokenUri(tokenUri)
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .scope(scope)
//                .build();
//        return new InMemoryReactiveClientRegistrationRepository(registration);
//    }
    @Bean
    public ReactiveClientRegistrationRepository clientRegistrations(
            @Value("${spring.security.oauth2.client.provider.backend.token-uri}") String tokenUri,
            @Value("${spring.security.oauth2.client.registration.backend.client-id}") String clientId,
            @Value("${spring.security.oauth2.client.registration.backend.client-secret}") String clientSecret//,
            //@Value("${spring.security.oauth2.client.registration.backend.scopes}") String scope
    ) {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId("backend")
                .tokenUri(tokenUri)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                //.scope(scope)
                .build();
        return new InMemoryReactiveClientRegistrationRepository(registration);
    }

}
