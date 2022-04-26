package com.dotmatics.mstenant.repository.impl;

import com.dotmatics.mstenant.entity.impl.Tenant;
import com.dotmatics.mstenant.repository.GenericRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TenantRepository extends GenericRepository<Tenant, Long> {
    Mono<Tenant> findByEmail(String email);
}
