package org.cdisandbox.typeassign;

import javax.inject.Inject;

/**
 *
 */
public class MyBean {
    
    @Inject
    public MyService<ClassA,ClassB> ms;
}
