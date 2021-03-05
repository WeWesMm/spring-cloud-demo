package com.cuihua.chapter5organization.service;

import com.cuihua.chapter5organization.model.Organization;
import com.cuihua.chapter5organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Lin Jing
 * @date 2020/10/23 11:18
 */
@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization getOrg(String organizationId) {

        if (organizationRepository.findById(organizationId).isPresent()) {

            return organizationRepository.findById(organizationId).get();
        }
        return null;
    }

    public void saveOrg(Organization org) {
        org.setId(UUID.randomUUID().toString());

        organizationRepository.save(org);

    }

    public void updateOrg(Organization org) {
        organizationRepository.save(org);
    }

    public void deleteOrg(Organization org) {
        organizationRepository.delete(org);
    }
}
