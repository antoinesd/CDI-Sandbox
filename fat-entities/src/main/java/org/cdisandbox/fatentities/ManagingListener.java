package org.cdisandbox.fatentities;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.PostLoad;

/**
 * @author Antoine Sabot-Durand
 */
public class ManagingListener {

    @Inject
    BeanManager bm;

    @PostLoad
    public void postLoad(Object entity) {
        Class<?> clazz = entity.getClass();
        Manager manager = new Manager(clazz);
        manager.manage(entity);
    }
}
