package com.dotmatics.mstenant.routing;

import com.dotmatics.mstenant.model.impl.TenantDto;
import com.dotmatics.mstenant.service.impl.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
@RequiredArgsConstructor
public class TenantRoutingConfig implements WebFluxConfigurer {
    private static final String PATH        = "/api/v1/tenant/";

    private final TenantService service;

    @Bean
    RouterFunction<ServerResponse> getTenantRoutes() {
        return route()
                .GET("/api/user", this::getUser)
                .GET(PATH+"email/{email}", this::getEmail)
                .POST(PATH+"email", this::emailPost)
                .build();
    }

    private Mono<ServerResponse> getUser(ServerRequest request) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getName())
                .flatMap(username -> ServerResponse.ok().bodyValue(username));
    }

    private Mono<ServerResponse> getEmail(ServerRequest request) {
        return service.getByEmail(request.pathVariable("email"))
                    .flatMap((dto -> service.addUserName(dto)))
                    .flatMap(dto -> ServerResponse.ok().bodyValue(dto));
    }

    private Mono<ServerResponse> emailPost(ServerRequest request) {
        return request.bodyToMono(TenantDto.class)
                .flatMap(dto -> service.getByEmail(dto))
                .flatMap((dto -> service.addUserName(dto)))
                .flatMap(dto -> ServerResponse.ok().bodyValue(dto));
    }
}
