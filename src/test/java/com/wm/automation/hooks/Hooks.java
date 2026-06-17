package com.wm.automation.hooks;

import com.wm.automation.base.DriverFactory;
import com.wm.automation.reporting.ReportLogger;
import com.wm.automation.reporting.ReportManager;
import com.wm.automation.utils.ConfigReader;
import com.wm.automation.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    // ─── Suite-level lifecycle ────────────────────────────────────────────────

    @BeforeAll
    public static void beforeSuite() {
        ReportManager.initReports();
        logger.info("============================================================");
        logger.info("  WM Automation Suite started  [env={}]",
                ConfigReader.getInstance().getEnvironment());
        logger.info("============================================================");
    }

    @AfterAll
    public static void afterSuite() {
        ReportManager.flushReports();
        logger.info("============================================================");
        logger.info("  WM Automation Suite completed");
        logger.info("============================================================");
    }

    // ─── Scenario-level lifecycle ─────────────────────────────────────────────

    /**
     * Runs before every scenario.
     * Browser is initialised only for @web and @e2e tagged scenarios.
     */
    @Before
    public void beforeScenario(Scenario scenario) {
        logger.info("------------------------------------------------------------");
        logger.info("  Scenario: {} [{}]", scenario.getName(),
                String.join(", ", scenario.getSourceTagNames()));
        logger.info("------------------------------------------------------------");

        ReportManager.createTest(scenario.getName(),
                "Tags: " + String.join(", ", scenario.getSourceTagNames()));
        ReportLogger.info("Scenario started: " + scenario.getName());

        boolean isWebScenario = scenario.getSourceTagNames().contains("@web")
                || scenario.getSourceTagNames().contains("@e2e");

        if (isWebScenario) {
            DriverFactory.initDriver();
            logger.info("WebDriver initialised for scenario: {}", scenario.getName());
        }
    }

    /**
     * Runs after every scenario.
     * Captures screenshot on failure, then quits the browser.
     */
    @After
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                logger.error("Scenario FAILED: {}", scenario.getName());
                ReportLogger.fail("Scenario failed: " + scenario.getName());

                if (ConfigReader.getInstance().isScreenshotOnFailure()
                        && DriverFactory.isDriverInitialised()) {
                    ScreenshotUtils.captureAndAttach(scenario);
                    ReportLogger.attachScreenshot("Failure Screenshot");
                }
            } else {
                logger.info("Scenario PASSED: {}", scenario.getName());
                ReportLogger.pass("Scenario passed: " + scenario.getName());
            }
        } finally {
            DriverFactory.quitDriver();
            ReportManager.removeTest();
            logger.info("------------------------------------------------------------");
        }
    }
}
