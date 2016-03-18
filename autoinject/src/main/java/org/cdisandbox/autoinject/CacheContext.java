package org.cdisandbox.autoinject;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This Annotation don't have to be a qualifier
 */
@Target({TYPE, METHOD, PARAMETER, FIELD})
@Retention(RUNTIME)
@Documented
public @interface CacheContext {
    boolean ignoreFinal() default true;

    boolean selfInvocation() default false;

}
