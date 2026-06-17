package com.wm.automation.utils;

import com.wm.automation.base.DriverFactory;
import com.wm.automation.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private static final Logger logger = LogManager.getLogger(WaitUtils.class);

    private WaitUtils() {}

    private static WebDriverWait getWait() {
        return getWait(ConfigReader.getInstance().getExplicitWait());
    }

    private static WebDriverWait getWait(long seconds) {
        return new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(seconds));
    }

    private static FluentWait<WebDriver> getFluentWait(long timeoutSeconds) {
        return new FluentWait<>(DriverFactory.getDriver())
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(FrameworkConstants.DEFAULT_POLLING_INTERVAL_MS))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }

    public static WebElement waitForVisibility(WebElement element) {
        logger.debug("Waiting for element visibility: {}", element);
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By locator) {
        logger.debug("Waiting for element visibility: {}", locator);
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickability(WebElement element) {
        logger.debug("Waiting for element to be clickable: {}", element);
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickability(By locator) {
        return getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForInvisibility(WebElement element) {
        getWait().until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForInvisibility(By locator) {
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static boolean waitForPresence(By locator) {
        try {
            getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static WebElement waitForPresenceElement(By locator) {
        return getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForPageTitle(String title) {
        getWait().until(ExpectedConditions.titleContains(title));
    }

    public static void waitForUrlContains(String urlFragment) {
        getWait().until(ExpectedConditions.urlContains(urlFragment));
    }

    public static boolean isElementVisible(By locator, long timeoutSeconds) {
        try {
            getFluentWait(timeoutSeconds)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void waitForTextPresence(By locator, String text) {
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static <T> T waitForCondition(ExpectedCondition<T> condition, long timeoutSeconds) {
        return getWait(timeoutSeconds).until(condition);
    }
}
