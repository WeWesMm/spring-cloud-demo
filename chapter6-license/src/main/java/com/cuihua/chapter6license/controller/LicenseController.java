package com.cuihua.chapter6license.controller;

import com.cuihua.chapter6license.model.License;
import com.cuihua.chapter6license.services.LicenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Lin Jing
 * @date 2021/1/19 下午3:21
 */
@Slf4j
@RestController
@RequestMapping(value = "v1/organizations/{organizationId}/licenses")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/")
    public List<License> getLicense(@PathVariable("organizationId") String organizationId) {
        return licenseService.getLicenseByOrg(organizationId);
    }

    @GetMapping("/{licenseId}")
    public License getLicense(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId) {
        log.info("Found tmx-correlation-id in license-service-controller: {} ", request.getHeader("tmx-correlation-id"));

        return licenseService.getLicense(organizationId, licenseId);
    }

    @PutMapping("/{licenseId}")
    public void updateLicenses(@PathVariable("licenseId") String licenseId, @RequestBody License license) {
        licenseService.updateLicense(license);
    }

    @PostMapping("/")
    public void saveLicenses(@RequestBody License license) {
        licenseService.saveLicense(license);
    }

    @DeleteMapping("/{licenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLicenses(@PathVariable("licenseId") String licenseId, @RequestBody License license) {
        licenseService.deleteLicense(license);
    }
}
