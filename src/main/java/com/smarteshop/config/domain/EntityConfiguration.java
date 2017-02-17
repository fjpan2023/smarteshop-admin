package com.smarteshop.config.domain;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class EntityConfiguration implements ApplicationContextAware {
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityConfiguration.class);

    private ApplicationContext webApplicationContext;
    private final HashMap<String, Class<?>> entityMap = new HashMap<String, Class<?>>(50);
    private ApplicationContext applicationcontext;
    private Resource[] entityContexts;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.webApplicationContext = applicationContext;
    }


    public Class<?> lookupEntityClass(String beanId) {
        Class<?> clazz;
        if (entityMap.containsKey(beanId)) {
            clazz = entityMap.get(beanId);
        } else {
            Object object = applicationcontext.getBean(beanId);
            clazz = object.getClass();
            entityMap.put(beanId, clazz);
        }
        if (LOGGER.isDebugEnabled()) {
        	LOGGER.debug("Returning class (" + clazz.getName() + ") configured with bean id (" + beanId + ')');
        }
        return clazz;
    }
    
    public String[] getEntityBeanNames() {
        return applicationcontext.getBeanDefinitionNames();
    }

    public <T> Class<T> lookupEntityClass(String beanId, Class<T> resultClass) {
        Class<T> clazz;
        if (entityMap.containsKey(beanId)) {
            clazz = (Class<T>) entityMap.get(beanId);
        } else {
            Object object = applicationcontext.getBean(beanId);
            clazz = (Class<T>) object.getClass();
            entityMap.put(beanId, clazz);
        }
        if (LOGGER.isDebugEnabled()) {
        	LOGGER.debug("Returning class (" + clazz.getName() + ") configured with bean id (" + beanId + ')');
        }
        return clazz;
    }

    public Object createEntityInstance(Class clazz) {
        Object bean = applicationcontext.getBean(clazz);
        if (LOGGER.isDebugEnabled()) {
        	LOGGER.debug("Returning instance of class (" + bean.getClass().getName() + ") configured with bean (" + clazz + ')');
        }
        return bean;
    }

    public <T> T createEntityInstance(String beanId, Class<T> resultClass) {
        T bean = (T) applicationcontext.getBean(beanId);
        if (LOGGER.isDebugEnabled()) {
        	LOGGER.debug("Returning instance of class (" + bean.getClass().getName() + ") configured with bean id (" + beanId + ')');
        }
        return bean;
    }

    public Resource[] getEntityContexts() {
        return entityContexts;
    }

    public void setEntityContexts(Resource[] entityContexts) {
        this.entityContexts = entityContexts;
    }
	
	

}
