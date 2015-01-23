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

import java.io.FileNotFoundException;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * @author Antoine Sabot-Durand
 */

@RunWith(Arquillian.class)
public class SimpleEventTest {

    @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {

        WebArchive ret = ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addClasses(SimpleObservingBean.class,
                        Payload.class,
                        Qualified.class,
                        QualifiedLiteral.class,
                        QualifiedAgain.class,
                        QualifiedAgainLiteral.class
                )
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        return ret;
    }


    @Inject
    Event<Payload> payloadEvent;

    @Inject
    @Qualified
    Event<Payload> qualifiedPayloadEvent;


    @Test
    public void shouldResolveThreeTypeObserver() {
        SubPayload myPl = new SubPayload();
        payloadEvent.select(SubPayload.class).fire(myPl); //select useless with Weld 2

        Assert.assertEquals(13, myPl.content);
    }

    @Test
    public void shouldResolveUnqualifiedObserver() {
        Payload myPl = new Payload();
        payloadEvent.fire(myPl);
        System.out.println("Job done");

        Assert.assertEquals(11, myPl.content);
    }


    @Test
    public void shouldResolvedQualifiedAndUnqualifiedObserver() {
        Payload myPl = new Payload();
        payloadEvent.select(new QualifiedLiteral()).fire(myPl);

        Assert.assertEquals(111, myPl.content);
    }

    @Test
    public void shouldResolvedQualifiedAndUnqualifiedObserver2() {
        Payload myPl = new Payload();
        qualifiedPayloadEvent.fire(myPl);

        Assert.assertEquals(111, myPl.content);
    }

    @Test
    public void shouldResolvedQualifiedWithParamAndUnqualifiedObserver() {
        Payload myPl = new Payload();
        payloadEvent.select(new QualifiedLiteral("special")).fire(myPl);

        Assert.assertEquals(121, myPl.content);
    }
    

    @Test
    public void shouldResolvedQualifiedAgainAndUnqualifiedObserver() {
        Payload myPl = new Payload();
        payloadEvent.select(new QualifiedAgainLiteral()).fire(myPl);

        Assert.assertEquals(1011, myPl.content);
    }


    @Test
    public void shouldResolvedQualifiedQualifiedAgainAndUnqualifiedObserver() {
        Payload myPl = new Payload();
        payloadEvent.select(new QualifiedAgainLiteral(), new QualifiedLiteral()).fire(myPl);

        Assert.assertEquals(11111, myPl.content);
    }
}
