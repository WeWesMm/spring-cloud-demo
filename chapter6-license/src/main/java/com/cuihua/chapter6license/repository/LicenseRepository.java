package com.cuihua.chapter6license.repository;

import com.cuihua.chapter6license.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lin Jing
 * @date 2021/1/15 下午5:08
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {

    /**
     * 根据组织 id 获取许可证信息
     *
     * @param organizationId 组织 id
     * @return 许可证信息
     */
    List<License> findByOrganizationId(String organizationId);

    /**
     * 根据组织 id 及许可证 id 获取许可证信息
     *
     * @param organizationId 组织 id
     * @param licenseId      许可证 id
     * @return 许可证信息
     */
    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);

}
