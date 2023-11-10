package com.spg.applicationTask.engine.web;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.spg.applicationTask.engine.web.exception.InvalidRequestException;
import com.spg.applicationTask.engine.web.exception.MethodNotAllowedException;
import com.spg.applicationTask.engine.web.exception.MethodNotImplementedException;
import com.spg.applicationTask.engine.web.exception.ServerException;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spg.applicationTask.engine.extension.JsonUtils.toJson;
import static com.spg.applicationTask.engine.web.WebConstants.Header.APPLICATION_JSON;
import static com.spg.applicationTask.engine.web.WebConstants.Messages.JSON_ERROR;
import static com.spg.applicationTask.engine.web.WebConstants.Messages.METHOD_NOT_ALLOWED;
import static com.spg.applicationTask.engine.web.WebConstants.Messages.METHOD_NOT_IMPLEMENTED;
import static java.net.HttpURLConnection.HTTP_BAD_METHOD;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_IMPLEMENTED;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Provides a handler functionality for the http methods.
 */
public abstract class AbstractController {

    private final static Logger LOGGER = Logger.getLogger(AbstractController.class.getSimpleName());

    protected String apiPath;

    public AbstractController(String apiPath) {
        this.apiPath = apiPath;
    }

    public AbstractController() {

    }

    /**
     * Handles the http method GET
     *
     * @param httpExchange a http exchange.
     * @throws IOException when a controller encounters a problem.
     */
    protected void doGet(final HttpExchange httpExchange) throws IOException {
        throw new MethodNotAllowedException(HTTP_BAD_METHOD, METHOD_NOT_ALLOWED);
    }

    /**
     * Handles the http method POST
     *
     * @param httpExchange a http exchange.
     * @throws IOException when a controller encounters a problem.
     */
    protected void doPost(final HttpExchange httpExchange) throws IOException {
        throw new MethodNotAllowedException(HTTP_BAD_METHOD, METHOD_NOT_ALLOWED);
    }

    /**
     * Handles the http method DELETE
     *
     * @param httpExchange a http exchange.
     * @throws IOException when a controller encounters a problem.
     */
    protected void doDelete(final HttpExchange httpExchange) throws IOException {
        throw new MethodNotAllowedException(HTTP_BAD_METHOD, METHOD_NOT_ALLOWED);
    }

    /**
     * Handles a referred context.
     *
     * @param httpExchange a http exchange.
     * @see HttpExchange for more information.
     */
    protected void handle(final HttpExchange httpExchange) {
        try {
            service(httpExchange);
        } catch (final Exception e) {
            handle(httpExchange, e);
        } finally {
            httpExchange.close();
        }
    }

    /**
     * Redirects to right http method.
     *
     * @param httpExchange a http exchange.
     * @throws IOException                   when a controller encounters a problem.
     * @throws MethodNotImplementedException when a method haven't been implemented yet.
     * @see HttpExchange for more information.
     */
    private void service(final HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        switch (HttpMethod.valueOf(method)) {
            case GET -> doGet(httpExchange);
            case POST -> doPost(httpExchange);
            case DELETE -> doDelete(httpExchange);
            default -> throw new MethodNotImplementedException(HTTP_NOT_IMPLEMENTED, METHOD_NOT_IMPLEMENTED);
        }
    }

    /**
     * Writes an operation response.
     *
     * @param httpExchange a http exchange.
     * @param response     an operation response.
     * @param <T>          a type of result.
     * @throws IOException when a controller encounters a problem.
     * @see HttpExchange for more information.
     */
    protected <T extends Jsonable> void writeResponse(final HttpExchange httpExchange,
                                                      final List<T> response) {
        try {
            httpExchange.getResponseHeaders().put("Content-Type", Collections.singletonList(APPLICATION_JSON));
            String jsonResponse = toJson(response);
            httpExchange.sendResponseHeaders(HTTP_OK, jsonResponse.length());
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(jsonResponse.getBytes());
            outputStream.flush();

        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new InvalidRequestException(HTTP_BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Writes an operation response.
     *
     * @param httpExchange a http exchange.
     * @param response     an operation response.
     * @param <T>          a type of result.
     * @throws IOException when a controller encounters a problem.
     * @see HttpExchange for more information.
     */
    protected <T extends Jsonable> void writeResponse(final HttpExchange httpExchange,
                                                      final T response) {
        try {
            httpExchange.getResponseHeaders().put("Content-Type", Collections.singletonList(APPLICATION_JSON));
            StringBuilder jsonResponse = new StringBuilder();
            jsonResponse.append(response.toJson());
            httpExchange.sendResponseHeaders(HTTP_OK, jsonResponse.length());
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(jsonResponse.toString().getBytes());
            outputStream.flush();
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new InvalidRequestException(HTTP_BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Writes an operation response.
     *
     * @param httpExchange a http exchange.
     * @see HttpExchange for more information.
     */
    protected void writeResponse(final HttpExchange httpExchange,
                                 int rCode) {
        try {
            httpExchange.sendResponseHeaders(rCode, 0);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            throw new InvalidRequestException(HTTP_BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Gets a query from URI
     *
     * @param uri   a uri.
     * @param param an param.
     */
    protected Optional<String> getQuery(final URI uri, final String param) {
        final String query = uri.getQuery();
        return query != null ?
                Arrays.stream(query.split("&")).
                        filter(q -> q.contains(param)).
                        map(p -> p.split("=")[1]).
                        findFirst() :
                Optional.empty();
    }

    /**
     * Reads requests body and returns it as JsonArray
     *
     * @param httpExchange a http exchange.
     * @throws IOException when a controller encounters a problem.
     * @see JsonArray ,HttpExchange  for more information
     */
    protected JsonArray readRequestBody(HttpExchange httpExchange) throws IOException {
        try (final BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8))) {
            return (JsonArray) Jsoner.deserialize(bufferedReader);
        } catch (final JsonException e) {
            LOGGER.log(Level.SEVERE, e.toString());
            throw new InvalidRequestException(HTTP_BAD_REQUEST, JSON_ERROR);
        }
    }

    /**
     * Returns an apiPath of the controller.
     *
     * @return an apiPath.
     */
    public String getApiPath() {
        return apiPath;
    }

    /**
     * Sets an apiPath of the controller.
     *
     * @param apiPath a controller apiPath.
     */
    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
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

    private ErrorResponse getErrorResponse(final Throwable throwable,
                                           final HttpExchange httpExchange) throws IOException {
        final ErrorResponse.Builder response = new ErrorResponse.Builder();

        if (throwable instanceof final ServerException exception) {
            httpExchange.sendResponseHeaders(exception.getCode(), 0);
            response.message(exception.getMessage());
            response.error(exception.getCode());
        } else if (throwable instanceof final IllegalStateException exception) {
            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            String messages = Arrays.stream(exception.getSuppressed())
                    .map(Throwable::getMessage)
                    .reduce("", (accumulator, element) -> accumulator + element + "\n");
            response.message(messages);
            response.error(HTTP_BAD_REQUEST);
        } else {
            httpExchange.sendResponseHeaders(HTTP_INTERNAL_ERROR, 0);
            response.message(throwable.getMessage());
            response.error(HTTP_INTERNAL_ERROR);
        }

        return response.build();
    }
}

