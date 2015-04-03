package org.cdisandbox.fatentities;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.InjectionTarget;

/**
 * @author Antoine Sabot-Durand
 */
public class Manager<T> {

    private final InjectionTarget<T> injectionTarget;
    private final CreationalContext<T> ctx;

    public Manager(BeanManager beanManager, Class<T> clazz) {
        AnnotatedType<T> type = beanManager.createAnnotatedType(clazz);
        this.injectionTarget = beanManager.getInjectionTargetFactory(type).createInjectionTarget(null);
        this.ctx = beanManager.createCreationalContext(null);
    }

    public Manager(Class<T> clazz) {
        this(CDI.current().getBeanManager(), clazz);

    }


    public T manage(T instance) {
        injectionTarget.inject(instance, ctx);
        injectionTarget.postConstruct(instance);
        return instance;
    }

    public void dispose(T instance) {
        injectionTarget.preDestroy(instance);
        injectionTarget.dispose(instance);
    }


}
