package com.spg.applicationTask;

/**
 * Library constants.
 */
public final class Constants {

    public static final String CREATE_CONSTANT_CLASS_ERROR = "Constant class can not be created.";

    private Constants() {
        throw new AssertionError(CREATE_CONSTANT_CLASS_ERROR);
    }

    /**
     * Mapping constants for the library.
     */
    public final static class Mapping {

        private Mapping() {
            throw new AssertionError(CREATE_CONSTANT_CLASS_ERROR);
        }

        public static final String CONFIGS_TABLE = "configs";
        public static final String CONFIG_ATTRIBUTES_TABLE = "config-attributes";
        public static final String PROPERTIES_TABLE = "properties";
        public static final String PROPERTY_ATTRIBUTES_TABLE = "property-attributes";
    }

    /**
     * Settings constants for the library.
     */
    public final static class Settings {

        public static final String FETCH_SIZE = "fetch-size";
        public static final String DB_DIALECT = "db-dialect";
        public static final String POSTGRE = "postgre";
        public static final String DEFAULT = "default";

        private Settings() {
            throw new AssertionError(CREATE_CONSTANT_CLASS_ERROR);
        }
    }

    /**
     * Endpoints constants for the library.
     */
    public final static class Endpoints {

        private Endpoints() {
            throw new AssertionError(CREATE_CONSTANT_CLASS_ERROR);
        }

        public static final String ACCEPT_CONFIG = "accept-config-endpoint";
        public static final String ACCEPT_CONFIG_VALUE = "accept_config";
        public static final String CONFIG_NAMES = "config-names-endpoint";
        public static final String CONFIG_NAMES_VALUE = "config_names";
        public static final String CONFIG = "config-endpoint";
        public static final String CONFIG_VALUE = "config";
        public static final String JUST = "just-endpoint";
        public static final String JUST_VALUE = "just";
    }

    /**
     * Messages constants for the library.
     */
    public final static class Messages {

        private Messages() {
            throw new AssertionError(CREATE_CONSTANT_CLASS_ERROR);
        }

        public static final String CREATE_FACTORY_CLASS_ERROR = "Factory class can not be created.";
        public static final String CREATE_UTILS_CLASS_ERROR = "Utils class can not be created.";
        public static final String CREATE_HELPER_CLASS_ERROR = "Helper class can not be created.";
        public static final String META_CONFIG_ERROR = "MetaConfig can not be instantiated.";
        public static final String STRING_TO_JSON_ERROR = "String can not be parsed to JSON.";
        public static final String WRONG_ID_VALUE = "Id value must be greater than zero.";
        public static final String WRONG_VERSION_VALUE = "Version value must be greater than zero.";
        public static final String WRONG_UPDATED_VALUE = "Updated value must be greater than zero.";
        public static final String WRONG_PAGE_VALUE = "Page value must be greater or equal to zero.";
        public static final String WRONG_TOTAL_VALUE = "Total value must be greater or equal to zero.";
        public static final String WRONG_SIZE_VALUE = "Size value must be greater or equal to zero.";
        public static final String EMPTY_ASCENDING_VALUE = "Ascending must be set.";
        public static final String WRONG_CONFIG_NAME = "Config name is wrong.";
        public static final String REQUEST_SEND_ERROR = "Request can not be sent.";
        public static final String SERVER_STARTED = "Server started.";
        public static final String SERVER_STOPPED = "Server stopped.";
        public static final String METHOD_NOT_ALLOWED = "Method not allowed.";
        public static final String PATH_PARAM_NOT_PRESENT = "Path param is not presented.";
        public static final String REQUEST_PARAM_NOT_PRESENT = "Request param is not presented.";
        public static final String JSON_TO_CONFIG_ERROR = "JSON can not be parsed to config.";
        public static final String CONFIG_ACCEPTED = "Accepted '%s' config.";
        public static final String CONFIG_ACCEPT_ERROR = "Config accept error.";
        public static final String CREATE_CONFIG_TABLE_ERROR = "'Configs' table can not be created.";
        public static final String SAVE_CONFIGS_ERROR = "Config(s) instances can not be saved.";
        public static final String INSERT_ATTRIBUTES_ERROR_MSG = "Attribute(s) can not be inserted: '%s'.";
        public static final String UPDATE_ATTRIBUTES_ERROR_MSG = "Attribute(s) can not be updated: '%s'.";
        public static final String UPDATE_ATTRIBUTES_ERROR = "Attribute(s) can not be updated.";
        public static final String SAVE_PROPERTIES_ERROR = "Propert(y/ies) instances can not be saved.";
        public static final String RECEIVED_CONFIGS_ERROR = "Config instances can not be received.";
        public static final String RECEIVED_CONFIG_NAMES_ERROR = "Config names can not be received.";
        public static final String RECEIVED_PAGE_RESPONSE_ERROR = "Page response can not be received.";
        public static final String DELETE_CONFIGS_ERROR = "Config(s) can not be deleted.";
        public static final String DB_ERROR = "Database error.";
        public static final String DB_ROLLBACK_ERROR = "Database rollback error.";
        public static final String DB_CONNECTION_ERROR = "Database connection error.";
        public static final String SERVER_CREATE_ERROR = "Failed to create HTTPS server.";
        public static final String CERTIFICATE_LOAD_ERROR = "Failed to load the certificate.";
        public static final String SERVER_WRONG_STATUS_CODE = "Server returned the '%d' status code.";
        public static final String PARAM_ENCODING_ERROR = "Param can not be encoded.";
        public static final String PARAM_DECODING_ERROR = "Param can not be decoded.";
        public static final String PARAM_NOT_PRESENTED= "The '%s' param is not presented.";
    }
}