package org.cdisandbox.event;


import javax.enterprise.util.AnnotationLiteral;

/**
 * @author Antoine Sabot-Durand
 */
public class QualifiedLiteral extends AnnotationLiteral<Qualified> implements Qualified {
    
    private String value="";

    public QualifiedLiteral(String value) {
        this.value = value;
    }

    public QualifiedLiteral() {
        this("");
    }
    
    @Override
    public String value() {
        return value;
    }
}
