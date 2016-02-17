package org.cdisandbox.autoinject.Extension;

import org.cdisandbox.autoinject.CacheContext;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;

/**
 * Extension looking for the @CacheContext annotation to transform it in an InjectionPoint
 */
public class AutoInjectExtension implements Extension {

    /**
     * This Observer decorates all AnnotatedType containing @CacheContext in an AutoInjectingAnnotatedType
     */
    public <X> void CreateInjectionPoint(@Observes @WithAnnotations(CacheContext.class) ProcessAnnotatedType<X> pat) {
        pat.setAnnotatedType(new AutoInjectingAnnotatedType<>(pat.getAnnotatedType()));
    }


}
