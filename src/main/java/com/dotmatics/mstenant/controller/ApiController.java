package com.dotmatics.mstenant.controller;

import com.dotmatics.mstenant.model.BaseDto;
import com.dotmatics.mstenant.service.DtoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

//@SecurityRequirement(name = "bearer-key")
public abstract class ApiController<T, ID, DTO extends BaseDto> {
    private final String DTO_COUNT      = "count";
    private final String DTO_GET        = "get";
    private final String DTO_EXISTS     = "exists";
    private final String DTO_INSERT     = "insert";
    private final String DTO_UPDATE     = "update";
    private final String DTO_DELETE     = "delete";

    public abstract DtoService<T, ID, DTO> getService();

    private ResponseEntity<DTO> dtoResponse(DTO dto, Principal principal, String action) {
        getService().addResponseInfo(dto, principal,action);
        return ResponseEntity.ok().body(dto);
    }

    private Mono<ResponseEntity<DTO>> dtoErrorResponse(DTO dto, Principal principal, String action, String column, Throwable e) {
        getService().addResponseInfo(dto, principal, action);
        return Mono.just(ResponseEntity.badRequest()
                .body(getService().addErrorInfo(dto, column, e.getMessage())));
    }

   @GetMapping
    public Flux<DTO> findAll() {
        return getService().findAll();
    }

    @GetMapping(value = "/"+DTO_COUNT)
    public Mono<ResponseEntity<DTO>> count(Principal principal) {
        return count(getService().getDto(null), principal);
    }

    @PostMapping(value = "/"+DTO_COUNT)
    public Mono<ResponseEntity<DTO>> count(@RequestBody DTO dto, Principal principal) {
        return getService().count(dto)
                .map(mapDto -> dtoResponse(mapDto, principal, DTO_COUNT));
    }

    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<DTO>> get(@PathVariable ID id, Principal principal) {
        return get(getService().getDto(id), principal);
    }

    @PostMapping(value = "/"+DTO_GET)
    public Mono<ResponseEntity<DTO>> get(@RequestBody DTO dto, Principal principal) {
        return getService().get(dto)
                .map(mapDto -> dtoResponse(mapDto, principal, DTO_GET))

                .onErrorResume(e -> dtoErrorResponse(dto, principal, DTO_GET, DtoService.DTO_ID, e));
    }

    @GetMapping(value = "/"+DTO_EXISTS+"/{id}")
    public Mono<ResponseEntity<DTO>> exists(@PathVariable ID id, Principal principal) {
        return exists(getService().getDto(id), principal);
    }

    @PostMapping(value = "/"+DTO_EXISTS)
    public Mono<ResponseEntity<DTO>> exists(@RequestBody DTO dto, Principal principal) {
        return getService().exists(dto)
                .map(mapDto -> dtoResponse(mapDto, principal, DTO_EXISTS))

                .onErrorResume(e -> dtoErrorResponse(dto, principal, DTO_EXISTS, DtoService.DTO_ID, e));
    }

    @PostMapping
    public Mono<ResponseEntity<DTO>> insert(@RequestBody DTO dto, Principal principal) {
        return getService().insert(dto)
                .map(mapDto -> dtoResponse(mapDto, principal, DTO_INSERT))

                .onErrorResume(e -> dtoErrorResponse(dto, principal, DTO_INSERT, DtoService.DTO_ANY, e));
    }

    @PutMapping
    public Mono<ResponseEntity<DTO>> update(@RequestBody DTO dto, Principal principal) {
        return getService().update(dto)
                .map(mapDto -> dtoResponse(mapDto, principal, DTO_UPDATE))

                .onErrorResume(e -> dtoErrorResponse(dto, principal, DTO_UPDATE, DtoService.DTO_ID, e));
    }

    @DeleteMapping
    public Mono<ResponseEntity<DTO>> delete(@RequestBody DTO dto, Principal principal) {
        return getService().delete(dto)
                .map(mapDto -> dtoResponse(mapDto, principal, DTO_DELETE))

                .onErrorResume(e -> dtoErrorResponse(dto, principal, DTO_DELETE, DtoService.DTO_ID, e));
    }
}
