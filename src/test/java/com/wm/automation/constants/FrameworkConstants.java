package com.wm.automation.constants;

public final class FrameworkConstants {

    private FrameworkConstants() {}

    // Config file paths (relative to project root)
    public static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";
    public static final String QA_CONFIG_FILE_PATH = "src/test/resources/config/qa.properties";
    public static final String PROD_CONFIG_FILE_PATH = "src/test/resources/config/prod.properties";

    // Test data paths
    public static final String WEB_TEST_DATA_PATH = "src/test/resources/testdata/web-testdata.json";
    public static final String API_TEST_DATA_PATH = "src/test/resources/testdata/api-testdata.json";

    // Schema paths
    public static final String SCHEMAS_DIR = "src/test/resources/schemas/";

    // Report output paths
    public static final String REPORTS_DIR = "target/cucumber-reports/";
    public static final String EXTENT_REPORTS_DIR = "target/extent-reports/";
    public static final String SCREENSHOTS_DIR = "target/screenshots/";

    // Default timeout values (seconds)
    public static final long DEFAULT_EXPLICIT_WAIT = 20L;
    public static final long DEFAULT_PAGE_LOAD_TIMEOUT = 30L;
    public static final long DEFAULT_POLLING_INTERVAL_MS = 500L;

    // Scenario context keys
    public static final String CONTEXT_API_RESPONSE = "API_RESPONSE";
    public static final String CONTEXT_API_STATUS_CODE = "API_STATUS_CODE";
    public static final String CONTEXT_CURRENT_SCENARIO_NAME = "SCENARIO_NAME";
}
