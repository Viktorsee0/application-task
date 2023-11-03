package com.spg.applicationTask.api.repository.sql;

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

        public static final String USERS = """
                SELECT U.ID, U.FIRSTNAME, U.LASTNAME, U.EMAIL,
                       P.ID,P.NAME,P.DESCRIPTION,
                       T.ID, T.TITLE, T.DESCRIPTION, T.CREATED_DATE, T.DUE_DATE, T.STATUS, T.ASSIGNEE_ID,
                       U2.ID, U2.FIRSTNAME, U2.LASTNAME, U2.EMAIL
                FROM USERS AS U
                         LEFT JOIN PROJECTS AS P ON P.ID = U.PROJECT_ID
                         LEFT JOIN TASKS AS T ON  T.PROJECT_ID =P.ID
                         LEFT JOIN USERS AS U2 ON  U2.ID =T.ASSIGNEE_ID""";

        public static final String PROJECTS = """
                SELECT P.ID, P.NAME, P.DESCRIPTION,
                        T.ID, T.TITLE, T.DESCRIPTION, T.CREATED_DATE, T.DUE_DATE, T.STATUS,
                        U.ID, U.FIRSTNAME, U.LASTNAME, U.EMAIL
                FROM %1$s AS P
                        LEFT JOIN %2$s AS T ON T.PROJECT_ID = P.ID
                        LEFT JOIN %3$s AS U ON U.ID = T.ASSIGNEE_ID""";
    }

    public final static class INSERT {
        public static final String INSERT_CLASS_ERROR = "INSERT class can not be created.";

        private INSERT() {
            throw new AssertionError(INSERT_CLASS_ERROR);
        }

        public static final String USERS = """
                INSERT INTO %s (FIRSTNAME, LASTNAME, EMAIL, PROJECT_ID)
                VALUES (?, ?, ?, ?);""";
        public static final String TASKS = """
                INSERT INTO %s (TITLE, DESCRIPTION, DUE_DATE, STATUS, ASSIGNEE_ID, PROJECT_ID)
                VALUES (?, ?, ?, ?, ?, ?);""";
        public static final String PROJECTS = """
                INSERT INTO %s (NAME, DESCRIPTION)
                VALUES (?, ?);""";

        public static final String PROJECT_TASKS = """
                INSERT INTO %s (PROJECT_ID, TASK_ID)
                VALUES (?, ?);""";
    }

    public final static class UPDATE {
        public static final String UPDATE_CLASS_ERROR = "UPDATE class can not be created.";

        private UPDATE() {
            throw new AssertionError(UPDATE_CLASS_ERROR);
        }

        public static final String USERS = """
                UPDATE %s SET FIRSTNAME = ?, LASTNAME = ?, EMAIL = ?, PROJECT_ID = ?
                WHERE ID = ?;""";

        public static final String TASKS = """
                UPDATE %s SET TITLE = ?, DESCRIPTION = ?, DUE_DATE = ?, STATUS = ?, ASSIGNEE_ID = ?, PROJECT_ID = ?
                WHERE ID = ?;""";

        public static final String PROJECTS = """
                UPDATE %s SET NAME = ?, DESCRIPTION = ?
                WHERE ID = ?;""";
    }

    public final static class DELETE {
        public static final String DELETE_CLASS_ERROR = "DELETE class can not be created.";

        private DELETE() {
            throw new AssertionError(DELETE_CLASS_ERROR);
        }

        public static final String BY_ID = """
                DELETE FROM %1$s WHERE %1$s.ID = ?""";

        public static final String PROJECT_TASK = """ 
                DELETE FROM %1$s WHERE PROJECT_ID = ?""";

    }

    public final static class CREATE_TABLE {

        public static final String CREATE_TABLE_CLASS_ERROR = "CREATE_TABLE class can not be created.";

        private CREATE_TABLE() {
            throw new AssertionError(CREATE_TABLE_CLASS_ERROR);
        }

        public static final String USERS = """
                CREATE TABLE IF NOT EXISTS %1$s
                        (ID SERIAL NOT NULL PRIMARY KEY,
                        FIRSTNAME VARCHAR(255) NOT NULL,
                        LASTNAME VARCHAR(255) NOT NULL,
                        EMAIL VARCHAR(255) UNIQUE,
                        PROJECT_ID INTEGER,
                        FOREIGN KEY (PROJECT_ID) REFERENCES %2$s (ID) ON DELETE SET NULL);""";

        public static final String TASKS = """
                CREATE TABLE IF NOT EXISTS %1$s
                        (ID SERIAL NOT NULL PRIMARY KEY,
                        TITLE VARCHAR(255) NOT NULL,
                        DESCRIPTION TEXT NOT NULL,
                        CREATED_DATE TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        DUE_DATE TIMESTAMPTZ NOT NULL,
                        STATUS VARCHAR NOT NULL,
                        ASSIGNEE_ID INTEGER REFERENCES %2$s(ID) ON DELETE SET NULL);""";
        public static final String PROJECTS = """
                CREATE TABLE IF NOT EXISTS %1$s
                        (ID SERIAL NOT NULL PRIMARY KEY,
                        NAME VARCHAR(255) NOT NULL UNIQUE,
                        DESCRIPTION TEXT NOT NULL);""";
        public static final String PROJECT_TASKS = """
                CREATE TABLE IF NOT EXISTS %1$s " +
                        (PROJECT_ID INTEGER NOT NULL REFERENCES %2$s(ID) ON DELETE CASCADE,
                        TASK_ID INTEGER NOT NULL UNIQUE REFERENCES %3$s(ID) ON DELETE CASCADE,
                        PRIMARY KEY (project_id, task_id));""";
    }
}
