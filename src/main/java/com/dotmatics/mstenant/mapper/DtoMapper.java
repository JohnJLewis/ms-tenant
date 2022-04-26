package com.dotmatics.mstenant.mapper;

import com.dotmatics.mstenant.entity.Audit;
import com.dotmatics.mstenant.messages.Messages;
import com.dotmatics.mstenant.model.AuditDto;
import com.dotmatics.mstenant.model.BaseDto;
import com.dotmatics.mstenant.service.DtoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class DtoMapper<T, ID, DTO extends BaseDto> {
    public static final String SPACER                       = " ";
    public static final String MSG_COLUMN_MISSING           = "api.error.column.missing";
    public static final String MSG_INVALID_NUMBER           = "api.error.id.invalid.number";

    protected Map<String, String> columnMap = new HashMap<>();

    public abstract DTO getDto(ID id);
    public abstract Long getDtoId(DTO dto);
    public abstract boolean validateData(DTO dto);
    public abstract Mono<DTO> toDto(T entity);
    public abstract T toEntity(DTO dto);
    public abstract T applyUpdates(T entity, DTO dto);

    public boolean validate(DTO dto) {
        if (getDtoId(dto)==null) addError(dto, DtoService.DTO_ID, Messages.get(MSG_COLUMN_MISSING), MSG_COLUMN_MISSING);
        return ! isError(dto);
    }

    public boolean isError(BaseDto dto) {
        BaseDto baseDto = getBaseDto(dto);
        return ! baseDto.apiResult.success;
    }

    public void addAction(BaseDto dto, String action, String entity) {
        BaseDto baseDto = getBaseDto(dto);
        baseDto.apiResult.action = action;
        baseDto.apiResult.entity = entity;
    }

    public void addUserName(BaseDto dto, String username) {
        BaseDto baseDto = getBaseDto(dto);
        baseDto.apiResult.username = username;
    }

    public void addCount(BaseDto dto, Long count) {
        BaseDto baseDto = getBaseDto(dto);
        baseDto.apiResult.count = count;
    }

    public void setSuccessMessage(BaseDto dto, String message) {
        getBaseDto(dto).apiResult.message = message;;
    }


    public void addError(BaseDto dto, String column, String error) {
        addError(dto, column, error,null);
    }

    public void addError(BaseDto dto, String column, String error, String code) {
        addError(dto, column, error, code, null);
    }

    public void addError(BaseDto dto, String column, String error, String code, Object[] args) {
        BaseDto baseDto = getBaseDto(dto);
        if (baseDto.apiResult.errors==null) {
            baseDto.apiResult.errors = new ArrayList<>();
        }

        baseDto.apiResult.errors.add(new BaseDto.BaseDtoResult.BaseDtoError(column, error, code, args));
        baseDto.apiResult.success = false;
    }

    protected BaseDto getBaseDto(BaseDto dto) {
        if (dto.apiResult==null) {
            dto.apiResult = new BaseDto.BaseDtoResult();
            dto.apiResult.username = "anonymousUser";
        }

        return dto;
    }

    public void setAuditData(AuditDto dto, Audit entity) {
        dto.createdBy = entity.getCreatedBy();
        dto.createdDate = entity.getCreatedDate();
        dto.lastModifiedBy = entity.getLastModifiedBy();
        dto.lastModifiedDate = entity.getLastModifiedDate();
        dto.version = entity.getVersion();
    }

//    public List<DtoSort> setSorts(List<Sort.Order> orders) {
//        return orders.stream().map(DtoSort::new).collect(Collectors.toList());
//    }
//

    public String getEntityColumn(String dtoColumn) {
        if (columnMap.containsKey(dtoColumn)) return columnMap.get(dtoColumn);
        return dtoColumn;
    }
}
