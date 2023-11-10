package com.spg.applicationTask.api.dto;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.spg.applicationTask.api.model.User;
import com.spg.applicationTask.engine.extension.ExtJsonable;
import com.spg.applicationTask.engine.extension.Validator;

import java.io.IOException;
import java.io.Writer;

import static com.spg.applicationTask.api.Constants.Messages.WRONG_ID_VALUE;
import static com.spg.applicationTask.engine.extension.JsonUtils.getInt;

/**
 * DTO for user.
 *
 * @see User
 */
public class UserDTO implements ExtJsonable {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private ProjectDTO project;

    public UserDTO(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.project = builder.project;
    }

    /**
     * Returns an id of the user.
     *
     * @return a user id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets user id.
     *
     * @param id an id of the user.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns a firstname of user.
     *
     * @return a user first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns a lastname of user.
     *
     * @returna a user lastname.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns an email of user.
     *
     * @return a user email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns a project dto of user.
     *
     * @return a user project dto.
     */
    public ProjectDTO getProject() {
        return project;
    }

    @Override
    public void toJson(Writer writer) throws IOException {
        final JsonObject json = new JsonObject();
        json.put("id", id);
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("email", email);
        json.put("project", project);
        json.toJson(writer);
    }

    public final static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private String email;
        private ProjectDTO project;

        public Builder() {
        }

        /**
         * Constructs a user dto based on the json object.
         *
         * @param jsonObject a json object with the user dto.
         */
        public Builder(final JsonObject jsonObject) {
            final JsonObject prototype = Validator.of(jsonObject).get();
            this.id = getInt(prototype, "id");
            this.firstName = Validator.of((String) prototype.get("firstName")).get();
            this.lastName = Validator.of((String) prototype.get("lastName")).get();
            this.email = Validator.of((String) prototype.get("email")).get();
            if (jsonObject.get("project") != null) {
                this.project = new ProjectDTO.Builder((JsonObject) jsonObject.get("project")).build();
            }
        }

        /**
         * Constructs a user dto with the id parameter.
         *
         * @param id a user dto id.
         * @return a builder of the user dto.
         */
        public Builder id(final int id) {
            if (id > 0) {
                this.id = id;
            } else {
                throw new IllegalArgumentException(WRONG_ID_VALUE);
            }
            return this;
        }

        /**
         * Constructs a user dto with the firstName parameter.
         *
         * @param firstName a user dto firstName.
         * @return a builder of the user dto.
         */
        public Builder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * Constructs a user dto with the lastName parameter.
         *
         * @param lastName a user dto lastName.
         * @return a builder of the user dto.
         */
        public Builder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * Constructs a user dto with the email parameter.
         *
         * @param email a user dto email.
         * @return a builder of the user dto.
         */
        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * Constructs a user dto with the project parameter.
         *
         * @param project a user dto project.
         * @return a builder of the user dto.
         */
        public Builder project(final ProjectDTO project) {
            this.project = project;
            return this;
        }

        /**
         * Builds a user dto with required parameters.
         *
         * @return a builder of the user dto.
         */
        public UserDTO build() {
            return new UserDTO(this);
        }
    }
}
