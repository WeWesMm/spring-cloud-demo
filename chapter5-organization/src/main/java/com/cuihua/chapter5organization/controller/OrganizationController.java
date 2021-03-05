package com.cuihua.chapter5organization.controller;

import com.cuihua.chapter5organization.model.Organization;
import com.cuihua.chapter5organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Lin Jing
 * @date 2020/10/23 11:20
 */
@RestController
@RequestMapping("/v1/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {

        return organizationService.getOrg(organizationId);
    }

    @PostMapping("/{organizationId}")
    public void saveOrganization(@PathVariable("organizationId") String organizationId, @RequestBody Organization organization) {

    }
}
