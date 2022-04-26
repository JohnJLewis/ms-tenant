package com.dotmatics.mstenant.service.impl;

import com.dotmatics.mstenant.entity.impl.TenantWithAudit;
import com.dotmatics.mstenant.mapper.DtoMapper;
import com.dotmatics.mstenant.mapper.impl.TenantWithAuditMapper;
import com.dotmatics.mstenant.messages.Messages;
import com.dotmatics.mstenant.model.impl.TenantWithAuditDto;
import com.dotmatics.mstenant.repository.impl.TenantWithAuditRepository;
import com.dotmatics.mstenant.service.DtoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.lang.invoke.MethodHandles;

@RequiredArgsConstructor
@Slf4j
@Service
public class TenantWithAuditService extends DtoService<TenantWithAudit, Long, TenantWithAuditDto> {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TenantWithAuditRepository repository;
    private final TenantWithAuditMapper mapper;

    @Override
    public TenantWithAuditRepository getRepository() {
        return repository;
    }

    @Override
    public DtoMapper<TenantWithAudit, Long, TenantWithAuditDto> getMapper() {
        return mapper;
    }

    @Override
    public String getDtoName() {
        return "tenantWithAudit";
    }

    @Override
    public Long getId(TenantWithAuditDto dto) {
        return dto.id;
    }

    @Override
    public TenantWithAuditDto getNewDto() {
        return new TenantWithAuditDto();
    }

    public Mono<TenantWithAuditDto> getByEmail(String email) {
        TenantWithAuditDto dto = getNewDto();
        dto.email = email;
        return getByEmail(dto);
    }

    public Mono<TenantWithAuditDto> getByEmail(TenantWithAuditDto dto) {
        return getRepository().findByEmail(dto.email)
                .flatMap(entity -> getMapper().toDto(entity)
                                                .flatMap(flatDto -> {
                                                    getMapper().addAction(flatDto, "byEmail", getDtoName());
                                                    return Mono.just(flatDto);
                                                }))

                .switchIfEmpty(Mono.just(dto))
                    .flatMap(flatDto -> {
                        getMapper().addAction(dto, "byEmail", getDtoName());
                        getMapper().addError(dto, DTO_EMAIL, Messages.get(MSG_GENERIC_NOT_FOUND, dto.email), MSG_GENERIC_NOT_FOUND);
                        return Mono.just(flatDto);
                    });
    }


}
