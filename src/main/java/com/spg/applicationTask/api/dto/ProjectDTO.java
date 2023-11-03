package com.spg.applicationTask.api.dto;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.spg.applicationTask.engine.extension.ExtJsonable;
import com.spg.applicationTask.engine.extension.Validator;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.spg.applicationTask.engine.extension.JsonUtils.getInt;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

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

        public Builder(JsonObject jsonObject) {
            final JsonObject prototype = Validator.of(jsonObject).get();
            this.id = getInt(jsonObject, "id");
            this.name = Validator.of((String) jsonObject.get("name")).get();
            this.description = Validator.of((String) jsonObject.get("description")).get();
            this.tasks = getTasks(jsonObject).toList();
        }

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public Builder tasks(final List<TaskDTO> tasks) {
            this.tasks.clear();
            this.tasks.addAll(tasks);
            return this;
        }

        public Builder task(final TaskDTO task) {
            this.tasks.add(task);
            return this;
        }

        static Stream<TaskDTO> getTasks(final JsonObject jsonObject) {
            final JsonArray jsonProperties = (JsonArray) jsonObject.get("tasks");
            return jsonProperties != null ?
                    jsonProperties.stream().map(json -> new TaskDTO.Builder((JsonObject) json).build()) :
                    Stream.empty();
        }

        public ProjectDTO build() {
            return new ProjectDTO(this);
        }
    }
}
