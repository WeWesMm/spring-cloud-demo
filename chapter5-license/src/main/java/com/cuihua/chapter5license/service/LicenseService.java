package com.cuihua.chapter5license.service;

import com.cuihua.chapter5license.clients.OrganizationRestTemplateClient;
import com.cuihua.chapter5license.config.ServerConfig;
import com.cuihua.chapter5license.model.License;
import com.cuihua.chapter5license.model.Organization;
import com.cuihua.chapter5license.repository.LicenseRepository;
import com.cuihua.chapter5license.utils.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Lin Jing
 * @date 2020/10/23 10:51
 */
@Service
@Slf4j
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private OrganizationRestTemplateClient organizationRestClient;

    @Autowired
    private ServerConfig config;

    public License getLicense(String organizationId, String licenseId) {

        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        Organization org = getOrganization(organizationId);

        return license
                .withOrganizationName(org.getName())
                .withContactName(org.getContactName())
                .withContactEmail(org.getContactEmail())
                .withContactPhone(org.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    @HystrixCommand(
            commandProperties = {
                    // 设置 Hystrix 断路器的超时时间，以毫秒为单位
                    // @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "12000")
                    // 设置在窗口中必须发生的连续调用次数
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    // 设置跳闸前必须达到的调用失败百分比
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    // 设置恢复健康前的休眠时间
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
                    // 设置窗口时间
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    // 设置在窗口中收集统计信息的次数
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")
            },
            // fallbackMethod 定义了类中的一个方法，如果来自 Hystrix 的调用失败，那么就会调用该方法。
            fallbackMethod = "buildFallbackLicenseList",
            // 定义线程池的唯一名称
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties = {
                    // 定义线程池中线程的最大数量
                    @HystrixProperty(name = "coreSize", value = "30"),
                    // 定义一个位于线程池浅的队列，它可以对传入的请求进行排序
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            }
    )
    public List<License> getLicensesByOrg(String organizationId) {
        log.info("LicenseService.getLicensesByOrg  Correlation id: {}.", UserContextHolder.getContext().getCorrelationId());
        // randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    private void randomlyRunLong() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        if (randomNum == 3) {
            sleep();
        }
    }

    private void sleep() {

        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @HystrixCommand
    private Organization getOrganization(String organizationId) {
        return organizationRestClient.getOrganization(organizationId);
    }

    /**
     * 后背策略方法实现，简单地返回一个许可对象
     *
     * @param organizationId 组织 id
     * @return 许可证
     */
    private List<License> buildFallbackLicenseList(String organizationId) {

        List<License> licenseList = new ArrayList<>();
        License license = new License()
                .withId("0000000-00-00000")
                .withOrganizationId(organizationId)
                .withProductName("Sorry no licensing information currently available");
        licenseList.add(license);
        return licenseList;
    }

}
