package com.spg.applicationTask.engine.web;

import com.spg.applicationTask.engine.extension.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ServerConfig {

    private Map<Property, String> properties;

    private ServerConfig(final Builder builder) {
        this.properties = Map.copyOf(builder.properties);
    }

    public void setProperties(Map<Property, String> properties) {
        this.properties = properties;
    }

    public Optional<String> getProperty(Property p) {
        return Optional.ofNullable(properties.get(p));
    }

    public enum Property {
        PORT, HOST, KEY_STORE_FILE, STORE_PASSWORD
    }

    public final static class Builder {

        private final Map<Property, String> properties = new HashMap<>();

        public Builder() {
        }

        public Builder property(final Property property, final String value) {
            this.properties.put(Validator.of(property).get(), Validator.of(value).get());
            return this;
        }

        public ServerConfig build() {
            return new ServerConfig(this);
        }
    }
}
