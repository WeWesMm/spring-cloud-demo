package com.cuihua.chapter6orgservicenew.controller;

import com.cuihua.chapter6orgservicenew.model.Organization;
import com.cuihua.chapter6orgservicenew.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Lin Jing
 * @date 2021/1/22 下午2:11
 */
@Slf4j
@RestController
@RequestMapping("/v1/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {

        Organization org = organizationService.getOrg(organizationId);
        org.setContactName("NEW::" + org.getContactName());
        return org;
    }

    @PutMapping(value = "/{organizationId}")
    public void updateOrganization(@PathVariable("organizationId") String orgId, @RequestBody Organization org) {
        organizationService.updateOrg(org);
    }

    @PostMapping(value = "/{organizationId}")
    public void saveOrganization(@RequestBody Organization org) {
        organizationService.saveOrg(org);
    }

    @DeleteMapping(value = "/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("organizationId") String orgId, @RequestBody Organization org) {
        organizationService.deleteOrg(org);
    }
}
