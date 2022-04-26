package com.dotmatics.mstenant.service.impl;

import com.dotmatics.mstenant.entity.impl.Tenant;
import com.dotmatics.mstenant.mapper.DtoMapper;
import com.dotmatics.mstenant.mapper.impl.TenantMapper;
import com.dotmatics.mstenant.messages.Messages;
import com.dotmatics.mstenant.model.impl.TenantDto;
import com.dotmatics.mstenant.repository.impl.TenantRepository;
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
public class TenantService extends DtoService<Tenant, Long, TenantDto> {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final TenantRepository repository;
    private final TenantMapper mapper;

    @Override
    public TenantRepository getRepository() {
        return repository;
    }

    @Override
    public DtoMapper<Tenant, Long, TenantDto> getMapper() {
        return mapper;
    }

    @Override
    public String getDtoName() {
        return "tenant";
    }

    @Override
    public Long getId(TenantDto dto) {
        return dto.id;
    }

    @Override
    public TenantDto getNewDto() {
        return new TenantDto();
    }

    public Mono<TenantDto> getByEmail(String email) {
        TenantDto dto = getNewDto();
        dto.email = email;
        return getByEmail(dto);
    }

    public Mono<TenantDto> getByEmail(TenantDto dto) {
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
