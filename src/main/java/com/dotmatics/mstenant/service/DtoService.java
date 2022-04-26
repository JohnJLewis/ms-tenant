package com.dotmatics.mstenant.service;

import com.dotmatics.mstenant.mapper.DtoMapper;
import com.dotmatics.mstenant.messages.Messages;
import com.dotmatics.mstenant.model.BaseDto;
import com.dotmatics.mstenant.repository.GenericRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@NoRepositoryBean
public abstract class DtoService<T, ID, DTO extends BaseDto> {
    public  static final String DTO_ANY                 = "any";
    public  static final String DTO_ID                  = "id";
    public  static final String DTO_EMAIL               = "email";
    public  static final String MSG_NOT_FOUND           = "api.error.id.notfound";
    public  static final String MSG_GENERIC_NOT_FOUND   = "api.error.notfound";

    public abstract GenericRepository<T, ID> getRepository();
    public abstract DtoMapper<T, ID, DTO> getMapper();
    public abstract String getDtoName();
    public abstract ID getId(DTO dto);
    public abstract DTO getNewDto();

    public Mono<DTO> get(ID id) {
        return get(getDto(id));
    }

    public Mono<DTO> get(DTO dto) {
        return monoEntityToMonoDto(getEntity(getId(dto)))
                .switchIfEmpty(Mono.error(new Exception(MSG_NOT_FOUND)));
    }

    public Mono<T> getEntity(ID id) {
        return getRepository().findById(id);
    }

    public Mono<DTO> monoEntityToMonoDto(Mono<T> monoEntity) {
        return monoEntity
                .flatMap(getMapper()::toDto);
    }

    public Mono<DTO> exists(ID id) {
        return exists(getDto(id));
    }

    public Mono<DTO> exists(DTO dto) {
        return getRepository().existsById(getId(dto))
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.just(dto);
                    } else {
                        return Mono.error(new Exception(MSG_NOT_FOUND));
                    }
                });
    }

    public Mono<DTO> count(DTO dto) {
        return getRepository().count()
                .flatMap(count -> {
                    getMapper().addCount(dto, count);
                    return Mono.just(dto);
                });
    }

    public Flux<DTO> findAll() {
        return getRepository()
                .findAll()
                .flatMap(getMapper()::toDto);
    }

    public Mono<DTO> insert(DTO dto) {
        return monoEntityToMonoDto(
                   getRepository().save(getMapper().toEntity(dto))
                        .onErrorResume(e -> Mono.error(new Exception("Insert error: " + e.getMessage()))));
    }

    public Mono<DTO> update(DTO dto) {
        return monoEntityToMonoDto(
                   getEntity(getId(dto))
                       .switchIfEmpty(Mono.error(new Exception(MSG_NOT_FOUND)))

                       .flatMap(entity -> getRepository().save(getMapper().applyUpdates(entity, dto))

                       .onErrorResume(e -> Mono.error(new Exception("Update error: " + e.getMessage())))));
    }

    public Mono<Void> delete(ID id) {
        return getRepository().deleteById(id);
    }

    public Mono<DTO> delete(DTO dto) {
        return getEntity(getId(dto))
                .switchIfEmpty(Mono.error(new Exception(MSG_NOT_FOUND)))

                .flatMap(flatEntity -> delete(getId(dto))
                        .then(monoEntityToMonoDto(Mono.just(flatEntity))));

    }

    public DTO addErrorInfo(DTO dto, String column, String error) {
        if (dto==null) {
            dto = getNewDto();
        }

        getMapper().addError(dto, column, Messages.get(error, getMapper().getDtoId(dto)), error);
        return dto;
    }

    public DTO getDto(ID id) {
        return getMapper().getDto(id);
    }

    public Mono<DTO> addUserName(DTO dto) {
        return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> ctx.getAuthentication().getName())
                .flatMap(username -> {
                    getMapper().addUserName(dto, username);
                    return Mono.just(dto);
                });
    }

    public void addResponseInfo(DTO dto, Principal principal, String action) {
        getMapper().addAction(dto, action, getDtoName());
        getMapper().addUserName(dto, principal==null?"anonymousUser":principal.getName());;
    }
}
