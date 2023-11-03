package com.spg.applicationTask.api.dto;


import com.github.cliftonlabs.json_simple.JsonObject;
import com.spg.applicationTask.api.utils.ExtJsonable;
import com.spg.applicationTask.api.utils.Validator;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.spg.applicationTask.api.utils.JsonUtils.getInt;

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

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }

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
        private LocalDateTime createdDate;
        private LocalDateTime dueDate;
        private String status;
        private AssigneeDTO assignee;

        public Builder() {

        }

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

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public Builder dueDate(final LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder status(final String status) {
            this.status = status;
            return this;
        }

        public Builder assignee(final AssigneeDTO assignee) {
            this.assignee = assignee;
            return this;
        }

        public TaskDTO build() {
            return new TaskDTO(this);
        }
    }
}
