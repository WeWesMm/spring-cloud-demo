package com.cuihua.chapter6organization.controller;

import com.cuihua.chapter6organization.model.Organization;
import com.cuihua.chapter6organization.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Lin Jing
 * @date 2021/1/19 下午4:22
 */
@Slf4j
@RestController
@RequestMapping("/v1/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService orgService;

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        log.info("Looking up data for org {}", organizationId);

        Organization org = orgService.getOrg(organizationId);
        org.setContactName("OLD::" + org.getContactName());
        return org;
    }

    @PutMapping(value = "/{organizationId}")
    public void updateOrganization(@PathVariable("organizationId") String orgId, @RequestBody Organization org) {
        orgService.updateOrg(org);
    }

    @PostMapping(value = "/{organizationId}")
    public void saveOrganization(@RequestBody Organization org) {
        orgService.saveOrg(org);
    }

    @DeleteMapping(value = "/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("organizationId") String orgId, @RequestBody Organization org) {
        orgService.deleteOrg(org);
    }
}
