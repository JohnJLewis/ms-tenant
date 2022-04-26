package com.dotmatics.mstenant.model;

import java.time.LocalDateTime;

public abstract class AuditDto extends BaseDto {
    public String createdBy;
    public LocalDateTime createdDate;
    public String lastModifiedBy;
    public LocalDateTime lastModifiedDate;
    public Integer version;

    public AuditDto() {
//        createdDate = LocalDateTime.now();
//        lastModifiedDate = LocalDateTime.now();
    }
}
