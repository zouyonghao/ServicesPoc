package org.zyh.microservices.demo;


import org.zyh.microservices.poc.annotation.Api;
import org.zyh.microservices.poc.annotation.FlexibleApi;

/**
 * @author zyh
 */
@FakeProtocol
@Api
@FlexibleApi("test.Service")
public interface ServiceApi {

    String test();

}
