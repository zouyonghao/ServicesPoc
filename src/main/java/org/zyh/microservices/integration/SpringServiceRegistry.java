package org.zyh.microservices.integration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.zyh.microservices.poc.registry.ServiceRegistry;

/**
 * @author z672909
 */
public class SpringServiceRegistry extends ServiceRegistry implements ApplicationContextAware {

    private ApplicationContext context;

    @Override
    protected Object getInstance(Class clazz) {
        Object instance = super.getInstance(clazz);
        if (instance == null) {
            instance = context.getAutowireCapableBeanFactory().getBean(clazz);
        }
        addInstance(clazz, instance);
        return instance;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
