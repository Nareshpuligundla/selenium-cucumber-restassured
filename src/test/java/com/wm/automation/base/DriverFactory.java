package com.wm.automation.base;

import com.wm.automation.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public final class DriverFactory {

    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void initDriver() {
        if (driverThreadLocal.get() != null) {
            return; // already initialized for this thread
        }

        ConfigReader config = ConfigReader.getInstance();
        String browser = config.getBrowser().trim().toLowerCase();
        boolean headless = config.isHeadless();

        WebDriver driver = switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions opts = new FirefoxOptions();
                if (headless) opts.addArguments("-headless");
                yield new FirefoxDriver(opts);
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions opts = new EdgeOptions();
                if (headless) opts.addArguments("--headless=new", "--no-sandbox",
                        "--disable-dev-shm-usage");
                yield new EdgeDriver(opts);
            }
            default -> {
                // chrome (default)
                WebDriverManager.chromedriver().setup();
                ChromeOptions opts = new ChromeOptions();
                if (headless) opts.addArguments("--headless=new", "--no-sandbox",
                        "--disable-dev-shm-usage", "--window-size=1920,1080");
                opts.addArguments("--start-maximized", "--disable-notifications",
                        "--disable-popup-blocking", "--disable-infobars");
                yield new ChromeDriver(opts);
            }
        };

        driver.manage().timeouts().implicitlyWait(Duration.ZERO); // rely on explicit waits
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(config.getPageLoadTimeout()));
        driver.manage().window().maximize();

        driverThreadLocal.set(driver);
        logger.info("WebDriver [{}] initialised (headless={})", browser, headless);
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                logger.warn("Exception while quitting driver: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove();
                logger.info("WebDriver quit and removed from ThreadLocal");
            }
        }
    }

    public static boolean isDriverInitialised() {
        return driverThreadLocal.get() != null;
    }
}
