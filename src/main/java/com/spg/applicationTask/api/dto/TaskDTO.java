package com.spg.applicationTask.api.dto;


import com.github.cliftonlabs.json_simple.JsonObject;
import com.spg.applicationTask.api.model.Task;
import com.spg.applicationTask.engine.extension.ExtJsonable;
import com.spg.applicationTask.engine.extension.Validator;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.spg.applicationTask.engine.extension.JsonUtils.getInt;

/**
 * DTO for task
 *
 * @see Task
 */
public class TaskDTO implements ExtJsonable {

    private Integer id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private String status;
    private AssigneeDTO assignee;

    public TaskDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.dueDate = builder.dueDate;
        this.status = builder.status;
        this.assignee = builder.assignee;
    }

    /**
     * Returns an id of the task.
     *
     * @return a task id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns a title of the task.
     *
     * @return a task title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns a description of the task.
     *
     * @return a task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a dueDate of the task.
     *
     * @return a task dueDate.
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Returns a status of the task.
     *
     * @return a task status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns an assignee of the task.
     *
     * @return a task assignee.
     */
    public AssigneeDTO getAssignee() {
        return assignee;
    }

    @Override
    public void toJson(Writer writer) throws IOException {
        final JsonObject json = new JsonObject();
        json.put("id", id);
        json.put("title", title);
        json.put("description", description);
        json.put("dueDate", dueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        json.put("status", status);
        json.put("assignee", assignee);
        json.toJson(writer);
    }

    public final static class Builder {
        private int id;
        private String title;
        private String description;
        private LocalDateTime dueDate;
        private String status;
        private AssigneeDTO assignee;

        public Builder() {

        }
        /**
         * Constructs a task dto based on the json object.
         *
         * @param jsonObject a json object with the task dto.
         */
        public Builder(JsonObject jsonObject) {
            final JsonObject prototype = Validator.of(jsonObject).get();
            this.id = getInt(prototype, "id");
            this.title = Validator.of((String) prototype.get("title")).get();
            this.description = Validator.of((String) prototype.get("description")).get();
            this.dueDate = LocalDateTime.parse(Validator.of((String) prototype.get("dueDate")).get(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.status = Validator.of((String) prototype.get("status")).get();
            if (jsonObject.get("assignee") != null) {
                this.assignee = new AssigneeDTO.Builder((JsonObject) jsonObject.get("assignee"))
                        .build();
            }
        }

        /**
         * Constructs a task dto with the id parameter.
         *
         * @param id a task dto id.
         * @return a builder of the task dto.
         */
        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        /**
         * Constructs a task dto with the title parameter.
         *
         * @param title a task dto title.
         * @return a builder of the task dto.
         */
        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        /**
         * Constructs a task dto with the description parameter.
         *
         * @param description a task dto description.
         * @return a builder of the task dto.
         */
        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        /**
         * Constructs a task dto with the dueDate parameter.
         *
         * @param dueDate a task dto dueDate.
         * @return a builder of the task dto.
         */
        public Builder dueDate(final LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        /**
         * Constructs a task dto with the status parameter.
         *
         * @param status a task dto status.
         * @return a builder of the task dto.
         */
        public Builder status(final String status) {
            this.status = status;
            return this;
        }

        /**
         * Constructs a task dto with the assignee parameter.
         *
         * @param assignee a task dto assignee.
         * @return a builder of the task dto.
         */
        public Builder assignee(final AssigneeDTO assignee) {
            this.assignee = assignee;
            return this;
        }

        /**
         * Builds a task dto with required parameters.
         *
         * @return a builder of the task dto.
         */
        public TaskDTO build() {
            return new TaskDTO(this);
        }
    }
}
