package com.santander.cloudbr.games.challenges;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

@ApplicationScoped
public class BookApplicationLifeCycle {

    private static final Logger LOGGER = Logger.getLogger(BookApplicationLifeCycle.class);

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("  ____              _         _    ____ ___ ");
        LOGGER.info(" | __ )  ___   ___ | | __    / \\  |  _ \\_ _|");
        LOGGER.info(" |  _ \\ / _ \\ / _ \\| |/ /   / _ \\ | |_) | | ");
        LOGGER.info(" | |_) | (_) | (_) |   <   / ___ \\|  __/| | ");
        LOGGER.info(" |____/ \\___/ \\___/|_|\\_\\ /_/   \\_\\_|  |___|");
        LOGGER.info("                         Powered by Quarkus");
        LOGGER.info("The application Book is starting with profile " + ProfileManager.getActiveProfile());
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application Book is stopping...");
    }
}