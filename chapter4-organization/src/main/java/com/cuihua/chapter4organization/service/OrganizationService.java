package com.cuihua.chapter4organization.service;

import com.cuihua.chapter4organization.model.Organization;
import com.cuihua.chapter4organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lin Jing
 * @date 2020/10/22 09:57
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

    public void saveOrg(Organization organization) {

        organizationRepository.save(organization);
    }

    public void updateOrg(Organization organization) {

        organizationRepository.save(organization);
    }

    public void deleteOrg(Organization organization) {

        organizationRepository.deleteById(organization.getId());
    }

}

