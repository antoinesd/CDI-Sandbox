package org.cdisandbox.converter;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

/**
 * @author Antoine Sabot-Durand
 */
@Dependent
public class Converter {

    @Inject
    private InjectionPoint ip;
    

    public Object convert() {
        String toConvert = ip.getAnnotated().getAnnotation(Convert.class).value();
        Class<?> toType = (Class<?>) ip.getAnnotated().getBaseType();

        System.out.println("I have to convert " + toConvert + " to type: " + toType.toString());
        if (toType.equals(Integer.class))
            return new Integer(toConvert);
        else if (toType.equals(StringBuffer.class))
            return new StringBuffer(toConvert);
        else
            return null;

    }
}
