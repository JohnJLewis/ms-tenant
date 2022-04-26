package com.dotmatics.mstenant.model.impl;

import com.dotmatics.mstenant.model.BaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenantDto extends BaseDto implements Serializable {
    public Long id;
    public String name;
    public String email;
}
