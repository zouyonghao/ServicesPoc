package org.zyh.microservices.poc.model;

/**
 * @author z672909
 */
public class ProviderDefinition extends AbstractServiceDefinition {

    private Class<?> originalClass;

    public Class<?> getOriginalClass() {
        return originalClass;
    }

    public void setOriginalClass(Class<?> originalClass) {
        this.originalClass = originalClass;
    }

    @Override
    public Type type() {
        return Type.PROVIDER;
    }
}
