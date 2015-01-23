package org.cdisandbox.event;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.Bean;
import javax.inject.Inject;

/**
 * @author Antoine Sabot-Durand
 */

@ApplicationScoped
public class ApplicationBean {
    
    @Inject
    Bean<ApplicationBean> meta;
    
    @Inject
   private SingletonBean bean;


    public void doSomething() {
        bean.doSomething();
    }
}
