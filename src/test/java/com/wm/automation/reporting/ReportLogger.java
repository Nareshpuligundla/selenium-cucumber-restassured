package com.wm.automation.reporting;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.wm.automation.base.DriverFactory;

import java.util.Base64;

public class ReportLogger {

    private static final Logger logger = LogManager.getLogger(ReportLogger.class);

    private ReportLogger() {}

    public static void info(String message) {
        logger.info(message);
        ExtentTest test = ReportManager.getTest();
        if (test != null) test.log(Status.INFO, message);
    }

    public static void pass(String message) {
        logger.info("[PASS] {}", message);
        ExtentTest test = ReportManager.getTest();
        if (test != null) test.log(Status.PASS, message);
    }

    public static void fail(String message) {
        logger.error("[FAIL] {}", message);
        ExtentTest test = ReportManager.getTest();
        if (test != null) test.log(Status.FAIL, message);
    }

    public static void warn(String message) {
        logger.warn("[WARN] {}", message);
        ExtentTest test = ReportManager.getTest();
        if (test != null) test.log(Status.WARNING, message);
    }

    public static void skip(String message) {
        logger.info("[SKIP] {}", message);
        ExtentTest test = ReportManager.getTest();
        if (test != null) test.log(Status.SKIP, message);
    }

    public static void attachScreenshot(String title) {
        if (!DriverFactory.isDriverInitialised()) return;
        try {
            String base64 = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BASE64);
            ExtentTest test = ReportManager.getTest();
            if (test != null) {
                test.addScreenCaptureFromBase64String(base64, title);
            }
        } catch (Exception e) {
            logger.warn("Could not attach screenshot to report: {}", e.getMessage());
        }
    }
}
