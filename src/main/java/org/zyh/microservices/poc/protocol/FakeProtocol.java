package org.zyh.microservices.poc.protocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zyh
 */
public class FakeProtocol implements Protocol {

    private final Map<Class, Object> interfaceProviderMap = new HashMap<>();

    @Override
    public String name() {
        return "fake";
    }

    @Override
    public <T> T provide(Class apiInterface, final T provider) {
        interfaceProviderMap.put(apiInterface, provider);
        return provider;
    }

    @Override
    public <T> T reference(final Class<T> apiInterface) {
        return (T) Proxy
                .newProxyInstance(apiInterface.getClassLoader(), new Class[]{apiInterface}, new InvocationHandler() {
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        return interfaceProviderMap.get(apiInterface).getClass()
                                .getDeclaredMethod(method.getName(), method.getParameterTypes())
                                .invoke(interfaceProviderMap.get(apiInterface), objects);
                    }
                });
    }
}
