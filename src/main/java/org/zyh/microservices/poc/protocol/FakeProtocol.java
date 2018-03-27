package org.zyh.microservices.poc.protocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zyh
 */
public class FakeProtocol implements Protocol {
    public String name() {
        return "fake";
    }

    public <T> T provide(Class apiInterface, final T provider) {
        return (T) Proxy.newProxyInstance(apiInterface.getClassLoader(), new Class[]{apiInterface}, new InvocationHandler() {
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                return provider.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes()).invoke(provider, objects);
            }
        });
    }

    public <T> T reference(T object) {
        return object;
    }
}
