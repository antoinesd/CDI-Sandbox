package org.cdisandbox.fatentities;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 *
 */
@Transactional
public class PersonService {

    @Inject
    EntityManager em;

    public Integer getNumberPerson() {
        return 5;
    }

    public Person findPerson(Long id) {
        return em.find(Person.class, id);
    }


    public void persist(Object obj) {
        em.persist(obj);
    }
}
