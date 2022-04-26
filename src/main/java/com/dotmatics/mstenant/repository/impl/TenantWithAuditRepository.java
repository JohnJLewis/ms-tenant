package com.dotmatics.mstenant.repository.impl;

import com.dotmatics.mstenant.entity.impl.TenantWithAudit;
import com.dotmatics.mstenant.repository.GenericRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TenantWithAuditRepository extends GenericRepository<TenantWithAudit, Long> {
    Mono<TenantWithAudit> findByEmail(String email);
}
