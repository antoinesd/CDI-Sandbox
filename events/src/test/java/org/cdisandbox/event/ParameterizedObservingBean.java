package org.cdisandbox.event;

import javax.enterprise.event.Observes;
import java.util.List;
import java.util.Map;

/**
 * @author Antoine Sabot-Durand
 */
public class ParameterizedObservingBean {
    
    public void processNumberList(@Observes List<Number> event) {
        event.add(new Integer(1));
    }
    
    public void processIntegerList(@Observes List<Integer> event) {
            event.add(2);
        }

    public void observesMap(@Observes Map<?, ?> event) {
        System.out.println("I'm here !");
    }
    
}
