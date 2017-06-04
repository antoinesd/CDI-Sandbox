package org.cdisandbox.converter;

import java.io.FileNotFoundException;

import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Antoine Sabot-Durand
 */

@RunWith(Arquillian.class)
public class ConvertTest {

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {

        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addPackage("org.cdisandbox.converter")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsServiceProvider(Extension.class, ConverterExtension.class);

        return ret;
    }

    @Test
    public void shoulHaveInjectedConvertedInteger() {
        Assert.assertEquals(new Integer(126), converted);


    }

    @Test
    public void shoulHaveInjectedConvertedStringBuffer() {
        Assert.assertEquals("Me/Hungry", converted2.toString());

    }

    @Inject
    @Convert("126")
    Integer converted;

    @Inject
    @Convert("Me/Hungry")
    StringBuffer converted2;
}
