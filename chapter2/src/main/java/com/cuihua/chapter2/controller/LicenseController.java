package com.cuihua.chapter2.controller;

import com.cuihua.chapter2.model.License;
import com.cuihua.chapter2.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Lin Jing
 * @date 2020/10/14 16:33
 */
@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @GetMapping("/{licenseId}")
    public License getLicense(@PathVariable("organizationId") String organizationId,
                              @PathVariable("licenseId") String licenseId) {
        return licenseService.getLicense(licenseId);
        // return new License()
        //         .withId(licenseId)
        //         .withProductName("Teleco")
        //         .withLicenseType("Seat")
        //         .withOrganizationId("TestOrg");
    }

    @PutMapping("/{licenseId}")
    public String updateLicenses(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId) {
        return "This is the put";
    }

    @PostMapping("/{licenseId}")
    public String saveLicenses(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId) {
        return "This is the post";
    }

    @DeleteMapping("/{licenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteLicenses(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId) {
        return "This is the Delete";
    }
}
