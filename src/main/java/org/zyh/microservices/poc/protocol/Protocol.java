package org.zyh.microservices.poc.protocol;

/**
 * @author zyh
 */
public interface Protocol {

    String name();

    <T> T provide(Class apiInterface, T object);

    <T> T reference(T object);

}
