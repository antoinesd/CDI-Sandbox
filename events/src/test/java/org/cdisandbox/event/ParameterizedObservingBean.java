package org.cdisandbox.event;

import java.util.List;
import javax.enterprise.event.Observes;

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
    
}
