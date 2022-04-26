package com.dotmatics.mstenant.model.impl;

import com.dotmatics.mstenant.model.AuditDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenantWithAuditDto extends AuditDto implements Serializable {
    public Long id;
    public String name;
    public String email;
}
