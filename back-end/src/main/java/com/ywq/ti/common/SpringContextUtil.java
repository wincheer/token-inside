package com.ywq.ti.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware{
	private static ApplicationContext applicationContext;  
	  
    @Override  
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
        SpringContextUtil.applicationContext = applicationContext;  
    }  
  
    public static ApplicationContext getCtx() {  
    	return SpringContextUtil.applicationContext;  
    }  
  
    public static <T> T getBean(Class<T> t) {  
        return SpringContextUtil.applicationContext.getBean(t);  
    }  
}
