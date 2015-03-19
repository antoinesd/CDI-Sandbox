package org.cdisandbox.injectparam;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 */
@Interceptor
@Priority(Interceptor.Priority.LIBRARY_BEFORE)
@InjectParams
public class ParamInjectInterceptor {

    @Inject
    @Any
    private Instance<Object> beans;

    @Inject
    private BeanManager bm;

    private static final Logger log = Logger.getLogger(ParamInjectInterceptor.class.getName());

    @AroundInvoke
    Object injector(InvocationContext context) throws Exception {

        Method method = context.getMethod();
        Class<?> paramTypes[] = method.getParameterTypes();
        Annotation paramAnnotations[][] = method.getParameterAnnotations();
        Object[] params = new Object[context.getParameters().length];

        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> c = paramTypes[i];
            Annotation[] annotations = paramAnnotations[i];
            Set<Annotation> qualifiers = new HashSet<>();
            for (Annotation a : annotations) {
                if (bm.isQualifier(a.annotationType()))
                    qualifiers.add(a);
            }
            if (qualifiers.isEmpty()) {
                qualifiers.add(new AnnotationLiteral<Default>() {
                });
            }
            Instance<?> selBean = beans.select(c, qualifiers.toArray(new Annotation[qualifiers.size()]));
            if (selBean.isAmbiguous()) {
                log.log(Level.INFO, "Ambiguous injection for parameter #{0} in method {1} of {2}, keeping invocation " 
                        + "parameter", new Object[] { i, method
                        .getName(), method.getDeclaringClass().getName() });
                params[i] = context.getParameters()[i];

            } else if (selBean.isUnsatisfied()) {
                log.log(Level.INFO, "Unsatisfied injection for parameter #{0} in method {1} of {2}, keeping invocation " 
                                + "parameter",
                        new Object[] { i, method
                                .getName(), method.getDeclaringClass().getName() });
                params[i] = context.getParameters()[i];
            } else {
                log.log(Level.INFO, "Injecting found bean instance in parameter #{0} in method {1} of {2}", new Object[] { i,
                        method
                        .getName(), method.getDeclaringClass().getName() });
                params[i] = selBean.get();
            }
        }
        context.setParameters(params);
        return context.proceed();
    }

}
