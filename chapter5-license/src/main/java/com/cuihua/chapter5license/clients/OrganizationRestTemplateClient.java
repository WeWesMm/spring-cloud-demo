package com.cuihua.chapter5license.clients;

import com.cuihua.chapter5license.model.Organization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Lin Jing
 * @date 2020/10/23 10:24
 */
@Slf4j
@Component
public class OrganizationRestTemplateClient {

    @Autowired
    private RestTemplate restTemplate;

    public Organization getOrganization(String organizationId) {

        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                "http://organizationservice/v1/organizations/{organizationId}",
                HttpMethod.GET,
                null, Organization.class, organizationId
        );

        return restExchange.getBody();
    }
}
