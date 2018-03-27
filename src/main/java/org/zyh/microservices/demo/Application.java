package org.zyh.microservices.demo;

import org.zyh.microservices.poc.protocol.FakeProtocol;
import org.zyh.microservices.poc.protocol.Protocol;
import org.zyh.microservices.poc.registry.ServiceRegistry;

/**
 * @author zyh
 */
public class Application {
    public static void main(String[] args) throws ClassNotFoundException {
        ServiceRegistry registry = new ServiceRegistry();
        ServiceProvider provider = new ServiceProvider();
        Protocol protocol = new FakeProtocol();
        registry.addProtocol(protocol);
        registry.addProvider(provider);
        ServiceApi service = registry.getProvider(ServiceApi.class);
        System.out.println(service.test());
    }
}
