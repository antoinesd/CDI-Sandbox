package org.cdisandbox.typeassign;



/*
this doesn't work due to JLS subtyping rules :
public class MyService<T extends MasterDbo<D>,D extends DetailDbo>
*/


/**
 * This version work
 */
public class MyService<T extends MasterDbo<? extends DetailDbo>,D extends DetailDbo> {
}
