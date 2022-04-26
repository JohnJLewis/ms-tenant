package com.dotmatics.mstenant.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Configuration
@EnableR2dbcAuditing
public class EnableR2dbcAuditingConfig {

    @Bean
    public SpringSecurityAuditorAware auditorAwareProvider() {
        return new SpringSecurityAuditorAware();
    }
}
