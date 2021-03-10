package com.cuihua.chapter6orgservicenew.repository;

import com.cuihua.chapter6orgservicenew.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Lin Jing
 * @date 2021/1/22 下午2:05
 */
@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
}
