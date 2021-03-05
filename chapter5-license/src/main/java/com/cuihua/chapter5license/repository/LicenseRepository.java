package com.cuihua.chapter5license.repository;

import com.cuihua.chapter5license.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lin Jing
 * @date 2020/10/23 10:21
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, String> {

    /**
     * 通过组织 id 查找
     *
     * @param organizationId 组织 id
     * @return 许可证信息
     */
    List<License> findByOrganizationId(String organizationId);

    /**
     * 通过组织及许可证 id 查找
     *
     * @param organizationId 组织id
     * @param licenseId      许可证id
     * @return 许可证信息
     */
    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
