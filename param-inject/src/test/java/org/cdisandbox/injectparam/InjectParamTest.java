package org.cdisandbox.injectparam;

import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;
import javax.inject.Inject;

/**
 * @author Antoine Sabot-Durand
 */

@RunWith(Arquillian.class)
public class InjectParamTest {

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {

        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addPackage("org.cdisandbox.injectparam")
                .addClass(ParamInjectedBean.class)
                .addClass(Qualified.class)
                .addClass(QualifiedDifferently.class)
                .addClass(TestProducer.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        return ret;
    }

    @Inject
    ParamInjectedBean paramInjectedBean;

    @Test
    public void testParamInjection1() {
        Assert.assertEquals("Hi there!I am a qualified bean instance", paramInjectedBean.injectMe1("one", "two"));
    }

    @Test
    public void testParamInjectionUnsatisfiedByType() {
        Assert.assertEquals("Hi there!2", paramInjectedBean.injectMe2("one", 2));
    }

    @Test
    public void testParamInjectionByQualifier() {
        Assert.assertEquals("oneI am a qualified bean instance", paramInjectedBean.injectMe3("one", "two"));
    }

}
