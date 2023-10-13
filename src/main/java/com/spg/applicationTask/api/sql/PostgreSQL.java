package com.spg.applicationTask.api.sql;

public final class PostgreSQL {

    public static final String CREATE_POSTGRESQL_CLASS_ERROR = "PostgreSQL class can not be created.";

    private PostgreSQL() {
        throw new AssertionError(CREATE_POSTGRESQL_CLASS_ERROR);
    }

    public final static class SELECT {

        public static final String SELECT_CLASS_ERROR = "SELECT class can not be created.";

        private SELECT() {
            throw new AssertionError(SELECT_CLASS_ERROR);
        }
    }

    public final static class INSERT {
        public static final String INSERT_CLASS_ERROR = "INSERT class can not be created.";

        private INSERT() {
            throw new AssertionError(INSERT_CLASS_ERROR);
        }

        public static final String USERS =
                "INSERT INTO %s (FIRSTNAME, LASTNAME, EMAIL) " +
                        "VALUES (?, ?, ?);";
        public static final String TASKS =
                "INSERT INTO %s (TITLE, DESCRIPTION, CREATED_DATE, DUE_DATE, STATUS, ASSIGNEE_ID) " +
                        "VALUES (?, ?, ?, ?, ?, ?);";
        public static final String PROJECTS =
                "INSERT INTO %s (NAME, DESCRIPTION, CREATOR_ID) " +
                        "VALUES (?, ?, ?);";
        public static final String USERS_PROJECTS =
                "INSERT INTO %s (USER_ID, PROJECT_ID) " +
                        "VALUES (?, ?);";
    }

    public final static class UPDATE {
        public static final String UPDATE_CLASS_ERROR = "UPDATE class can not be created.";

        private UPDATE() {
            throw new AssertionError(UPDATE_CLASS_ERROR);
        }

    }

    public final static class DELETE {
        public static final String DELETE_CLASS_ERROR = "DELETE class can not be created.";

        private DELETE() {
            throw new AssertionError(DELETE_CLASS_ERROR);
        }

    }

    public final static class CREATE_TABLE {

        public static final String CREATE_TABLE_CLASS_ERROR = "CREATE_TABLE class can not be created.";

        private CREATE_TABLE() {
            throw new AssertionError(CREATE_TABLE_CLASS_ERROR);
        }

        public static final String USERS =
                "CREATE TABLE IF NOT EXISTS %s " +
                        "(ID SERIAL NOT NULL PRIMARY KEY, " +
                        "FIRSTNAME VARCHAR(255) NOT NULL, " +
                        "LASTNAME VARCHAR(255) NOT NULL, " +
                        "EMAIL VARCHAR(255) UNIQUE)";

        //TODO make up smth with status
        public static final String TASKS =
                "CREATE TABLE IF NOT EXISTS %2$s " +
                        "(ID SERIAL NOT NULL PRIMARY KEY, " +
                        "TITLE VARCHAR(255) NOT NULL, " +
                        "DESCRIPTION TEXT NOT NULL, " +
                        "CREATED_DATE TIMESTAMPTZ NOT NULL, " +
                        "DUE_DATE TIMESTAMPTZ NOT NULL, " +
                        "STATUS VARCHAR NOT NULL, " +
                        "ASSIGNEE_ID INTEGER REFERENCES %1$s(ID) ON DELETE CASCADE)";
        public static final String PROJECTS =
                "CREATE TABLE IF NOT EXISTS %2$s " +
                        "(ID SERIAL NOT NULL PRIMARY KEY, " +
                        "NAME VARCHAR(255) NOT NULL, " +
                        "DESCRIPTION TEXT NOT NULL, " +
                        "CREATOR_ID INTEGER REFERENCES %1$s(ID)";
//        public static final String PROJECT_TASKS =
//                "CREATE TABLE IF NOT EXISTS %3$s " +
//                        "(PROJECT_ID INTEGER NOT NULL REFERENCES %1$s(ID), " +
//                        "TASK_ID INTEGER NOT NULL REFERENCES %2$s(ID), " +
//                        "PRIMARY KEY (project_id, task_id))";
        public static final String USERS_PROJECTS =
                "CREATE TABLE IF NOT EXISTS %3$s " +
                        "(USER_ID INTEGER NOT NULL REFERENCES %1$s(ID), " +
                        "PROJECT_ID INTEGER NOT NULL REFERENCES %2$s(ID), " +
                        "PRIMARY KEY (project_id, task_id))";
    }
}
