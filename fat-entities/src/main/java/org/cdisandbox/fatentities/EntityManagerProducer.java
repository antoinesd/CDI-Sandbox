package org.cdisandbox.fatentities;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 */
public class EntityManagerProducer {

    @PersistenceContext(unitName = "myPU")
    @Produces
    private EntityManager em;

}
