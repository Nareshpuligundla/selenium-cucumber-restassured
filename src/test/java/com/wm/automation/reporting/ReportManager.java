package com.wm.automation.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.wm.automation.constants.FrameworkConstants;
import com.wm.automation.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReportManager {

    private static final Logger logger = LogManager.getLogger(ReportManager.class);
    private static volatile ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    private ReportManager() {}

    public static synchronized void initReports() {
        if (extentReports != null) return;

        try {
            Files.createDirectories(Paths.get(FrameworkConstants.EXTENT_REPORTS_DIR));
        } catch (IOException e) {
            logger.warn("Could not create extent reports directory: {}", e.getMessage());
        }

        String reportPath = FrameworkConstants.EXTENT_REPORTS_DIR + "ExtentReport.html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("WM Automation Report");
        sparkReporter.config().setReportName("WM Test Execution Report");
        sparkReporter.config().setEncoding("UTF-8");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Environment", ConfigReader.getInstance().getEnvironment());
        extentReports.setSystemInfo("Browser", ConfigReader.getInstance().getBrowser());
        extentReports.setSystemInfo("Base URL", ConfigReader.getInstance().getBaseUrl());
        extentReports.setSystemInfo("API Base URL", ConfigReader.getInstance().getApiBaseUrl());

        logger.info("ExtentReports initialised at: {}", reportPath);
    }

    public static ExtentTest createTest(String testName) {
        ExtentTest test = extentReports.createTest(testName);
        testThreadLocal.set(test);
        return test;
    }

    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = extentReports.createTest(testName, description);
        testThreadLocal.set(test);
        return test;
    }

    public static ExtentTest getTest() {
        return testThreadLocal.get();
    }

    public static synchronized void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
            logger.info("ExtentReports flushed to disk");
        }
    }

    public static void removeTest() {
        testThreadLocal.remove();
    }
}
