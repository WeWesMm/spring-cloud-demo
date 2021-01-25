package com.cuihua.chapter4organization.repository;

import com.cuihua.chapter4organization.model.Organization;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Lin Jing
 * @date 2020/10/22 09:51
 */
public interface OrganizationRepository extends CrudRepository<Organization, String> {

}
