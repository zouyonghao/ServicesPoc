package org.zyh.microservices.demo2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.zyh.microservices.integration.ServiceBeanFactoryPostProcessor;
import org.zyh.microservices.poc.protocol.FakeProtocol;
import org.zyh.microservices.poc.protocol.Protocol;

/**
 * @author z672909
 */
@SpringBootApplication
public class SpringIntegration2 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("aaa");
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegration2.class, args);
    }

    @Bean
    public ServiceBeanFactoryPostProcessor serviceBeanFactoryPostProcessor() {
        return new ServiceBeanFactoryPostProcessor();
    }

    @Bean
    public Protocol fakeProtocol() {
        return new FakeProtocol();
    }
}
