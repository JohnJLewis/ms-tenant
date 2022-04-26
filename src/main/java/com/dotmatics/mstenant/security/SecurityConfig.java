package com.dotmatics.mstenant.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    //Authentication exclusion list of security
    private static final String[] excludedAuthPages = {
//            "/api/v1/tenant",
//            "/api/v1/tenant/**",
            "/api/user"
    };

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(httpBasicSpec -> httpBasicSpec
                        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                )
                .authorizeExchange(it ->
                        it.pathMatchers(excludedAuthPages).permitAll()
                                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                                .anyExchange().authenticated()
                )
                .addFilterAfter(new JwtWebFilter(), SecurityWebFiltersOrder.FIRST)
                .build();
    }
}
