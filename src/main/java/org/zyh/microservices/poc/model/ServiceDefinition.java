package org.zyh.microservices.poc.model;

/**
 * @author z672909
 */
public interface ServiceDefinition {

    String name();

    String serviceName();

    String protocol();

    Class service();

    Type type();

    enum Type {
        PROVIDER, REFERENCE
    }

}
