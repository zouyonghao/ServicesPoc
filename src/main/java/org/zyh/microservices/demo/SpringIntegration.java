package org.zyh.microservices.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.zyh.microservices.integration.SpringServiceRegistry;
import org.zyh.microservices.poc.protocol.FakeProtocol;

/**
 * @author z672909
 */
@SpringBootApplication
public class SpringIntegration implements CommandLineRunner {

    @Autowired
    private ServiceApi serviceApi;

    @Override
    public void run(String... args) {
        System.out.println(serviceApi.test());
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegration.class, args);
    }

    @Bean("protocol")
    public FakeProtocol getProtocol() {
        FakeProtocol protocol = new FakeProtocol();
        registry().addProtocol(protocol);
        return protocol;
    }

    @Bean
    public SpringServiceRegistry registry() {
        return new SpringServiceRegistry();
    }

    @Bean
    public ServiceProvider provider() {
        return new ServiceProvider();
    }

    @Bean
    @DependsOn("protocol")
    public ServiceApi serviceApi() {
        registry().scan("org.zyh.microservices.demo");
        return registry().getReference(ServiceApi.class);
    }
}
