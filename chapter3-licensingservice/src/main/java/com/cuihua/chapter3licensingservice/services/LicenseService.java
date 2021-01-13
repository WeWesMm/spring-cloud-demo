package com.cuihua.chapter3licensingservice.services;

import com.cuihua.chapter3licensingservice.config.ServiceConfig;
import com.cuihua.chapter3licensingservice.model.License;
import com.cuihua.chapter3licensingservice.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Lin Jing
 * @date 2020/10/19 10:50
 */
@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private ServiceConfig config;

    public License getLicense(String organizationId, String licenseId) {

        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(config.getExampleProperty());
    }

    public List<License> getLicenseByOrg(String organizationId) {

        return licenseRepository.findByOrganizationId(organizationId);
    }

    public void saveLicense(License license) {

        license.withId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    public void updateLicense(License license) {

        licenseRepository.save(license);
    }

    public void deleteLicense(License license) {

        licenseRepository.delete(license);
    }
}
