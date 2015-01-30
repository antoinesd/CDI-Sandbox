package org.cdisandbox.typeassign;

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
 *
 */
@RunWith(Arquillian.class)
public class TypeVariableTest {
    
    @Deployment
        public static Archive<?> createTestArchive() throws FileNotFoundException {
    
            WebArchive ret = ShrinkWrap
                    .create(WebArchive.class, "test.war")
                    .addPackage("org.cdisandbox.typeassign")
                    .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
                    
    
            return ret;
        }
    
    @Inject
    private SpecialBean sb;
    
    
    @Test
    public void specialBeanShouldNotBeNull() {
        Assert.assertNotNull(sb);
    }
}
