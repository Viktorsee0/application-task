package com.spg.applicationTask.engine;

import com.spg.applicationTask.controller.JustController;
import com.spg.applicationTask.extension.Validator;

import java.util.*;

public final class ServerConfig {

    private final List<AbstractController> controllers;
    private final Map<Property, String> properties;


    private ServerConfig(final Builder builder) {
        this.properties = Map.copyOf(builder.properties);
        this.controllers = List.copyOf(builder.controllers);
    }

    public Optional<String> getProperty(Property p) {
        return Optional.ofNullable(properties.get(p));
    }

    public List<AbstractController> getControllers() {
        return controllers;
    }

    enum Property {
        PORT, HOST
    }

    public final static class Builder {

        private final List<AbstractController> controllers = new ArrayList<>();
        private final Map<Property, String> properties = new HashMap<>();

        public Builder() {
        }

        public Builder property(final Property property, final String value) {
            this.properties.put(Validator.of(property).get(), Validator.of(value).get());
            return this;
        }

        public Builder controller(final JustController controller) {
            this.controllers.add(Validator.of(controller).get());
            return this;
        }

        public Builder controllers(final List<AbstractController> controllers) {
            this.controllers.addAll(Validator.of(controllers).get());
            return this;
        }

        public ServerConfig build() {
            return new ServerConfig(this);
        }
    }
}
