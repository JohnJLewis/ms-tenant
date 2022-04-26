package com.dotmatics.mstenant.mapper.impl;

import com.dotmatics.mstenant.entity.impl.Tenant;
import com.dotmatics.mstenant.mapper.DtoMapper;
import com.dotmatics.mstenant.model.impl.TenantDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TenantMapper extends DtoMapper<Tenant, Long, TenantDto> {
    @Override
    public TenantDto getDto(Long id) {
        return TenantDto.builder()
                .id(id)
                .build();
    }

    @Override
    public Long getDtoId(TenantDto dto) {
        return dto.id;
    }

    @Override
    public boolean validateData(TenantDto dto) {
        return ! isError(dto);
    }

    @Override
    public Mono<TenantDto> toDto(Tenant entity) {
        return Mono.just(
                TenantDto.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .email(entity.getEmail())
                        .build());
    }

    @Override
    public Tenant toEntity(TenantDto dto) {
        Tenant entity = new Tenant();
        return applyUpdates(entity, dto);
    }

    @Override
    public Tenant applyUpdates(Tenant entity, TenantDto dto) {
        if (dto.name!=null) entity.setName(dto.name);
        if (dto.email!=null) entity.setEmail(dto.email);
        return entity;
    }
}
