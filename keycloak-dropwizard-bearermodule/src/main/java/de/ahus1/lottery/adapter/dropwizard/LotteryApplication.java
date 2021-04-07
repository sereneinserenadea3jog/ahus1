package de.ahus1.lottery.adapter.dropwizard;

import de.ahus1.keycloak.dropwizard.KeycloakBundle;
import de.ahus1.keycloak.dropwizard.KeycloakConfiguration;
import de.ahus1.lottery.adapter.dropwizard.resource.DrawRessource;
import de.ahus1.lottery.adapter.dropwizard.util.DropwizardBearerTokenFilterImpl;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.jaxrs.JaxrsBearerTokenFilterImpl;

import java.io.IOException;

public class LotteryApplication extends Application<LotteryConfiguration> {
    public static void main(String[] args) throws Exception {
        new LotteryApplication().run(args);
    }

    @Override
    public String getName() {
        return "lottery";
    }

    @Override
    public void initialize(Bootstrap<LotteryConfiguration> bootstrap) {

        // set up folders for static content
        bootstrap.addBundle(new AssetsBundle("/assets/ajax", "/ajax", null, "ajax"));

        // support for new Java Date/Time API
        bootstrap.addBundle(new Java8Bundle());

        // tag::keycloak[]
        bootstrap.addBundle(new KeycloakBundle<LotteryConfiguration>() {
            @Override
            protected KeycloakConfiguration getKeycloakConfiguration(LotteryConfiguration configuration) {
                return configuration.getKeycloakConfiguration();
            }
        });
        // end::keycloak[]
    }

    @Override
    public void run(LotteryConfiguration configuration, Environment environment)
            throws ClassNotFoundException, IOException {

        environment.jersey().register(new DrawRessource());
    }
}
