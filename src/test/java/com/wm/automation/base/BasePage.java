package com.wm.automation.base;

import com.wm.automation.utils.ConfigReader;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.JavaScriptUtils;
import com.wm.automation.utils.WaitUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    protected final Logger logger = LogManager.getLogger(getClass());
    protected final WebDriver driver;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(driver, this);
    }

    // ─── Navigation ───────────────────────────────────────────────────────────

    public void navigateTo(String url) {
        driver.get(url);
        logger.info("Navigated to: {}", url);
        JavaScriptUtils.waitForPageLoad();
    }

    public void navigateToBaseUrl() {
        navigateTo(ConfigReader.getInstance().getBaseUrl());
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // ─── Element interaction wrappers ─────────────────────────────────────────

    protected void click(WebElement element) {
        ElementUtils.click(element);
    }

    protected void click(By locator) {
        ElementUtils.click(locator);
    }

    protected void type(WebElement element, String text) {
        ElementUtils.type(element, text);
    }

    protected void type(By locator, String text) {
        ElementUtils.type(locator, text);
    }

    protected String getText(WebElement element) {
        return ElementUtils.getText(element);
    }

    protected String getText(By locator) {
        return ElementUtils.getText(locator);
    }

    protected boolean isDisplayed(WebElement element) {
        return ElementUtils.isDisplayed(element);
    }

    protected boolean isDisplayed(By locator) {
        return ElementUtils.isDisplayed(locator);
    }

    protected void waitForVisibility(WebElement element) {
        WaitUtils.waitForVisibility(element);
    }

    protected void waitForClickability(WebElement element) {
        WaitUtils.waitForClickability(element);
    }

    protected void scrollToElement(WebElement element) {
        JavaScriptUtils.scrollToElement(element);
    }

    protected void jsClick(WebElement element) {
        JavaScriptUtils.clickElement(element);
    }

    // ─── Page readiness ───────────────────────────────────────────────────────

    public abstract boolean isPageLoaded();
}
