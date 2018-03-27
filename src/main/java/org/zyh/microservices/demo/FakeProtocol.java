package org.zyh.microservices.demo;

import org.zyh.microservices.poc.annotation.Protocol;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Protocol(name = "fake")
public @interface FakeProtocol {

}