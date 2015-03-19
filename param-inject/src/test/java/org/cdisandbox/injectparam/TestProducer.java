package org.cdisandbox.injectparam;

import javax.enterprise.inject.Produces;

/**
 *
 */
public class TestProducer {

    @Qualified
    @Produces
    String produceQualifiedString() {
        return "I am a qualified bean instance";
    }


    @Produces
    String produceString() {
        return "Hi there!";
    }
}
