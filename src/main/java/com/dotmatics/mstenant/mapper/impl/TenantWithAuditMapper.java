package com.dotmatics.mstenant.mapper.impl;

import com.dotmatics.mstenant.entity.impl.TenantWithAudit;
import com.dotmatics.mstenant.mapper.DtoMapper;
import com.dotmatics.mstenant.model.impl.TenantWithAuditDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TenantWithAuditMapper extends DtoMapper<TenantWithAudit, Long, TenantWithAuditDto> {
    @Override
    public TenantWithAuditDto getDto(Long id) {
        TenantWithAuditDto dto = new TenantWithAuditDto();
        dto.id = id;
        return dto;
    }

    @Override
    public Long getDtoId(TenantWithAuditDto dto) {
        return dto.id;
    }

    @Override
    public boolean validateData(TenantWithAuditDto dto) {
        return ! isError(dto);
    }

    @Override
    public Mono<TenantWithAuditDto> toDto(TenantWithAudit entity) {
        TenantWithAuditDto dto = new TenantWithAuditDto();
        setAuditData(dto, entity);
        dto.id =  entity.getId();
        dto.name = entity.getName();
        dto.email = entity.getEmail();
        return Mono.just(dto);
    }

    @Override
    public TenantWithAudit toEntity(TenantWithAuditDto dto) {
        TenantWithAudit entity = new TenantWithAudit();
        return applyUpdates(entity, dto);
    }

    @Override
    public TenantWithAudit applyUpdates(TenantWithAudit entity, TenantWithAuditDto dto) {
        if (dto.name!=null) entity.setName(dto.name);
        if (dto.email!=null) entity.setEmail(dto.email);
        return entity;
    }
}
