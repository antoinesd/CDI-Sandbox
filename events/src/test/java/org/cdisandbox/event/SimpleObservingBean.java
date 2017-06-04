package org.cdisandbox.event;


import java.lang.annotation.Annotation;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.EventMetadata;
import javax.enterprise.inject.spi.Unmanaged;

/**
 * @author Antoine Sabot-Durand
 */
public class SimpleObservingBean {


    public void observesAllPayloads(@Observes Payload pl) {
        pl.content += 10;
    }
    
    public void observesAllSubPayloads(@Observes SubPayload pl) {
            pl.content += 2;
        }

    public void observesSimplyQualifiedPayloads(@Observes @Qualified Payload pl) {
        pl.content += 100;
    }

    public void observesQualifiedWithParamPayloads(@Observes @Qualified("special") Payload pl, EventMetadata meta) {
        Qualified q;
        for (Annotation a : meta.getQualifiers()) {
            if (a.annotationType().equals(Qualified.class)) {
                q = (Qualified) a;
                System.out.println("***** Memeber is: " + q.value());
            }
        }

        pl.content += 110;
    }

    public void observesSimplyQualifiedAgainPayloads(@Observes @QualifiedAgain Payload pl) {
        pl.content += 1000;
    }

    public void observesQualifiedAndQualifiedAgainPayloads(@Observes @QualifiedAgain @Qualified Payload pl) {
        pl.content += 10000;
    }

    public void observesObject(@Observes Object pl) {
        if(pl instanceof Payload)
            ((Payload)pl).content += 1;
        }


    public void doSomethingWithContextualMyClass() {
        Unmanaged<MyClass> unmanagedMyClass = new Unmanaged<MyClass>(MyClass.class);
        Unmanaged.UnmanagedInstance<MyClass> umc = unmanagedMyClass.newInstance();
        umc.produce().inject().postConstruct();
        MyClass myInstance = umc.get();

        //Do what you need with MyInstance

        umc.preDestroy();
        umc.dispose();
    }
}
