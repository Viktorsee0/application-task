package com.spg.applicationTask.api.controller;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.spg.applicationTask.api.dto.UserDTO;
import com.spg.applicationTask.engine.IoC.annotation.Inject;
import com.spg.applicationTask.api.service.UserService;
import com.spg.applicationTask.engine.web.AbstractController;
import com.spg.applicationTask.engine.web.RestController;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.net.HttpURLConnection.HTTP_NO_CONTENT;

@RestController(apiPath = "/user")
public final class UserController extends AbstractController {

    @Inject
    private UserService userService;

    private final static Logger LOGGER = Logger.getLogger(UserController.class.getSimpleName());

    @Override
    protected void doGet(HttpExchange httpExchange) throws IOException {
        Optional<String> id = getQuery(httpExchange.getRequestURI(), "id");
        if (id.isEmpty()) {
            LOGGER.log(Level.INFO, "GETTING ALL USERS");
            writeResponse(httpExchange, userService.getUsers());
        } else {
            LOGGER.log(Level.INFO, "GETTING USER BY ID, ID=" + id.get());
            writeResponse(httpExchange, userService.getUser(Integer.parseInt(id.get())));
        }
    }

    @Override
    protected void doPost(HttpExchange httpExchange) throws IOException {
        JsonArray json = readRequestBody(httpExchange);
        List<UserDTO> users = json.stream().map(
                        user -> new UserDTO.Builder((JsonObject) user).build())
                .toList();
        LOGGER.log(Level.INFO, "SAVING: " + Arrays.toString(users.toArray()));
        List<UserDTO> saved = userService.save(users);
        writeResponse(httpExchange, saved);
    }

    @Override
    protected void doDelete(HttpExchange httpExchange) {
        Optional<String> id = getQuery(httpExchange.getRequestURI(), "id");
        if (id.isPresent()) {
            LOGGER.log(Level.INFO, "DELETING id:" + id);
            userService.delete(Integer.parseInt(id.get()));
            writeResponse(httpExchange, HTTP_NO_CONTENT);
        }
    }
}
