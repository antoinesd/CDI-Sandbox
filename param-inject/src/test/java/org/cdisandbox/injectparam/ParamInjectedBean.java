package org.cdisandbox.injectparam;

import javax.inject.Named;

/**
 *
 */
public class ParamInjectedBean {

    @InjectParams
    public String injectMe1(String first, @Qualified String second) {
        return first + second;
    }

    @InjectParams
    public String injectMe2(String first, Integer second) {
        return first + second;
    }

    @InjectParams
    public String injectMe3(@Named String first, @Qualified String second) {
        return first + second;
    }
}
