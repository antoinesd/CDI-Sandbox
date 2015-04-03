package org.cdisandbox.fatentities;

import javax.inject.Inject;
import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * @author Antoine Sabot-Durand
 */

@MappedSuperclass
@EntityListeners({ ManagingListener.class })
public class EntityRoot {

    private Long id;

    @Inject
    private EntityManager entityManager;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
