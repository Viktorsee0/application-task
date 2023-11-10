package com.spg.applicationTask.api.config;

import com.spg.applicationTask.engine.IoC.annotation.ComponentFactory;
import com.spg.applicationTask.engine.IoC.annotation.Configuration;
import com.spg.applicationTask.engine.IoC.annotation.Value;
import com.spg.applicationTask.engine.web.ServerConfig;
import com.spg.applicationTask.engine.web.ServerConfig.Property;

/**
 * The configuration class for server.
 */
@Configuration
public class ServerConfiguration {

    @Value("host")
    private String host;
    @Value("port")
    private String port;

    /**
     * Returns a configuration of server.
     *
     * @Return a serverConfig.
     */
    @ComponentFactory
    public ServerConfig getServerConfig() {
        return new ServerConfig.Builder()
                .property(Property.HOST, host)
                .property(Property.PORT, port)
                .build();
    }
}
