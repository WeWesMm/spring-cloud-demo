package com.cuihua.chapter6organization.repository;

import com.cuihua.chapter6organization.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Lin Jing
 * @date 2021/1/19 下午4:16
 */
@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {
    /**
     * 通过 id 查询组织信息
     *
     * @param organizationId 组织 id
     * @return 组织信息
     */
    Optional<Organization> findById(String organizationId);
}
