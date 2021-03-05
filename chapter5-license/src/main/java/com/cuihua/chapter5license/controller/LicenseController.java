package com.cuihua.chapter5license.controller;

import com.cuihua.chapter5license.config.ServerConfig;
import com.cuihua.chapter5license.model.License;
import com.cuihua.chapter5license.service.LicenseService;
import com.cuihua.chapter5license.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Lin Jing
 * @date 2020/10/23 11:04
 */
@Slf4j
@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @Autowired
    private ServerConfig serverConfig;

    @GetMapping("/")
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        log.info("LicenseServiceController Correlation id: {}.", UserContextHolder.getContext().getCorrelationId());
        return licenseService.getLicensesByOrg(organizationId);
    }

    @GetMapping("/{licenseId}")
    public License getLicense(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId) {
        return licenseService.getLicense(organizationId, licenseId);
    }
}
