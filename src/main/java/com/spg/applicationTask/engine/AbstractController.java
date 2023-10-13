package com.spg.applicationTask.engine;

import com.spg.applicationTask.engine.exception.InternalServerErrorException;
import com.spg.applicationTask.engine.exception.InvalidRequestException;
import com.spg.applicationTask.engine.exception.MethodNotAllowedException;
import com.spg.applicationTask.engine.exception.ResourceNotFoundException;
import com.spg.applicationTask.extension.Validator;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spg.applicationTask.engine.ServerConstants.Header.APPLICATION_JSON;
import static java.net.HttpURLConnection.*;

public abstract class AbstractController {
    private final static Logger LOGGER = Logger.getLogger(AbstractController.class.getSimpleName());

    final String apiPath;
//    final ConfigService configService;

    protected AbstractController(final AbstractBuilder abstractBuilder) {
        this.apiPath = abstractBuilder.apiPath;
//        this.configService = abstractBuilder.configService;
    }

    /**
     * Handles a referred context.
     *
     * @param httpExchange a http exchange.
     * @see HttpExchange for more information.
     */
    protected void handle(final HttpExchange httpExchange) {
        try {
            execute(httpExchange);
        } catch (final Exception e) {
            handle(httpExchange, e);
        } finally {
            httpExchange.close();
        }
    }

    /**
     * The main method that handles a context.
     *
     * @param httpExchange a http exchange.
     * @throws IOException when a controller encounters a problem.
     * @see HttpExchange for more information.
     */
    public abstract void execute(final HttpExchange httpExchange) throws IOException;

    /**
     * Writes an operation response.
     *
     * @param httpExchange a http exchange.
     * @param response     an operation response.
     * @param <T>          a type of result.
     * @throws IOException when a controller encounters a problem.
     * @see HttpExchange for more information.
     */
    protected <T> void writeResponse(final HttpExchange httpExchange, final OperationResponse<T> response) throws IOException {
        try {
            httpExchange.getResponseHeaders().put("Content-Type", Collections.singletonList(APPLICATION_JSON));
            final byte[] jsonBytes = response.toJson().getBytes();
            httpExchange.sendResponseHeaders(HTTP_OK, jsonBytes.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(jsonBytes);
            outputStream.flush();
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new InvalidRequestException(HTTP_BAD_REQUEST, e.getMessage());
        }
    }

    private void handle(final HttpExchange httpExchange, final Throwable throwable) {
        try {
            LOGGER.log(Level.WARNING, throwable.getMessage());
            throwable.printStackTrace();

            final OutputStream responseBody = httpExchange.getResponseBody();
            responseBody.write(getErrorResponse(throwable, httpExchange).toJson().getBytes());
            responseBody.close();
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, throwable.getMessage());
            e.printStackTrace();
        }
    }

    private <T> OperationResponse<T> getErrorResponse(final Throwable throwable, final HttpExchange httpExchange) throws IOException {

        final OperationResponse<T> response = new OperationResponse.Builder<T>().error(throwable.getMessage()).build();

        if (throwable instanceof InvalidRequestException) {
            final InvalidRequestException exception = (InvalidRequestException) throwable;
            httpExchange.sendResponseHeaders(exception.getCode(), 0);
        } else if (throwable instanceof ResourceNotFoundException) {
            final ResourceNotFoundException exception = (ResourceNotFoundException) throwable;
            httpExchange.sendResponseHeaders(exception.getCode(), 0);
        } else if (throwable instanceof MethodNotAllowedException) {
            final MethodNotAllowedException exception = (MethodNotAllowedException) throwable;
            httpExchange.sendResponseHeaders(exception.getCode(), 0);
        } else {
            if (throwable instanceof InternalServerErrorException) {
                httpExchange.sendResponseHeaders(((InternalServerErrorException) throwable).getCode(), 0);
            } else {
                httpExchange.sendResponseHeaders(HTTP_INTERNAL_ERROR, 0);
            }
        }

        return response;
    }

    /**
     * Wraps and builds instances of controllers.
     */
    protected static abstract class AbstractBuilder {
        private final String apiPath;
//        private final ConfigService configService;

        /**
         * Constructs a controller with the configuration service param.
         *
         * @param apiPath       an api path.
         */
        protected AbstractBuilder(final String apiPath) {
            this.apiPath = Validator.of(apiPath).get();
        }

        /**
         * Builds a controller with the required parameter.
         *
         * @return a builder of the controller.
         */
        protected abstract AbstractController build();
    }
}
