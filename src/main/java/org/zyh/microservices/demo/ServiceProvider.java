package org.zyh.microservices.demo;

import org.zyh.microservices.poc.annotation.Provider;
import org.zyh.microservices.poc.annotation.SoftImpl;

/**
 * @author zyh
 */
@Provider
@SoftImpl("org.zyh.microservices.demo.ServiceApi")
public class ServiceProvider {

    public String test() {
        return "test";
    }

}
