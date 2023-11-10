package com.spg.applicationTask.api.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * The task model that contains project, assignee and description.
 */
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

    /**
     * Returns an id of the task.
     *
     * @return an id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets an id of the task.
     *
     * @param id a project id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns a title of the task.
     *
     * @return a title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns a description of the task.
     *
     * @return a description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a dueDate of the task.
     *
     * @return a dueDate.
     */
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * Returns a status of the task.
     *
     * @return a status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns an optional of assignee of the task.
     *
     * @return an optional of assignee.
     */
    public Optional<User> getAssignee() {
        return Optional.ofNullable(assignee);
    }

    /**
     * Returns an optional of project of the task.
     *
     * @return an optional of project.
     */
    public Optional<Project> getProject() {
        return Optional.ofNullable(project);
    }

    /**
     * Sets a project of the task.
     *
     * @param project a project of task.
     */
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
        return Objects.equals(id, task.id) &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(createdDate, task.createdDate) &&
                Objects.equals(dueDate, task.dueDate) &&
                Objects.equals(status, task.status) &&
                Objects.equals(assignee, task.assignee) &&
                Objects.equals(project, task.project);
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

        /**
         * Constructs a task model with the id parameter.
         *
         * @param id a task id.
         * @return a builder of the task model.
         */
        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        /**
         * Constructs a task model with the title parameter.
         *
         * @param title a task title.
         * @return a builder of the task model.
         */
        public Builder title(final String title) {
            this.title = title;
            return this;
        }

        /**
         * Constructs a task model with the description parameter.
         *
         * @param description a task description.
         * @return a builder of the task model.
         */
        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        /**
         * Constructs a task model with the createdDate parameter.
         *
         * @param createdDate a task createdDate.
         * @return a builder of the task model.
         */
        public Builder createdDate(final LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        /**
         * Constructs a task model with the dueDate parameter.
         *
         * @param dueDate a task dueDate.
         * @return a builder of the task model.
         */
        public Builder dueDate(final LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        /**
         * Constructs a task model with the status parameter.
         *
         * @param status a task status.
         * @return a builder of the task model.
         */
        public Builder status(final String status) {
            this.status = status;
            return this;
        }

        /**
         * Constructs a task model with the assignee parameter.
         *
         * @param assignee a task assignee.
         * @return a builder of the task model.
         */
        public Builder assignee(final User assignee) {
            this.assignee = assignee;
            return this;
        }

        /**
         * Constructs a task model with the project parameter.
         *
         * @param project a task project.
         * @return a builder of the task model.
         */
        public Builder project(final Project project) {
            this.project = project;
            return this;
        }

        /**
         * Builds a task model with required parameters.
         *
         * @return a builder of the task model.
         */
        public Task build() {
            return new Task(this);
        }
    }
}
