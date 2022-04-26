package com.dotmatics.mstenant.model;

import com.dotmatics.mstenant.model.utility.DtoSort;
import com.dotmatics.mstenant.model.utility.DtoWhere;
import com.fasterxml.jackson.annotation.JsonInclude;
import reactor.core.publisher.Mono;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoList<DTO> extends BaseDto {
    public List<DtoWhere> where;
    public List<DtoSort> orderBy;
    public Mono<List<DTO>> list;

    public DtoList() {
    }

    public DtoList(Mono<List<DTO>> list) {
        this.list = list;
    }
}
