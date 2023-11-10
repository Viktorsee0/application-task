package com.spg.applicationTask.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The project model that contains tasks.
 */
public final class Project {

    private int id;
    private String name;
    private String description;
    private List<Task> tasks;

    public Project(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.tasks = builder.tasks;
    }

    /**
     * Adds a task of the project.
     *
     * @param task a project task.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Returns an id of the project.
     *
     * @return an id.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a name of the project.
     *
     * @return a name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a description of the project.
     *
     * @return a description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a tasks of the project.
     *
     * @return a tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Sets an id of the project.
     *
     * @param id a project id.
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tasks=" + tasks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id &&
                Objects.equals(name, project.name) &&
                Objects.equals(description, project.description) &&
                Objects.equals(tasks, project.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, tasks);
    }

    public final static class Builder {
        private int id;
        private String name;
        private String description;
        private List<Task> tasks = new ArrayList<>();

        /**
         * Constructs a project model with the id parameter.
         *
         * @param id a project id.
         * @return a builder of the project model.
         */
        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        /**
         * Constructs a project model with the name parameter.
         *
         * @param name a project name.
         * @return a builder of the project model.
         */
        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        /**
         * Constructs a project model with the description parameter.
         *
         * @param description a project description.
         * @return a builder of the project model.
         */
        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        /**
         * Constructs a project model with the tasks parameters.
         *
         * @param tasks a project tasks.
         * @return a builder of the project model.
         */
        public Builder tasks(final List<Task> tasks) {
            this.tasks.clear();
            this.tasks.addAll(tasks);
            return this;
        }

        /**
         * Constructs a project model with the task parameter.
         *
         * @param task a project task.
         * @return a builder of the project model.
         */
        public Builder task(final Task task) {
            this.tasks.add(task);
            return this;
        }


        /**
         * Builds a project model with required parameters.
         *
         * @return a builder of the project model.
         */
        public Project build() {
            return new Project(this);
        }
    }
}
