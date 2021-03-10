package com.cuihua.chapter6zuulsvr.model;

/**
 * @author Lin Jing
 * @date 2021/1/21 下午3:56
 */
public class AbTestingRoute {
    /**
     * 目标服务ID
     */
    String serviceName;
    /**
     * 活跃状态
     */
    String active;
    String endpoint;
    /**
     * 权重
     */
    Integer weight;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
