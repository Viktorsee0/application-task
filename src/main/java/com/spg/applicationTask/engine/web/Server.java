package com.spg.applicationTask.engine.web;

import com.spg.applicationTask.engine.IoC.annotation.Inject;
import com.spg.applicationTask.engine.web.ServerConfig.Property;
import com.sun.net.httpserver.HttpServer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spg.applicationTask.engine.web.Server.Settings.BACKLOG_VALUE;
import static com.spg.applicationTask.engine.web.Server.Settings.HOSTNAME_VALUE;
import static com.spg.applicationTask.engine.web.Server.Settings.PORT_VALUE;
import static com.spg.applicationTask.engine.web.WebConstants.Messages.SERVER_STARTED;
import static com.spg.applicationTask.engine.web.WebConstants.Messages.SERVER_STOPPED;

public final class Server implements WebServer {

    private final static Logger LOGGER = Logger.getLogger(Server.class.getSimpleName());
    private HttpServer server;
    @Inject
    private ServerConfig config;

    @PostConstruct
    public void postConstruct() {
        String host = config.getProperty(Property.HOST)
                .orElse(HOSTNAME_VALUE);
        int port = Integer.parseInt(config.getProperty(Property.PORT)
                .orElse(String.valueOf(PORT_VALUE)));
        try {
            this.server = HttpServer.create(new InetSocketAddress(host, port), BACKLOG_VALUE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public WebServer start() {
        load();
        server.start();
        LOGGER.log(Level.INFO, SERVER_STARTED);
        LOGGER.log(Level.INFO, "PORT: " + config.getProperty(Property.PORT));
        return this;
    }

    @Override
    public void stop() {
        server.stop(0);
        LOGGER.log(Level.INFO, SERVER_STOPPED);
    }

    @Override
    public void addControllers(AbstractController... controllers) {
        Arrays.stream(controllers).forEach(controller ->
                server.createContext(controller.getApiPath(), controller::handle));
    }

    private void load() {
        this.server.setExecutor(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    public final static class Settings {

        private Settings() {

        }

        static final String HOSTNAME_VALUE = "localhost";
        static final int PORT_VALUE = 8000;
        static final int BACKLOG_VALUE = 0;
    }

}
