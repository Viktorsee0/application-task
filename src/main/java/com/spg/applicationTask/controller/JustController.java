package com.spg.applicationTask.controller;

import com.spg.applicationTask.annotation.Value;
import com.spg.applicationTask.engine.AbstractController;
import com.spg.applicationTask.engine.OperationResponse;
import com.spg.applicationTask.engine.exception.MethodNotAllowedException;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.logging.Logger;


import static com.spg.applicationTask.engine.ServerConstants.Method.GET;
import com.spg.applicationTask.Constants.Messages;
import static java.net.HttpURLConnection.HTTP_BAD_METHOD;

public final class JustController extends AbstractController {

    private final static Logger LOGGER = Logger.getLogger(JustController.class.getSimpleName());

    public JustController(Builder builder) {
        super(builder);
    }

    @Override
    public void execute(HttpExchange httpExchange) throws IOException {
        if (GET.equals(httpExchange.getRequestMethod())) {
            writeResponse(httpExchange, new OperationResponse.Builder<String>().result("JUST").build());
        } else {
            throw new MethodNotAllowedException(HTTP_BAD_METHOD, Messages.METHOD_NOT_ALLOWED);
        }
    }

    public final static class Builder extends AbstractBuilder {

        /**
         * Constructs a controller with the configuration service param.
         *
         * @param apiPath       an api path.
         */
        public Builder(final String apiPath) {
            super(apiPath);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public JustController build() {
            return new JustController(this);
        }
    }
}
