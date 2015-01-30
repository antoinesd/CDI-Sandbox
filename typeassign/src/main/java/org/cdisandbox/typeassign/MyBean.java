package org.cdisandbox.typeassign;

import javax.inject.Inject;

/**
 *
 */
public class MyBean<T extends MasterDbo<D>,D extends DetailDbo> {
    
    @Inject
    public MyService<T,D> ws;
}
