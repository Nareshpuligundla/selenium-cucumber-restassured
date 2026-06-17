package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ConfigReader;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents the WM.com Sign-In / Login page.
 * Covers: email + password login, "Forgot password" link, Create Account link,
 * form-level validation messages, and the post-login redirect.
 */
public class LoginPage extends BasePage {

    private static final String LOGIN_PATH = "/login";

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(css = "input[type='email'], input[name='email'], input[id*='email' i]")
    private WebElement emailInput;

    @FindBy(css = "input[type='password'], input[name='password'], input[id*='password' i]")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit'], button.login-btn, button[data-testid='sign-in-btn']")
    private WebElement signInButton;

    @FindBy(css = "a[href*='forgot'], a[data-testid='forgot-password'], .forgot-password-link")
    private WebElement forgotPasswordLink;

    @FindBy(css = "a[href*='register'], a[href*='create-account'], .create-account-link")
    private WebElement createAccountLink;

    @FindBy(css = ".error-message, .alert-danger, [data-testid='login-error'], " +
                  ".form-error, [role='alert']")
    private WebElement errorMessage;

    @FindBy(css = ".success-message, [data-testid='login-success']")
    private WebElement successMessage;

    @FindBy(css = "label[for*='remember'], input[type='checkbox'][name*='remember']")
    private WebElement rememberMeCheckbox;

    private static final By FORM_READY_LOC =
            By.cssSelector("input[type='email'], input[name='email']");

    private static final By ERROR_LOC =
            By.cssSelector(".error-message, .alert-danger, [data-testid='login-error'], [role='alert']");

    // ─── Actions ──────────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(FORM_READY_LOC, 10);
    }

    public void open() {
        navigateTo(ConfigReader.getInstance().getBaseUrl() + LOGIN_PATH);
        logger.info("Opened Login page");
    }

    public void enterEmail(String email) {
        type(emailInput, email);
        logger.info("Entered email: {}", email);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
        logger.info("Entered password: [hidden]");
    }

    public void clickSignIn() {
        click(signInButton);
        logger.info("Clicked Sign In button");
    }

    public void loginWith(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignIn();
        logger.info("Login submitted for: {}", email);
    }

    public void clickForgotPassword() {
        click(forgotPasswordLink);
        logger.info("Clicked Forgot Password link");
    }

    public void clickCreateAccount() {
        click(createAccountLink);
        logger.info("Clicked Create Account link");
    }

    public void checkRememberMe() {
        if (!rememberMeCheckbox.isSelected()) {
            click(rememberMeCheckbox);
            logger.info("Checked Remember Me");
        }
    }

    public boolean isErrorMessageDisplayed() {
        return WaitUtils.isElementVisible(ERROR_LOC, 5);
    }

    public String getErrorMessageText() {
        try {
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isSignInButtonEnabled() {
        return signInButton.isEnabled();
    }

    public boolean isForgotPasswordLinkVisible() {
        return isDisplayed(forgotPasswordLink);
    }

    public boolean isCreateAccountLinkVisible() {
        return isDisplayed(createAccountLink);
    }

    public String getEmailFieldValue() {
        return ElementUtils.getAttribute(emailInput, "value");
    }

    public boolean isEmailFieldVisible() {
        return isDisplayed(emailInput);
    }

    public boolean isPasswordFieldVisible() {
        return isDisplayed(passwordInput);
    }
}
