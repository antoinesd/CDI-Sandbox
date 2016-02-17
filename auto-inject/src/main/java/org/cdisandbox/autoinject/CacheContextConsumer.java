package org.cdisandbox.autoinject;

import java.util.Map;

/**
 * This bean include a Map<String,String> annotated with @CacheContext
 * The extension should transform this into an injection point
 */
public class CacheContextConsumer {

    @CacheContext
    private Map<String, String> cache;

    public Map<String, String> getCache() {
        return cache;
    }
}
