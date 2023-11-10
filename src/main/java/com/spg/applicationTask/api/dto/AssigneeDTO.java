package com.spg.applicationTask.api.dto;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.spg.applicationTask.api.model.User;
import com.spg.applicationTask.engine.extension.ExtJsonable;
import com.spg.applicationTask.engine.extension.Validator;

import java.io.IOException;
import java.io.Writer;

import static com.spg.applicationTask.engine.extension.JsonUtils.getInt;

/**
 * DTO for user
 *
 * @see User
 */
public class AssigneeDTO implements ExtJsonable {

    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public AssigneeDTO(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
    }


    /**
     * Returns an id of the assignee.
     *
     * @return an assignee id.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns an firstName of the assignee.
     *
     * @return an assignee firstName.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns an lastName of the assignee.
     *
     * @return an assignee lastName.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns an email of the assignee.
     *
     * @return an assignee email.
     */
    public String getEmail() {
        return email;
    }

    @Override
    public void toJson(Writer writer) throws IOException {
        final JsonObject json = new JsonObject();
        json.put("id", id);
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("email", email);
        json.toJson(writer);
    }

    public final static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private String email;

        public Builder() {
        }

        /**
         * Constructs an assignee dto based on the json object.
         *
         * @param jsonObject a json object with the assignee dto.
         */
        public Builder(final JsonObject jsonObject) {
            final JsonObject prototype = Validator.of(jsonObject).get();
            this.id = getInt(prototype, "id");
            this.firstName = Validator.of((String) prototype.get("firstName")).get();
            this.lastName = Validator.of((String) prototype.get("lastName")).get();
            this.email = Validator.of((String) prototype.get("email")).get();
        }

        /**
         * Constructs an assignee dto with the id parameter.
         *
         * @param id an assignee dto id.
         * @return a builder of the assignee dto.
         */
        public Builder id(final int id) {
            this.id = id;
            return this;
        }

        /**
         * Constructs an assignee dto with the firstName parameter.
         *
         * @param firstName an assignee dto firstName.
         * @return a builder of the assignee dto.
         */
        public Builder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * Constructs an assignee dto with the lastName parameter.
         *
         * @param lastName an assignee dto lastName.
         * @return a builder of the assignee dto.
         */
        public Builder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * Constructs an assignee dto with the email parameter.
         *
         * @param email an assignee dto email.
         * @return a builder of the assignee dto.
         */
        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * Builds a assignee dto with required parameters.
         *
         * @return a builder of the assignee dto.
         */
        public AssigneeDTO build() {
            return new AssigneeDTO(this);
        }
    }
}
