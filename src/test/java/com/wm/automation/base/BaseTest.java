package com.wm.automation.base;

import com.wm.automation.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Optional base class for non-Cucumber utility code.
 * The primary execution layer is Cucumber – TestNG is the runner only.
 * This class provides shared setup helpers available to any future
 * plain TestNG classes that extend it.
 */
public abstract class BaseTest {

    protected final Logger logger = LogManager.getLogger(getClass());
    protected final ConfigReader config = ConfigReader.getInstance();

    protected void initBrowser() {
        DriverFactory.initDriver();
    }

    protected void quitBrowser() {
        DriverFactory.quitDriver();
    }

    protected String getBaseUrl() {
        return config.getBaseUrl();
    }
}
