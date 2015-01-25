package org.cdisandbox.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessBeanAttributes;
import javax.enterprise.inject.spi.ProcessInjectionPoint;

/**
 * @author Antoine Sabot-Durand
 */
public class ConverterExtension implements Extension {

    private static class ConverterBeanAttribute implements BeanAttributes<Object> {

        private BeanAttributes<Object> delegate;
        private Set<Type> types;

        public ConverterBeanAttribute(BeanAttributes<Object> delegate, Set<Type> types) {
            this.delegate = delegate;
            this.types = types;
        }

        @Override
        public Set<Type> getTypes() {
            return types;
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
        public String getName() {
            return delegate.getName();
        }

        @Override
        public Set<Class<? extends Annotation>> getStereotypes() {
            return delegate.getStereotypes();
        }

        @Override
        public boolean isAlternative() {
            return delegate.isAlternative();
        }
    }

    private Set<Type> types = new HashSet<>();

    public void retrieveTypes(@Observes ProcessInjectionPoint<?, ?> pip) {
        InjectionPoint ip = pip.getInjectionPoint();
        if (ip.getAnnotated().isAnnotationPresent(Convert.class))
            types.add(ip.getType());
    }
    
    public void addTypeToConverter(@Observes ProcessBeanAttributes<Object> pba) {
        if (pba.getAnnotated().isAnnotationPresent(Convert.class)) {
            pba.setBeanAttributes(new ConverterBeanAttribute(pba.getBeanAttributes(), types));
        }
    }
    
    
    


}
