package org.zyh.microservices.poc.registry;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.zyh.microservices.poc.annotation.Api;
import org.zyh.microservices.poc.annotation.Provider;
import org.zyh.microservices.poc.annotation.Reference;
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
    private final Map<Class, Object> referenceMap = new HashMap<>();
    private final Map<Class, Object> classInstanceMap = new HashMap<>();

    public void scan(String packageName) {
        for (String s : new FastClasspathScanner(packageName).scan().getNamesOfAllClasses()) {
            try {
                Class clazz = Class.forName(s);
                Provider providerAnno = findAnnotation(clazz, Provider.class);
                if (providerAnno != null) {
                    // find a provider
                    // 1. check metadata
                    SoftImpl softImpl = findAnnotation(clazz, SoftImpl.class);
                    if (softImpl == null) {
                        throw new IllegalStateException("not a provider");
                    }
                    String serviceName = softImpl.value();
                    Class apiInterface = Class.forName(serviceName);
                    org.zyh.microservices.poc.annotation.Protocol protocol =
                            findAnnotation(apiInterface, org.zyh.microservices.poc.annotation.Protocol.class);
                    if (null == protocol) {
                        throw new IllegalStateException("must provide with a protocol");
                    }
                    // 2. try get a protocol
                    Protocol protocolInstance = getProtocol(protocol.name());
                    // 3. try get a object
                    Object instance = getInstance(clazz);
                    // 4. try provide
                    Object providerObject = protocolInstance.provide(apiInterface, instance);
                    // 5. add provider, may add to other container
                    addProvider(apiInterface, providerObject);
                    continue;
                }
                Reference reference = findAnnotation(clazz, Reference.class);
                if (reference != null) {
                    // find a reference
                    // 1. check meta
                    Api api = findAnnotation(clazz, Api.class);
                    if (api == null) {
                        throw new IllegalStateException("not a reference");
                    }
                    // 2. try get a protocol
                    org.zyh.microservices.poc.annotation.Protocol protocol =
                            findAnnotation(clazz, org.zyh.microservices.poc.annotation.Protocol.class);
                    if (null == protocol) {
                        throw new IllegalStateException("must reference with a protocol");
                    }
                    Protocol protocolInstance = getProtocol(protocol.name());
                    // 3. try reference
                    Object referenceObject = protocolInstance.reference(clazz);
                    // 4. add reference
                    addReference(clazz, referenceObject);
                }
            } catch (ClassNotFoundException e) {
                // ignore
            }
        }
    }

    protected void addReference(Class clazz, Object referenceObject) {
        referenceMap.put(clazz, referenceObject);
    }

    public <T> T getReference(Class<T> apiInterface) {
        return (T) referenceMap.get(apiInterface);
    }

    protected Object getInstance(Class clazz) {
        return classInstanceMap.get(clazz);
    }

    public void putInstance(Class userClazz, Object instance) {
        classInstanceMap.put(userClazz, instance);
    }

    protected Protocol getProtocol(String name) {
        return protocolMap.get(name);
    }

    protected void addProvider(Class apiInterface, Object provider) {
        providerMap.put(apiInterface, provider);
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
    private static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationType,
                                                           Set<Annotation> visited) {
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
