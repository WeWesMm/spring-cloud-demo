package com.cuihua.chapter2.service;

import com.cuihua.chapter2.model.License;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Lin Jing
 * @date 2020/10/15 10:10
 */
@Service
public class LicenseService {

    public License getLicense(String licenseId) {

        return new License()
                .withId(licenseId)
                .withOrganizationId(UUID.randomUUID().toString())
                .withLicenseType("PerSeat")
                .withProductName("Test Product Name");
    }

    public void saveLicense(License license) {

    }

    public void updateLicense(License license) {

    }

    public void deleteLicense(License license) {

    }
}
