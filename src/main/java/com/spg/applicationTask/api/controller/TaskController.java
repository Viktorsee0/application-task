package com.spg.applicationTask.api.controller;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.spg.applicationTask.api.dto.TaskDTO;
import com.spg.applicationTask.api.service.TaskService;
import com.spg.applicationTask.engine.IoC.annotation.Inject;
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

@RestController(apiPath = "/task")
public final class TaskController extends AbstractController {

    @Inject
    private TaskService taskService;

    private final static Logger LOGGER = Logger.getLogger(TaskController.class.getSimpleName());

    @Override
    protected void doDelete(HttpExchange httpExchange) {
        Optional<String> id = getQuery(httpExchange.getRequestURI(), "id");
        if (id.isPresent()) {
            LOGGER.log(Level.INFO, "DELETING id:" + id);
            taskService.delete(Integer.parseInt(id.get()));
            writeResponse(httpExchange, HTTP_NO_CONTENT);
        }
    }

    @Override
    protected void doPost(HttpExchange httpExchange) throws IOException {
        JsonArray json = readRequestBody(httpExchange);
        List<TaskDTO> tasks = json.stream().map(
                        task -> new TaskDTO.Builder((JsonObject) task).build())
                .toList();
        LOGGER.log(Level.INFO, "SAVING: " + Arrays.toString(tasks.toArray()));
        List<TaskDTO> saved = taskService.save(tasks);
        writeResponse(httpExchange, saved);
    }
}
