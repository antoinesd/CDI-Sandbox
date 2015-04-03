package org.cdisandbox.fatentities;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @author Antoine Sabot-Durand
 */

@RunWith(Arquillian.class)
public class FatEntitiesTest {

    @Inject
    EntityManager em;
    @Inject
    BeanManager bm;

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {

        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addPackage("org.cdisandbox.fatentities")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("META-INF/persistence.xml", "classes/META-INF/persistence.xml");

        return ret;
    }

    @Test
    public void shouldPersonBePersisted() {
        Person p = new Person("Alphonse", "Allais");
        Manager<Person> mp = new Manager<Person>(bm, Person.class);
        mp.manage(p);

        p.persist();
        p.getEntityManager().flush();

        Person p2 = em.find(Person.class, 1);

        Assert.assertEquals("Allais", p2.getLastname());

    }

}
