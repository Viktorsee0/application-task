package com.spg.applicationTask.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void addTask(Task task) {
        this.tasks.add(task);
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
        return id == project.id && Objects.equals(name, project.name) && Objects.equals(description, project.description) && Objects.equals(tasks, project.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, tasks);
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public final static class Builder {
        private int id;
        private String name;
        private String description;
        private List<Task> tasks = new ArrayList<>();

        public Builder() {
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

        public Builder tasks(final List<Task> tasks) {
            this.tasks.clear();
            this.tasks.addAll(tasks);
            return this;
        }

        public Builder task(final Task task) {
            this.tasks.add(task);
            return this;
        }

        public Project build() {
            return new Project(this);
        }
    }
}
