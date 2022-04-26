package com.dotmatics.mstenant.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;

public class JwtWebFilter implements WebFilter {
    private static final String HEADER_AUTHORIZATION        = "Authorization";
    private static final String HEADER_BEARER               = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        final String authorizationHeader = request.getHeaders().getFirst(HEADER_AUTHORIZATION);

        if (! StringUtils.isBlank(authorizationHeader) && authorizationHeader.startsWith(HEADER_BEARER)) {
            String encryptedJwt = authorizationHeader.replace(HEADER_BEARER, "");
            DecodedJWT jwt = JWT.decode(encryptedJwt);

            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.NO_AUTHORITIES;
            User principal = new User(jwt.getClaim("email").asString(), "", authorities);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, null, authorities);

            return chain
                    .filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(token));
        }

        return chain.filter(exchange);
    }
}
