package com.dotmatics.mstenant.entity.impl;

import com.dotmatics.mstenant.entity.Audit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table("tenant_with_audit")
public class TenantWithAudit extends Audit {
    @Id
    private Long id;
    private String name;
    private String email;
}
