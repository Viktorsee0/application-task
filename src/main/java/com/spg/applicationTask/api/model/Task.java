package com.spg.applicationTask.api.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public final class Task {

    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private LocalDateTime dueDate;
    private String status;
    private User assignee;
    private Project project;

    public Task(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.createdDate = builder.createdDate;
        this.dueDate = builder.dueDate;
        this.status = builder.status;
        this.assignee = builder.assignee;
        this.project = builder.project;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Optional<User> getAssignee() {
        return Optional.ofNullable(assignee);
    }

    public Optional<Project> getProject() {
        return Optional.ofNullable(project);
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", dueDate=" + dueDate +
                ", status='" + status + '\'' +
                ", assignee=" + assignee +
                ", project=" + project +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(createdDate, task.createdDate) && Objects.equals(dueDate, task.dueDate) && Objects.equals(status, task.status) && Objects.equals(assignee, task.assignee) && Objects.equals(project, task.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, createdDate, dueDate, status, assignee, project);
    }

    public final static class Builder {
        private int id;
        private String title;
        private String description;
        private LocalDateTime createdDate;
        private LocalDateTime dueDate;
        private String status;
        private User assignee;
        private Project project;

        public Builder() {

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

        public Builder createdDate(final LocalDateTime createdDate) {
            this.createdDate = createdDate;
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

        public Builder assignee(final User assignee) {
            this.assignee = assignee;
            return this;
        }

        public Builder project(final Project project) {
            this.project = project;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }
}
