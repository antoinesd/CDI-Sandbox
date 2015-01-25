package org.cdisandbox.converter;

import javax.enterprise.util.AnnotationLiteral;

/**
 *
 */
public class ConvertLiteral extends AnnotationLiteral<Convert> implements Convert {
    @Override
    public String value() {
        return "";
    }
}
