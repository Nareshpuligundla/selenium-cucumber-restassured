package com.wm.automation.utils;

import com.wm.automation.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static volatile ConfigReader instance;
    private final Properties properties = new Properties();

    private ConfigReader() {
        loadBaseConfig();
        loadEnvConfig();
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    private void loadBaseConfig() {
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH)) {
            properties.load(fis);
            logger.info("Loaded base config from {}", FrameworkConstants.CONFIG_FILE_PATH);
        } catch (IOException e) {
            logger.error("Could not load base config: {}", e.getMessage());
        }
    }

    private void loadEnvConfig() {
        String env = System.getProperty("env", properties.getProperty("environment", "qa"));
        String envPath = "prod".equalsIgnoreCase(env)
                ? FrameworkConstants.PROD_CONFIG_FILE_PATH
                : FrameworkConstants.QA_CONFIG_FILE_PATH;

        try (FileInputStream fis = new FileInputStream(envPath)) {
            properties.load(fis);
            logger.info("Loaded env config from {}", envPath);
        } catch (IOException e) {
            logger.warn("Could not load env config {}: {}", envPath, e.getMessage());
        }
    }

    /** Returns system property override first, then properties file value, then empty string. */
    public String get(String key) {
        return System.getProperty(key, properties.getProperty(key, ""));
    }

    public String getBrowser() {
        return System.getProperty("browser", properties.getProperty("browser", "chrome"));
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(
                System.getProperty("headless", properties.getProperty("headless", "false")));
    }

    public String getBaseUrl() {
        return get("baseUrl");
    }

    public String getApiBaseUrl() {
        return get("apiBaseUrl");
    }

    public long getExplicitWait() {
        try {
            return Long.parseLong(get("explicitWait"));
        } catch (NumberFormatException e) {
            return FrameworkConstants.DEFAULT_EXPLICIT_WAIT;
        }
    }

    public long getPageLoadTimeout() {
        try {
            return Long.parseLong(get("pageLoadTimeout"));
        } catch (NumberFormatException e) {
            return FrameworkConstants.DEFAULT_PAGE_LOAD_TIMEOUT;
        }
    }

    public boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(get("screenshotOnFailure"));
    }

    public boolean isRetryFailedTests() {
        return Boolean.parseBoolean(get("retryFailedTests"));
    }

    public String getEnvironment() {
        return System.getProperty("env", properties.getProperty("environment", "qa"));
    }
}
