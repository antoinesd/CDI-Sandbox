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
 * This extensions retrieves all injection points with <pre>@Convert</pre> qualifiers
 * and store their types.
 *
 * During <pre>ProcessBeanAttributes</pre> it decorates the bean of type object and <pre>@Convert</pre> qualifier
 * (i.e the {@link ConverterProducer#produceConverter(InjectionPoint)} producer)
 * to add all discovered types in its types set.
 *
 * Instance of this bean will be created and injected for all injection points since it matches all <pre>@Convert</pre> injection points.
 *
 *
 * @author Antoine Sabot-Durand
 */
public class ConverterExtension implements Extension {

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

    private Set<Type> types = new HashSet<>();

    /**
     * Implementation of {@link BeanAttributes} SPI to decorate an existing <pre>BeanAttributes</pre>
     * by replacing its type set
     */
    private static class ConverterBeanAttribute implements BeanAttributes<Object> {

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

        private BeanAttributes<Object> delegate;

        private Set<Type> types;
    }


}
