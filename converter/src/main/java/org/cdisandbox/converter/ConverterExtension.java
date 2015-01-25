package org.cdisandbox.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.util.AnnotationLiteral;

/**
 * @author Antoine Sabot-Durand
 */
public class ConverterExtension implements Extension {

    private static class ConverterBean implements Bean<Object> {

        private final InjectionTarget target;
        private final Set<Annotation> qualifiers;
        private Set<Type> types;
        Converter converter;

        public ConverterBean(BeanManager bm, Set<Type> types) {
            AnnotatedType<Converter> atc = bm.createAnnotatedType(Converter.class);
            target = bm.createInjectionTarget(atc);
            qualifiers = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(new AnnotationLiteral<Any>() {
            }, new ConvertLiteral())));
            this.types = types;
        }

        @Override
        public Set<Type> getTypes() {
            return types;
        }

        @Override
        public Set<Annotation> getQualifiers() {
            return qualifiers;
        }

        @Override
        public Class<? extends Annotation> getScope() {
            return Dependent.class;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public Set<Class<? extends Annotation>> getStereotypes() {
            return Collections.EMPTY_SET;
        }

        @Override
        public Class<?> getBeanClass() {
            return Object.class;
        }

        @Override
        public boolean isAlternative() {
            return false;
        }

        @Override
        public boolean isNullable() {
            return false;
        }

        @Override
        public Set<InjectionPoint> getInjectionPoints() {
            return Collections.EMPTY_SET;
        }


        @Override
        public Object create(CreationalContext<Object> creationalContext) {
            converter = new Converter();
            target.inject(converter, creationalContext);
            return converter.convert();
        }

        @Override
        public void destroy(Object instance, CreationalContext<Object> creationalContext) {
            target.dispose(converter);
        }
    }

    private Set<Type> types = new HashSet<>();

    public void retrieveTypes(@Observes ProcessBean<?> pb) {
        Set<InjectionPoint> ips = pb.getBean().getInjectionPoints();
        for (InjectionPoint ip : ips) {
            if (ip.getAnnotated().isAnnotationPresent(Convert.class)) {
                types.add(ip.getType());
            }
        }
    }

    public void addConverter(@Observes AfterBeanDiscovery abd, BeanManager bm) {
        abd.addBean(new ConverterBean(bm, types));
    }


}
