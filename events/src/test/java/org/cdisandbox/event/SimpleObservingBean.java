package org.cdisandbox.event;


import javax.enterprise.event.Observes;

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

    public void observesQualifiedWithParamPayloads(@Observes @Qualified("special") Payload pl) {
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
}
