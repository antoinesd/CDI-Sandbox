package org.cdisandbox.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessProducerMethod;

/**
 * @author Antoine Sabot-Durand
 */
public class ConverterExtension implements Extension {

    private static class ConverterBean implements Bean<Object> {
        
       
        private final Bean<Object> delegate;
        private final Set<Type> types;
       
        public ConverterBean(Bean convBean, Set<Type> types) {
            this.types = types;
            this.delegate = convBean;
        }

        @Override
        public Set<Type> getTypes() {
            return types;
        }

        @Override
        public Class<?> getBeanClass() {
            return delegate.getBeanClass();
        }

        @Override
        public Set<InjectionPoint> getInjectionPoints() {
            return delegate.getInjectionPoints();
        }

        @Override
        public String getName() {
            return delegate.getName();
        }

        @Override
        public Set<Annotation> getQualifiers() {
            return delegate.getQualifiers();
        }

        @Override
        public Class<? extends Annotation> getScope() {
            return delegate.getScope();
        }

        @Override
        public Set<Class<? extends Annotation>> getStereotypes() {
            return delegate.getStereotypes();
        }

        @Override
        public boolean isAlternative() {
            return delegate.isAlternative();
        }

        @Override
        public boolean isNullable() {
            return delegate.isNullable();
        }

        @Override
        public Object create(CreationalContext<Object> creationalContext) {
            return delegate.create(creationalContext);
        }

        @Override
        public void destroy(Object instance, CreationalContext<Object> creationalContext) {
            delegate.destroy(instance, creationalContext);
        }
    }

    private Set<Type> types = new HashSet<>();
    private Bean<?> convBean;

    public void retrieveTypes(@Observes ProcessBean<?> pb) {
        Set<InjectionPoint> ips = pb.getBean().getInjectionPoints();
        for (InjectionPoint ip : ips) {
            if (ip.getAnnotated().isAnnotationPresent(Convert.class)) {
                types.add(ip.getType());
            }
        }
    }
    
    public void captureConvertBean(@Observes ProcessProducerMethod<?,?> ppm) {
        if(ppm.getAnnotated().isAnnotationPresent(Convert.class))
            convBean=ppm.getBean();
            
    }

    public void addConverter(@Observes AfterBeanDiscovery abd, BeanManager bm) {
        abd.addBean(new ConverterBean(convBean, types));
    }


}
