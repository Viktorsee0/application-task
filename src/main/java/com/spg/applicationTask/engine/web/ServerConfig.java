package com.spg.applicationTask.engine.web;

import com.spg.applicationTask.engine.extension.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Class for configure a web server.
 *
 * @see Server
 */
public final class ServerConfig {

    private Map<Property, String> properties;

    private ServerConfig(final Builder builder) {
        this.properties = Map.copyOf(builder.properties);
    }

    /**
     * Sets properties of the serverConfig.
     *
     * @param properties a serverConfig properties.
     */
    public void setProperties(Map<Property, String> properties) {
        this.properties = properties;
    }


    /**
     * Returns a property by key.
     *
     * @param p is a property key
     * @return a property value.
     * @see Property
     */
    public Optional<String> getProperty(Property p) {
        return Optional.ofNullable(properties.get(p));
    }

    /**
     * Describes existing properties key
     */
    public enum Property {
        PORT, HOST, KEY_STORE_FILE, STORE_PASSWORD
    }

    public final static class Builder {

        private final Map<Property, String> properties = new HashMap<>();

        /**
         * Constructs a serverConfig with the property key and property value.
         *
         * @param property a property key.
         * @param value    a property value.
         * @return a builder of the serverConfig.
         */
        public Builder property(final Property property, final String value) {
            this.properties.put(Validator.of(property).get(), Validator.of(value).get());
            return this;
        }

        /**
         * Builds a serverConfig with required parameters.
         *
         * @return a builder of the serverConfig.
         */
        public ServerConfig build() {
            return new ServerConfig(this);
        }
    }
}
