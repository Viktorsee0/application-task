package com.spg.applicationTask.api.dto;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.spg.applicationTask.api.model.Project;
import com.spg.applicationTask.engine.extension.ExtJsonable;
import com.spg.applicationTask.engine.extension.Validator;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.spg.applicationTask.engine.extension.JsonUtils.getInt;

/**
 * DTO for project
 *
 * @see Project
 */
public class ProjectDTO implements ExtJsonable {

    private int id;
    private String name;
    private String description;
    private List<TaskDTO> tasks;

    public ProjectDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.tasks = builder.tasks;
    }

    /**
     * Returns an id of the project.
     *
     * @return a project id.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a name of the project.
     *
     * @return a project name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a description of the project.
     *
     * @return a project description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns tasks of the project.
     *
     * @return project tasks.
     */
    public List<TaskDTO> getTasks() {
        return tasks;
    }

    /**
     * Sets the tasks to the project
     *
     * @param tasks the list of tasks
     */
    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void toJson(Writer writer) throws IOException {
        final JsonObject json = new JsonObject();
        json.put("id", id);
        json.put("name", name);
        json.put("description", description);
        json.put("tasks", new ArrayList<>(tasks));
        json.toJson(writer);
    }

    public final static class Builder {
        private int id;
        private String name;
        private String description;
        private List<TaskDTO> tasks = new ArrayList<>();

        public Builder() {
        }

        /**
         * Constructs a project dto based on the json object.
         *
         * @param jsonObject a json object with the project dto.
         */
        public Builder(JsonObject jsonObject) {
            final JsonObject prototype = Validator.of(jsonObject).get();
            this.id = getInt(jsonObject, "id");
            this.name = Validator.of((String) jsonObject.get("name")).get();
            this.description = Validator.of((String) jsonObject.get("description")).get();
            this.tasks = getTasks(jsonObject).toList();
        }

        /**
         * Constructs a project dto with the id parameter.
         *
         * @param id a project dto id.
         * @return a builder of the project dto.
         */
        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        /**
         * Constructs a project dto with the name parameter.
         *
         * @param name a project dto name.
         * @return a builder of the project dto.
         */
        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Constructs a project dto with the description parameter.
         *
         * @param description a project dto description.
         * @return a builder of the project dto.
         */
        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        /**
         * Constructs a project dto with the tasks parameter.
         *
         * @param tasks a project dto tasks.
         * @return a builder of the project dto.
         */
        public Builder tasks(final List<TaskDTO> tasks) {
            this.tasks.clear();
            this.tasks.addAll(tasks);
            return this;
        }

        /**
         * Constructs a project dto with the task parameter.
         *
         * @param task a project dto task.
         * @return a builder of the project dto.
         */
        public Builder task(final TaskDTO task) {
            this.tasks.add(task);
            return this;
        }

        /**
         * Returns tasks which belong to project.
         *
         * @param jsonObject a raw json object.
         * @return attributes as a map.
         */
        static Stream<TaskDTO> getTasks(final JsonObject jsonObject) {
            final JsonArray jsonProperties = (JsonArray) jsonObject.get("tasks");
            return jsonProperties != null ?
                    jsonProperties.stream().map(json -> new TaskDTO.Builder((JsonObject) json).build()) :
                    Stream.empty();
        }

        /**
         * Builds a project dto with required parameters.
         *
         * @return a builder of the project dto.
         */
        public ProjectDTO build() {
            return new ProjectDTO(this);
        }
    }
}
