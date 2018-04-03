package org.zyh.microservices.demo;

import org.zyh.microservices.poc.protocol.FakeProtocol;
import org.zyh.microservices.poc.registry.ServiceRegistry;

/**
 * @author zyh
 */
public class Application {
    public static void main(String[] args) {
        ServiceRegistry registry = new ServiceRegistry();

        ServiceProvider provider = new ServiceProvider();
        registry.addInstance(ServiceProvider.class, provider);
        registry.addProtocol(new FakeProtocol());

        registry.scan("org.zyh.microservices.demo");
        ServiceApi service = registry.getReference(ServiceApi.class);
        System.out.println(service.test());
    }
}
