package com.spg.applicationTask.engine.web;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.spg.applicationTask.engine.web.exception.InvalidRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.spg.applicationTask.engine.web.WebConstants.Messages.JSON_ERROR;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

/**
 * The internal implementation of the web client.
 */
public final class WebClient<T> {
    private final static Logger LOGGER = Logger.getLogger(WebClient.class.getSimpleName());
    private String url;
    private HttpMethod method;
    private String request;
    private T content;
    private int rCode;

    /**
     * Constructs the client based on url and http method.
     *
     * @param url an url of a web client.
     * @param method a method of a web client.
     */
    public WebClient(String url, HttpMethod method) {
        this(url, method, null);
    }

    /**
     * Constructs the client based on url, http method and request body.
     *
     * @param url an url of a web client.
     * @param method a method of a web client.
     * @param request a request body of a web client.
     */
    public WebClient(String url, HttpMethod method, String request) {
        this.url = url;
        this.method = method;
        this.request = request;

        HttpURLConnection connection = openConnection();
        try {
            if (request != null) {
                connection.setDoOutput(true);
                writeContent(connection);
            }
            if (connection.getResponseCode() > 299) {
                content = readContent(connection.getErrorStream());
            } else {
                if (!Objects.equals(connection.getResponseMessage(), "No Content")) {
                    content = readContent(connection.getInputStream());
                }
            }
            rCode = connection.getResponseCode();
        } catch (IOException | JsonException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Returns a rCode of the response.
     *
     * @return the rCode.
     */
    public int getCode() {
        return rCode;
    }

    /**
     * Returns a content of the response.
     *
     * @return the content.
     */
    public T getContent() {
        return content;
    }

    private HttpURLConnection openConnection() {
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod(method.name());
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private T readContent(final InputStream inputStream) throws IOException, JsonException {
        try (final BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return deserialize(bufferedReader);
        } catch (final JsonException e) {
            LOGGER.log(Level.SEVERE, e.toString());
            throw new InvalidRequestException(HTTP_BAD_REQUEST, JSON_ERROR);
        }
    }

    private T deserialize(BufferedReader bufferedReader) throws JsonException {
        return (T) Jsoner.deserialize(bufferedReader);
    }

    private void writeContent(final HttpURLConnection connection) throws IOException {
        try (final OutputStream outputStream = connection.getOutputStream()) {
            final byte[] input = request.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }
    }
}
