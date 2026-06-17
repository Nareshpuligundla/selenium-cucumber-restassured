package com.wm.automation.utils;

import com.wm.automation.base.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElementUtils {

    private static final Logger logger = LogManager.getLogger(ElementUtils.class);

    private ElementUtils() {}

    public static void click(WebElement element) {
        WaitUtils.waitForClickability(element).click();
        logger.debug("Clicked element: {}", element);
    }

    public static void click(By locator) {
        WaitUtils.waitForClickability(locator).click();
        logger.debug("Clicked element: {}", locator);
    }

    public static void type(WebElement element, String text) {
        WebElement el = WaitUtils.waitForVisibility(element);
        el.clear();
        el.sendKeys(text);
        logger.debug("Typed '{}' into element", text);
    }

    public static void type(By locator, String text) {
        WebElement el = WaitUtils.waitForVisibility(locator);
        el.clear();
        el.sendKeys(text);
        logger.debug("Typed '{}' into locator: {}", text, locator);
    }

    public static void clearAndType(WebElement element, String text) {
        WebElement el = WaitUtils.waitForVisibility(element);
        el.clear();
        JavaScriptUtils.clearInputField(el);
        el.sendKeys(text);
    }

    public static String getText(WebElement element) {
        return WaitUtils.waitForVisibility(element).getText().trim();
    }

    public static String getText(By locator) {
        return WaitUtils.waitForVisibility(locator).getText().trim();
    }

    public static String getAttribute(WebElement element, String attribute) {
        WaitUtils.waitForVisibility(element);
        return element.getAttribute(attribute);
    }

    public static boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDisplayed(By locator) {
        return WaitUtils.isElementVisible(locator, 5);
    }

    public static boolean isEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public static List<WebElement> findAll(By locator) {
        WebDriver driver = DriverFactory.getDriver();
        return driver.findElements(locator);
    }

    public static int countElements(By locator) {
        return findAll(locator).size();
    }

    public static void selectByVisibleText(WebElement selectElement, String text) {
        WaitUtils.waitForVisibility(selectElement);
        new org.openqa.selenium.support.ui.Select(selectElement).selectByVisibleText(text);
    }

    public static void selectByValue(WebElement selectElement, String value) {
        WaitUtils.waitForVisibility(selectElement);
        new org.openqa.selenium.support.ui.Select(selectElement).selectByValue(value);
    }

    public static String getSelectedOption(WebElement selectElement) {
        WaitUtils.waitForVisibility(selectElement);
        return new org.openqa.selenium.support.ui.Select(selectElement)
                .getFirstSelectedOption().getText();
    }
}
