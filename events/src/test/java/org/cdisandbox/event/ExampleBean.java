package org.cdisandbox.event;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.EventMetadata;

/**
 * @author Antoine Sabot-Durand
 */

@ApplicationScoped
public class ExampleBean {
    
   private void strictListen(@Observes @Qualified Payload evt, EventMetadata meta)
   {
       if(meta.getQualifiers().contains(new QualifiedLiteral()) && meta.getType().equals(Payload.class))
           System.out.println("Do something");
       else
           System.out.println("ignore");
   }
    
}
