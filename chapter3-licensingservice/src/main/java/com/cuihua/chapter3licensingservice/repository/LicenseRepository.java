package com.cuihua.chapter3licensingservice.repository;

import com.cuihua.chapter3licensingservice.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lin Jing
 * @date 2020/10/19 10:35
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {

    /**
     * 查询
     *
     * @param organizationId 组织 id
     * @return
     */
    List<License> findByOrganizationId(String organizationId);

    /**
     * 查询
     *
     * @param organizationId 组织 id
     * @param licenseId      许可证 id
     * @return
     */
    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);

}
