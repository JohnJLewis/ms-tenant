package com.dotmatics.mstenant.controller.impl;

import com.dotmatics.mstenant.controller.ApiController;
import com.dotmatics.mstenant.entity.impl.Tenant;
import com.dotmatics.mstenant.model.impl.TenantDto;
import com.dotmatics.mstenant.service.DtoService;
import com.dotmatics.mstenant.service.impl.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/tenant")
@RequiredArgsConstructor
@Slf4j
public class TenantController extends ApiController<Tenant, Long, TenantDto> {
    private final TenantService service;

    @Override
    public DtoService<Tenant, Long, TenantDto> getService() {
        return service;
    }
}
