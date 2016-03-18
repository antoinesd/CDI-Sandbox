package org.cdisandbox.config.extension;

import org.cdisandbox.config.Configuration;
import org.cdisandbox.config.ConfiguredService;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionPoint;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Antoine Sabot-Durand
 */
public class ConfigurationExtension implements Extension {


    private Set<Configuration> configurations = new HashSet<>();

    public void retrieveTypes(@Observes ProcessInjectionPoint<?, ConfiguredService> pip, BeanManager bm) {
        InjectionPoint ip = pip.getInjectionPoint();

        if (ip.getAnnotated().isAnnotationPresent(Configuration.class))
            configurations.add(ip.getAnnotated().getAnnotation(Configuration.class));
        else
            pip.addDefinitionError(new IllegalStateException("Service should be configured"));
    }


    public void createBeans(@Observes AfterBeanDiscovery abd, BeanManager bm) {

        ConfiguredServiceBean.types = bm.createAnnotatedType(ConfiguredService.class).getTypeClosure();

        for (Configuration configuration : configurations) {
            abd.addBean(new ConfiguredServiceBean(configuration));
        }


    }


}
