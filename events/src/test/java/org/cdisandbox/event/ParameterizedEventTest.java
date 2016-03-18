package org.cdisandbox.event;

import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Antoine Sabot-Durand
 */

@RunWith(Arquillian.class)
public class ParameterizedEventTest {

    @Inject
    Event<List<Number>> listNumberEvent;
    @Inject
    Event<List<Integer>> listIntegerEvent;
    @Inject
    Event<Map<?, ?>> paramEvtMap;

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {

        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addClasses(ParameterizedObservingBean.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        return ret;
    }

    @Test
    public void shouldCallNumberObserver() {
        List<Number> pl = new ArrayList<>();
        listNumberEvent.fire(pl);
        Assert.assertEquals(1, pl.size());
        Assert.assertEquals(1, pl.get(0).intValue());

    }

    @Test
    public void shouldCallIntegerObserver() {
        List<Integer> pl = new ArrayList<>();
        listIntegerEvent.fire(pl);
        Assert.assertEquals(1, pl.size());
        Assert.assertEquals(2, pl.get(0).intValue());

    }

    @Test
    public void shouldWork() {
        Map<?, ?> pl = new HashMap<>();
        paramEvtMap.fire(pl);
        Assert.assertTrue(true);

    }
}
