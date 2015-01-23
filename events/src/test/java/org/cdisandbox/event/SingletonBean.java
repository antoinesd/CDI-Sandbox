package org.cdisandbox.event;

import javax.inject.Singleton;

/**
 * @author Antoine Sabot-Durand
 */

@Singleton
public class SingletonBean {
    
    private String field;
    
    public void doSomething() {
        System.out.println("I'm doing something");
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
