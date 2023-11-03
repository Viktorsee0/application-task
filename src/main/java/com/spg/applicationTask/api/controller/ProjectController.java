package com.spg.applicationTask.api.controller;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.spg.applicationTask.api.dto.ProjectDTO;
import com.spg.applicationTask.engine.IoC.annotation.Inject;
import com.spg.applicationTask.api.service.ProjectService;
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

@RestController(apiPath = "/project")
public class ProjectController extends AbstractController {

    @Inject
    private ProjectService projectService;

    private final static Logger LOGGER = Logger.getLogger(UserController.class.getSimpleName());

    @Override
    protected void doGet(HttpExchange httpExchange) throws IOException {
        Optional<String> id = getQuery(httpExchange.getRequestURI(), "id");
        if (id.isPresent()) {
            LOGGER.log(Level.INFO, "GETTING id:" + id.get());
            writeResponse(httpExchange, projectService.getProject(Integer.parseInt(id.get())));
        } else {
            LOGGER.log(Level.INFO, "GETTING all");
            writeResponse(httpExchange, projectService.getProjects());
        }
    }

    @Override
    protected void doPost(HttpExchange httpExchange) throws IOException {
        JsonArray json = readRequestBody(httpExchange);
        List<ProjectDTO> projects = json.stream()
                .map(project -> new ProjectDTO.Builder((JsonObject) project).build())
                .toList();
        LOGGER.log(Level.INFO, "SAVING: " + Arrays.toString(projects.toArray()));
        List<ProjectDTO> saved = projectService.save(projects);
        writeResponse(httpExchange, saved);
    }

    @Override
    protected void doDelete(HttpExchange httpExchange) throws IOException {
        Optional<String> id = getQuery(httpExchange.getRequestURI(), "id");
        if (id.isPresent()) {
            LOGGER.log(Level.INFO, "DELETING id:" + id);
            projectService.delete(Integer.parseInt(id.get()));
            writeResponse(httpExchange, HTTP_NO_CONTENT);
        }
    }
}
