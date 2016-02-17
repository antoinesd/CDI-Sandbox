package org.cdisandbox.autoinject;

import javax.enterprise.inject.Produces;
import java.util.HashMap;
import java.util.Map;

/**
 * This bean produce a bean of type Map<String,String>
 */
public class CacheContextProducer {


    @Produces
    public Map<String, String> produceCacheContext() {

        return new HashMap<>();
    }

}
