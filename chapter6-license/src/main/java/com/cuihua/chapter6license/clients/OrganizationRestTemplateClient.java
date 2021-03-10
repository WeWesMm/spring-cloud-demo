package com.cuihua.chapter6license.clients;

import com.cuihua.chapter6license.model.Organization;
import com.cuihua.chapter6license.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Lin Jing
 * @date 2021/1/19 下午2:35
 */
@Slf4j
@Component
public class OrganizationRestTemplateClient {

    @Autowired
    RestTemplate restTemplate;

    public Organization getOrganization(String organizationId) {
        log.info(">>> In Licensing Service.getOrganization: {}. Thread Id: {}",
                UserContextHolder.getContext().getCorrelationId(), Thread.currentThread().getId());

        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                "http://zuulservice/api/organization/v1/organizations/{organizationId}",
                HttpMethod.GET,
                null, Organization.class, organizationId);
        return restExchange.getBody();
    }
}
