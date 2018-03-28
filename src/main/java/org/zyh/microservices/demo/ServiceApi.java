package org.zyh.microservices.demo;


import org.zyh.microservices.poc.annotation.Api;
import org.zyh.microservices.poc.annotation.FlexibleApi;
import org.zyh.microservices.poc.annotation.Reference;

/**
 * @author zyh
 */
@FakeProtocol
@Api
@FlexibleApi("test.Service")
@Reference
public interface ServiceApi {

    String test();

}
