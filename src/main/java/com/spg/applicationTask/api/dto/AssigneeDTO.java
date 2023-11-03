package com.spg.applicationTask.api.dto;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.spg.applicationTask.engine.extension.ExtJsonable;
import com.spg.applicationTask.engine.extension.Validator;

import java.io.IOException;
import java.io.Writer;

import static com.spg.applicationTask.engine.extension.JsonUtils.getInt;

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

    public int getId() {
        return id;
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

        public Builder(final JsonObject jsonObject) {
            final JsonObject prototype = Validator.of(jsonObject).get();
            this.id = getInt(prototype, "id");
            this.firstName = Validator.of((String) prototype.get("firstName")).get();
            this.lastName = Validator.of((String) prototype.get("lastName")).get();
            this.email = Validator.of((String) prototype.get("email")).get();
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

        public AssigneeDTO build() {
            return new AssigneeDTO(this);
        }
    }
}
