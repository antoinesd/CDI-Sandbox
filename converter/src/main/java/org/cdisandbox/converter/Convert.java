package org.cdisandbox.converter;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Antoine Sabot-Durand
 */
@Qualifier
@Target({TYPE, METHOD, PARAMETER, FIELD})
@Retention(RUNTIME)
@Documented
public @interface Convert {
    
    @Nonbinding
    String value() default "";

    public static class Literal extends AnnotationLiteral<Convert> implements Convert {

        public static final Convert INSTANCE = new Literal();

        @Override
        public String value() {
            return "";
        }
    }
    
}
