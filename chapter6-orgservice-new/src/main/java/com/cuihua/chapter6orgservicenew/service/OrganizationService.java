package com.cuihua.chapter6orgservicenew.service;

import com.cuihua.chapter6orgservicenew.model.Organization;
import com.cuihua.chapter6orgservicenew.repository.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Lin Jing
 * @date 2021/1/22 下午2:07
 */
@Slf4j
@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization getOrg(String organizationId) {
        return organizationRepository.findById(organizationId).get();
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
