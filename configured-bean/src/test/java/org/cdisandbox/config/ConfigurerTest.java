package org.cdisandbox.config;

import org.cdisandbox.config.extension.ConfigurationExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;
import java.io.FileNotFoundException;

/**
 * Created by antoine on 18/03/2016.
 */
@RunWith(Arquillian.class)
public class ConfigurerTest {

    @Inject
    @Configuration("conf1")
    ConfiguredService cs1;
    @Inject
    @Configuration("conf1")
    ConfiguredService cs1bis;
    @Inject
    @Configuration("conf2")
    ConfiguredService cs2;
    @Inject
    @Any
    Instance<ConfiguredService> configuredServices;

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {

        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addPackages(true, "org.cdisandbox.config")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsServiceProvider(Extension.class, ConfigurationExtension.class);

        return ret;
    }

    @Test
    public void shouldHave2Beans() {
        int i = 0;
        for (ConfiguredService configuredService : configuredServices) {
            i++;
        }
        Assert.assertEquals(2, i);
    }

    @Test
    public void shouldValueBeFilled() {
        Assert.assertEquals("conf2", cs2.getValue());
    }

}
