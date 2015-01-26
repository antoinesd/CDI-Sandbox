package org.cdisandbox.converter;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * @author Antoine Sabot-Durand
 */
public class ConverterProducer {
    

    @Produces
    @Convert
    public Object produceConverter(InjectionPoint ip) {
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
