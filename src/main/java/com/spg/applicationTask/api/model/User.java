package com.spg.applicationTask.api.model;

import java.util.Objects;
import java.util.Optional;

public final class User{

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Project project;

    public User(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.project = builder.project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Optional<Project> getProject() {
        return Optional.ofNullable(project);
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", project=" + project +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(project, user.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, project);
    }

    public final static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private Project project;

        public Builder() {
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", project=" + project +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Builder builder = (Builder) o;
            return id == builder.id && Objects.equals(firstName, builder.firstName) && Objects.equals(lastName, builder.lastName) && Objects.equals(email, builder.email) && Objects.equals(project, builder.project);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, firstName, lastName, email, project);
        }

        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        public Builder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder project(final Project project) {
            this.project = project;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
