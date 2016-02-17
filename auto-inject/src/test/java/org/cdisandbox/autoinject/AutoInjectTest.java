package org.cdisandbox.autoinject;

import org.cdisandbox.autoinject.Extension.AutoInjectExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;
import java.io.FileNotFoundException;

/**
 * Arquillian test
 */

@RunWith(Arquillian.class)
public class AutoInjectTest {

    @Inject
    CacheContextConsumer consumer;

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {

        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addPackage("org.cdisandbox.autoinject")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsServiceProvider(Extension.class, AutoInjectExtension.class);

        return ret;
    }

    /**
     * testing if Map in consumer has been correctly injected
     */
    @Test
    public void shouldHaveNonNullCache() {
        Assert.assertNotNull(consumer.getCache());
    }


}


