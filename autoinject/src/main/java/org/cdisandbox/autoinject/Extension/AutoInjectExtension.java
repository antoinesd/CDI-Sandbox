package org.cdisandbox.autoinject.Extension;

import org.cdisandbox.autoinject.CacheContext;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessManagedBean;
import javax.enterprise.inject.spi.WithAnnotations;
import java.util.HashSet;
import java.util.Set;

/**
 * extension looking for the @CacheContext annotation to transform it in an InjectionPoint
 */
public class AutoInjectExtension implements Extension {

    private Set<AnnotatedType<?>> atWithCacheContext = new HashSet<>();

    /**
     * This Observer captures all AnnotatedType containing @CacheContext
     */
    public void CreateInjectionPoint(@Observes @WithAnnotations(CacheContext.class) ProcessAnnotatedType<?> pat) {
        atWithCacheContext.add(pat.getAnnotatedType());
    }

    /**
     * This observer add an injection point in all bean having @Cachecontext annotaion on some field
     */
    public <T> void CheckBeansForCacheContext(@Observes ProcessManagedBean<T> pmb, BeanManager bm) {
        AnnotatedType<T> beanAT = pmb.getAnnotatedBeanClass();
        if (atWithCacheContext.contains(beanAT)) {
            Bean<T> bean = pmb.getBean();
            Set<InjectionPoint> ips = new HashSet(bean.getInjectionPoints());
            for (AnnotatedField<? super T> f : beanAT.getFields()) {
                if (f.isAnnotationPresent(CacheContext.class)) {
                    ips.add(bm.createInjectionPoint(f));
                }
            }
        }
    }
}
