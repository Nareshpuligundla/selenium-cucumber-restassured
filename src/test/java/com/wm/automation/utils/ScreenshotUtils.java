package com.wm.automation.utils;

import com.wm.automation.base.DriverFactory;
import com.wm.automation.constants.FrameworkConstants;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtils() {}

    /**
     * Captures a screenshot and attaches it to the Cucumber scenario report.
     * Also saves it to the screenshots directory.
     */
    public static void captureAndAttach(Scenario scenario) {
        if (!DriverFactory.isDriverInitialised()) {
            logger.warn("No WebDriver active – skipping screenshot for scenario: {}", scenario.getName());
            return;
        }
        try {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");
            saveToFile(screenshot, sanitizeFileName(scenario.getName()));
            logger.info("Screenshot captured and attached for scenario: {}", scenario.getName());
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    /**
     * Captures and saves a screenshot to disk, returning the file path.
     */
    public static String capture(String testName) {
        if (!DriverFactory.isDriverInitialised()) {
            logger.warn("No WebDriver active – skipping screenshot for: {}", testName);
            return "";
        }
        try {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            return saveToFile(screenshot, sanitizeFileName(testName));
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
            return "";
        }
    }

    private static String saveToFile(byte[] data, String name) throws IOException {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String fileName = name + "_" + timestamp + ".png";
        Path dir = Paths.get(FrameworkConstants.SCREENSHOTS_DIR);
        Files.createDirectories(dir);
        Path filePath = dir.resolve(fileName);
        Files.write(filePath, data);
        logger.debug("Screenshot saved to: {}", filePath.toAbsolutePath());
        return filePath.toAbsolutePath().toString();
    }

    private static String sanitizeFileName(String name) {
        return name == null ? "screenshot" : name.replaceAll("[^a-zA-Z0-9_-]", "_");
    }
}
