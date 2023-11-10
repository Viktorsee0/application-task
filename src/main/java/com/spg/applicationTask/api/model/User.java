package com.spg.applicationTask.api.model;

import java.util.Objects;
import java.util.Optional;

/**
 * The user model that contains project and description.
 */
public final class User {

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

    /**
     * Returns an id of the user.
     *
     * @return an id.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a firstName of the user.
     *
     * @return an firstName.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns a lastName of the user.
     *
     * @return a lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns an email of the user.
     *
     * @return an email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns an optional of project of the user.
     *
     * @return an optional of project.
     */
    public Optional<Project> getProject() {
        return Optional.ofNullable(project);
    }

    /**
     * Sets an id of the task.
     *
     * @param id a task id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets a project of the task.
     *
     * @param project a task project.
     */
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
        return id == user.id &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(project, user.project);
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

        /**
         * Constructs a user model with the id parameter.
         *
         * @param id a user id.
         * @return a builder of the user model.
         */
        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        /**
         * Constructs a user model with the firstName parameter.
         *
         * @param firstName a user firstName.
         * @return a builder of the user model.
         */
        public Builder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * Constructs a user model with the lastName parameter.
         *
         * @param lastName a user lastName.
         * @return a builder of the user model.
         */
        public Builder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * Constructs a user model with the email parameter.
         *
         * @param email a user email.
         * @return a builder of the user model.
         */
        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * Constructs a user model with the project parameter.
         *
         * @param project a user project.
         * @return a builder of the user model.
         */
        public Builder project(final Project project) {
            this.project = project;
            return this;
        }

        /**
         * Builds a user model with required parameters.
         *
         * @return a builder of the user model.
         */
        public User build() {
            return new User(this);
        }
    }
}
