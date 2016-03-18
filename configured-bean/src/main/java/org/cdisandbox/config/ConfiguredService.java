package org.cdisandbox.config;

import javax.enterprise.inject.Vetoed;

/**
 * Created by antoine on 18/03/2016.
 */

@Vetoed
public class ConfiguredService {

    private String value;

    protected ConfiguredService() {
    }

    public ConfiguredService(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
