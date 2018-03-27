package org.zyh.microservices.poc.registry;

import org.zyh.microservices.poc.annotation.SoftImpl;
import org.zyh.microservices.poc.protocol.Protocol;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author zyh
 */
public class ServiceRegistry {

    private final Map<String, Protocol> protocolMap = new HashMap<String, Protocol>();
    private final Map<Class, Object> providerMap = new HashMap<Class, Object>();

    public void addProvider(Object provider) throws ClassNotFoundException {
        Class userClass = provider.getClass();
        SoftImpl softImpl = (SoftImpl) userClass.getAnnotation(SoftImpl.class);
        if (softImpl == null) {
            throw new IllegalStateException("not a provider");
        }
        String serviceName = softImpl.value();
        Class apiInterface = Class.forName(serviceName);
        org.zyh.microservices.poc.annotation.Protocol protocol = findAnnotation(apiInterface, org.zyh.microservices.poc.annotation.Protocol.class);
        Protocol protocolInstance = protocolMap.get(protocol.name());
        Object proxyInstance = protocolInstance.provide(apiInterface, provider);
        providerMap.put(apiInterface, proxyInstance);
    }

    public <T> T getProvider(Class<T> serviceApi) {
        return (T) providerMap.get(serviceApi);
    }

    public void addProtocol(Protocol protocol) {
        protocolMap.put(protocol.name(), protocol);
    }

    public static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationType) {
        return findAnnotation(clazz, annotationType, new HashSet<Annotation>());
    }

    @SuppressWarnings("unchecked")
    private static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationType, Set<Annotation> visited) {
        try {
            Annotation[] anns = clazz.getDeclaredAnnotations();
            for (Annotation ann : anns) {
                if (ann.annotationType() == annotationType) {
                    return (A) ann;
                }
            }
            for (Annotation ann : anns) {
//                if (!isInJavaLangAnnotationPackage(ann) && visited.add(ann)) {
                if (visited.add(ann)) {
                    A annotation = findAnnotation(ann.annotationType(), annotationType, visited);
                    if (annotation != null) {
                        return annotation;
                    }
                }
            }
        } catch (Exception ex) {
//            handleIntrospectionFailure(clazz, ex);
            return null;
        }

        for (Class<?> ifc : clazz.getInterfaces()) {
            A annotation = findAnnotation(ifc, annotationType, visited);
            if (annotation != null) {
                return annotation;
            }
        }

        Class<?> superclass = clazz.getSuperclass();
        if (superclass == null || Object.class == superclass) {
            return null;
        }
        return findAnnotation(superclass, annotationType, visited);
    }
}
