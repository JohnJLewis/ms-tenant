package com.dotmatics.mstenant.controller.impl;

import com.dotmatics.mstenant.controller.ApiController;
import com.dotmatics.mstenant.entity.impl.TenantWithAudit;
import com.dotmatics.mstenant.model.impl.TenantWithAuditDto;
import com.dotmatics.mstenant.service.DtoService;
import com.dotmatics.mstenant.service.impl.TenantWithAuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/tenantWithAudit")
@RequiredArgsConstructor
@Slf4j
public class TenantWithAuditController extends ApiController<TenantWithAudit, Long, TenantWithAuditDto> {
    private final TenantWithAuditService service;

    @Override
    public DtoService<TenantWithAudit, Long, TenantWithAuditDto> getService() {
        return service;
    }
}
