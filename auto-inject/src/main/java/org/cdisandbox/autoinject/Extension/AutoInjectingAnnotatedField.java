package org.cdisandbox.autoinject.Extension;

import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * This AnnotatedField implementation adds @Inject to field annotations if it is annoted with @CacheContext
 */
public class AutoInjectingAnnotatedField<X> implements AnnotatedField<X> {

    private final Set<Annotation> annotations;
    private final AnnotatedField<X> delegate;

    public AutoInjectingAnnotatedField(AnnotatedField<X> delegate) {
        this.delegate = delegate;
        annotations = new HashSet<>(delegate.getAnnotations());
        annotations.add(new InjectLiteral());
    }

    @Override
    public Field getJavaMember() {
        return delegate.getJavaMember();
    }

    @Override
    public boolean isStatic() {
        return delegate.isStatic();
    }

    @Override
    public AnnotatedType<X> getDeclaringType() {
        return delegate.getDeclaringType();
    }

    @Override
    public Type getBaseType() {
        return delegate.getBaseType();
    }

    @Override
    public Set<Type> getTypeClosure() {
        return delegate.getTypeClosure();
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        if (annotationType.equals(Inject.class))
            return (T) new InjectLiteral();
        return delegate.getAnnotation(annotationType);
    }

    @Override
    public Set<Annotation> getAnnotations() {
        return annotations;
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        if (annotationType.equals(Inject.class))
            return true;
        return delegate.isAnnotationPresent(annotationType);
    }

    public static class InjectLiteral extends AnnotationLiteral<Inject> implements Inject {
    }
}
