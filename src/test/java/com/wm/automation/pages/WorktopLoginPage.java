package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ConfigReader;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object for the Worktop platform login page.
 * Covers: client selector, identifier, password, Sign In, generic / wrong-client /
 * lockout messages, Switch/Change client action and Forgot password link.
 */
public class WorktopLoginPage extends BasePage {

    private static final String LOGIN_PATH = "/login";

    // ─── Locators ─────────────────────────────────────────────────────────────

    // TODO: replace - confirm client selector locator on the Worktop login page
    @FindBy(css = "select[name='client'], select[id*='client' i], [data-testid='client-selector']")
    private WebElement clientSelector;

    // TODO: replace - confirm identifier field locator
    @FindBy(css = "input[name='identifier'], input[id*='identifier' i], input[type='email'], input[name='username']")
    private WebElement identifierInput;

    // TODO: replace - confirm password field locator
    @FindBy(css = "input[type='password'], input[name='password']")
    private WebElement passwordInput;

    // TODO: replace - confirm Sign In button locator
    @FindBy(css = "button[type='submit'], [data-testid='sign-in-btn']")
    private WebElement signInButton;

    // TODO: replace - confirm error / message container locator
    @FindBy(css = ".login-error, .error-message, [data-testid='login-error'], [role='alert']")
    private WebElement errorMessage;

    // TODO: replace - confirm Switch to Client / Change client action locator
    @FindBy(css = "[data-testid='switch-client'], button.switch-client, a.switch-client, [data-testid='change-client']")
    private WebElement switchClientAction;

    // TODO: replace - confirm Forgot password link locator
    @FindBy(css = "a[href*='forgot'], [data-testid='forgot-password']")
    private WebElement forgotPasswordLink;

    private static final By FORM_READY_LOC =
            By.cssSelector("input[name='identifier'], input[id*='identifier' i], input[type='email'], input[name='username']");

    private static final By ERROR_LOC =
            By.cssSelector(".login-error, .error-message, [data-testid='login-error'], [role='alert']");

    private static final By SWITCH_CLIENT_LOC =
            By.cssSelector("[data-testid='switch-client'], button.switch-client, a.switch-client, [data-testid='change-client']");

    // ─── Actions ──────────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(FORM_READY_LOC, 10);
    }

    public void open() {
        String base = System.getProperty("worktop.base.url", ConfigReader.getInstance().getBaseUrl());
        navigateTo(base + LOGIN_PATH);
        logger.info("Opened Worktop login page");
    }

    public void selectClient(String clientName) {
        ElementUtils.selectByVisibleText(clientSelector, clientName);
        logger.info("Selected client: {}", clientName);
    }

    public String getSelectedClient() {
        try {
            String value = ElementUtils.getAttribute(clientSelector, "value");
            String text = clientSelector.getText();
            return (text != null && !text.isEmpty()) ? text : value;
        } catch (Exception e) {
            logger.warn("Could not read selected client: {}", e.getMessage());
            return "";
        }
    }

    public void enterIdentifier(String identifier) {
        type(identifierInput, identifier);
        logger.info("Entered identifier: {}", identifier);
    }

    public String getIdentifierValue() {
        return ElementUtils.getAttribute(identifierInput, "value");
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
        logger.info("Entered password: [hidden]");
    }

    public String getPasswordValue() {
        return ElementUtils.getAttribute(passwordInput, "value");
    }

    public void clickSignIn() {
        click(signInButton);
        logger.info("Clicked Sign In");
    }

    public boolean isErrorDisplayed() {
        return WaitUtils.isElementVisible(ERROR_LOC, 5);
    }

    public String getErrorText() {
        try {
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isSwitchClientActionDisplayed(String expectedLabel) {
        if (!WaitUtils.isElementVisible(SWITCH_CLIENT_LOC, 5)) {
            return false;
        }
        String label = getText(switchClientAction);
        return expectedLabel == null || expectedLabel.isEmpty()
                || label.toLowerCase().contains(expectedLabel.toLowerCase());
    }

    public void clickSwitchClient() {
        click(switchClientAction);
        logger.info("Clicked switch / change client action");
    }

    public void clickForgotPassword() {
        click(forgotPasswordLink);
        logger.info("Clicked Forgot password link");
    }
}
