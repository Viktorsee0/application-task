package com.spg.applicationTask.engine;


import com.spg.applicationTask.annotation.Value;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spg.applicationTask.Constants.CREATE_CONSTANT_CLASS_ERROR;
import static com.spg.applicationTask.Constants.Messages.SERVER_STARTED;
import static com.spg.applicationTask.Constants.Messages.SERVER_STOPPED;
import static com.spg.applicationTask.engine.Server.Settings.*;

public final class Server implements WebServer {

    private final static Logger LOGGER = Logger.getLogger(Server.class.getSimpleName());
    private final HttpServer server;
    @Value("db.url")
    private String url;
    private final List<AbstractController> controllers = new ArrayList<>();

    /**
     * Constructs a web server.
     *
     * @throws Exception when a web server encounters a problem.
     */
    public Server(ServerConfig config) throws IOException {

        String host = config.getProperty(ServerConfig.Property.HOST)
                .orElse(HOSTNAME_VALUE);

        int port = Integer.parseInt(config.getProperty(ServerConfig.Property.PORT)
                .orElse(String.valueOf(PORT_VALUE)));

        controllers.addAll(config.getControllers());

        this.server = HttpServer.create(new InetSocketAddress(host, port), BACKLOG_VALUE);

        controllers
                .forEach(controller ->
                        server.createContext(controller.apiPath, controller::handle));
    }


    @Override
    public WebServer start() {
        server.start();
        LOGGER.log(Level.INFO, SERVER_STARTED);
        return this;
    }

    @Override
    public void stop() {
        server.stop(0);
        LOGGER.log(Level.INFO, SERVER_STOPPED);
    }

    public final static class Settings {

        private Settings() {
            throw new AssertionError(CREATE_CONSTANT_CLASS_ERROR);
        }

        // The configuration name
        public static final String CONFIG_NAME = "config-server";
        // The hostname key
        public static final String HOSTNAME = "hostname";
        // The hostname value
        static final String HOSTNAME_VALUE = "localhost";
        // The api path key
        static final String API_PATH = "api-path";
        // The api path value
        static final String API_PATH_VALUE = "/api/metacfg/";
        // The port key
        public static final String PORT = "port";
        // The port value
        static final int PORT_VALUE = 8000;
        // The backlog key
        public static final String BACKLOG = "backlog";
        // The backlog value
        static final int BACKLOG_VALUE = 0;
        // The key-store-file key
        public static final String KEY_STORE_FILE = "key-store-file";
        // The key-store-file value
        static final String KEY_STORE_FILE_VALUE = "./data/metacfg4j.keystore";
        // The alias key
        public static final String ALIAS = "alias";
        // The alias value
        static final String ALIAS_VALUE = "alias";
        // The store password key
        public static final String STORE_PASSWORD = "store-password";
        // The store password value
        static final String STORE_PASSWORD_VALUE = "password";
        // The key password key
        public static final String KEY_PASSWORD = "key-password";
        // The key password value
        static final String KEY_PASSWORD_VALUE = "password";
    }
}
