package com.spg.applicationTask.api.utils;

import com.spg.applicationTask.api.repository.sql.PostgreSQL;

import static com.spg.applicationTask.api.utils.SQLUtils.Mapping.PROJECTS_TABLE;
import static com.spg.applicationTask.api.utils.SQLUtils.Mapping.PROJECT_TASKS_TABLE;
import static com.spg.applicationTask.api.utils.SQLUtils.Mapping.TASKS_TABLE;
import static com.spg.applicationTask.api.utils.SQLUtils.Mapping.USERS_TABLE;

public final class SQLUtils {

    public static final class Select {
        public static String user() {
            return String.format(PostgreSQL.SELECT.USERS, USERS_TABLE,
                    PROJECTS_TABLE, PROJECT_TASKS_TABLE, TASKS_TABLE);
        }

        public static String project() {
            return String.format(PostgreSQL.SELECT.PROJECTS, PROJECTS_TABLE, TASKS_TABLE, USERS_TABLE);
        }
    }

    public static final class Insert {

        public static String user() {
            return String.format(PostgreSQL.INSERT.USERS, USERS_TABLE);
        }

        public static String project() {
            return String.format(PostgreSQL.INSERT.PROJECTS, PROJECTS_TABLE);
        }

        public static String task() {
            return String.format(PostgreSQL.INSERT.TASKS, TASKS_TABLE);
        }
    }

    public static final class Update {

        public static String user() {
            return String.format(PostgreSQL.UPDATE.USERS, USERS_TABLE);
        }

        public static String task() {
            return String.format(PostgreSQL.UPDATE.TASKS, TASKS_TABLE);
        }

        public static String project() {
            return String.format(PostgreSQL.UPDATE.PROJECTS, PROJECTS_TABLE);
        }
    }

    public static final class Create {

        public static String users() {
            return String.format(PostgreSQL.CREATE_TABLE.USERS, USERS_TABLE, PROJECTS_TABLE);
        }

        public static String projects() {
            return String.format(PostgreSQL.CREATE_TABLE.PROJECTS, PROJECTS_TABLE);
        }

        public static String tasks() {
            return String.format(PostgreSQL.CREATE_TABLE.TASKS, TASKS_TABLE, USERS_TABLE);
        }
    }

    public static final class Delete {

        public static String users() {
            return String.format(PostgreSQL.DELETE.BY_ID, USERS_TABLE);
        }

        public static String tasks() {
            return String.format(PostgreSQL.DELETE.BY_ID, TASKS_TABLE);
        }

        public static String projects() {
            return String.format(PostgreSQL.DELETE.BY_ID, PROJECTS_TABLE);
        }
    }


    static final class Mapping {

        public static final String USERS_TABLE = "USERS";
        public static final String PROJECTS_TABLE = "PROJECTS";
        public static final String PROJECT_TASKS_TABLE = "PROJECT_TASKS";
        public static final String TASKS_TABLE = "TASKS";
    }
}
