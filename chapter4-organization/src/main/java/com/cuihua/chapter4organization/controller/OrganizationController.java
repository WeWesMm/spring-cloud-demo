package com.cuihua.chapter4organization.controller;

import com.cuihua.chapter4organization.model.Organization;
import com.cuihua.chapter4organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Lin Jing
 * @date 2020/10/22 10:03
 */
@RestController
@RequestMapping(value = "/v1/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {

        return organizationService.getOrg(organizationId);
    }

    @PutMapping("/organization")
    public void updateOrganization(@RequestBody Organization organization) {

        organizationService.updateOrg(organization);
    }

    @PostMapping("/organization")
    public void saveOrganization(@RequestBody Organization organization) {

        organizationService.saveOrg(organization);
    }

    @DeleteMapping("/{organizationId}")
    public void deleteOrganization(@PathVariable("organizationId") String organizationId, @RequestBody Organization organization) {

        organizationService.deleteOrg(organization);
    }
}
