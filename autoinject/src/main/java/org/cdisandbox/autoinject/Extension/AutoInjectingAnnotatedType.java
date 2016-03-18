package org.cdisandbox.autoinject.Extension;

import org.cdisandbox.autoinject.CacheContext;

import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * This AnnotatedType implementation decorates an exisiting AnnotatedType
 * to provide decoration with AutoInjectingAnnotatedField for Fields having @CacheContext annotation
 */
public class AutoInjectingAnnotatedType<X> implements AnnotatedType<X> {


    private final AnnotatedType<X> delegate;
    private final Set<AnnotatedField<? super X>> fields;

    public AutoInjectingAnnotatedType(AnnotatedType<X> delegate) {
        this.delegate = delegate;
        fields = new HashSet<>();
        for (AnnotatedField<? super X> field : delegate.getFields()) {
            if (field.isAnnotationPresent(CacheContext.class))
                fields.add(new AutoInjectingAnnotatedField(field));
            else
                fields.add(field);
        }
    }

    @Override
    public Class<X> getJavaClass() {
        return delegate.getJavaClass();
    }

    @Override
    public Set<AnnotatedConstructor<X>> getConstructors() {
        return delegate.getConstructors();
    }

    @Override
    public Set<AnnotatedMethod<? super X>> getMethods() {
        return delegate.getMethods();
    }

    @Override
    public Set<AnnotatedField<? super X>> getFields() {
        return fields;
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
        return delegate.getAnnotation(annotationType);
    }

    @Override
    public Set<Annotation> getAnnotations() {
        return delegate.getAnnotations();
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        return delegate.isAnnotationPresent(annotationType);
    }
}
