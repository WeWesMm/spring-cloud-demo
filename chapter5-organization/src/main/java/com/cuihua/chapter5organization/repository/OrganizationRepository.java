package com.cuihua.chapter5organization.repository;

import com.cuihua.chapter5organization.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lin Jing
 * @date 2020/10/23 10:49
 */
@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
}
