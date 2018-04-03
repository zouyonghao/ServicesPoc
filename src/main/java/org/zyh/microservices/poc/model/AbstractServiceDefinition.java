package org.zyh.microservices.poc.model;

/**
 * @author z672909
 */
public abstract class AbstractServiceDefinition implements ServiceDefinition {

    private String name;
    private String protocol;
    private String serviceName;
    private Class service;

    public void setService(Class service) {
        this.service = service;
    }

    @Override
    public Class service() {
        return service;
    }

    @Override
    public String serviceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String protocol() {
        return protocol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
