package com.spg.applicationTask.api;

public final class Constants {

    private Constants() {
    }


    public final static class Messages {

        private Messages() {
        }

        public static final String WRONG_ID_VALUE = "Id value must be greater than zero.";
        public static final String RECEIVED_USER_ERROR = "User instances can not be received.";
        public static final String EXISTING_USER_ERROR = "User(s) does not exist.";
        public static final String EXISTING_PROJECT_ERROR = "Project(s) does not exist.";
        public static final String EXISTING_TASK_ERROR = "Task(s) does not exist.";
        public static final String RECEIVED_PROJECT_ERROR = "Project instances can not be received.";
        public static final String DB_ROLLBACK_ERROR = "Database rollback error.";
        public static final String DB_ERROR = "Database error.";
        public static final String DELETE_USER_ERROR = "User can not be deleted.";
        public static final String DELETE_TASK_ERROR = "Task can not be deleted.";
        public static final String DELETE_PROJECT_ERROR = "Project can not be deleted.";
        public static final String DB_CONNECTION_ERROR = "Database connection error.";
        public static final String SAVE_USERS_ERROR = "User(s) instances can not be saved.";
        public static final String SAVE_PROJECTS_ERROR = "Project(s) instances can not be saved.";
        public static final String SAVE_TASKS_ERROR = "Task(s) instances can not be saved.";
        public static final String UPDATE_USERS_ERROR = "User(s) instances can not be updated.";
        public static final String UPDATE_TASKS_ERROR = "Task(s) instances can not be updated.";
    }
}
