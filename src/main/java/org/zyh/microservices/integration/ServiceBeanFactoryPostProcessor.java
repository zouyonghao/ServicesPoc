package org.zyh.microservices.integration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.zyh.microservices.poc.model.ProviderDefinition;
import org.zyh.microservices.poc.model.ReferenceDefinition;
import org.zyh.microservices.poc.registry.ServiceRegistry;

/**
 * @author z672909
 */
public class ServiceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private ServiceRegistry serviceRegistry = new ServiceRegistry();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        serviceRegistry.scan("org.zyh.microservices.demo");
        for (ProviderDefinition providerDefinition : serviceRegistry.getProviderDefinitions()) {
            GenericBeanDefinition definition = new GenericBeanDefinition();
            definition.setBeanClass(providerDefinition.getOriginalClass());
            definition.setAttribute("provider", true);
            definition.setAttribute("providerDefinition", providerDefinition);
            ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(providerDefinition.name(), definition);
        }
        for (ReferenceDefinition referenceDefinition : serviceRegistry.getReferenceDefinitions()) {
            GenericBeanDefinition definition = new GenericBeanDefinition();
            definition.setBeanClass(ReferenceFactoryBean.class);
            definition.setAttribute("reference", true);
            definition.setAttribute("referenceDefinition", referenceDefinition);
            ((DefaultListableBeanFactory) beanFactory).registerBeanDefinition(referenceDefinition.name(), definition);
        }
    }
}
