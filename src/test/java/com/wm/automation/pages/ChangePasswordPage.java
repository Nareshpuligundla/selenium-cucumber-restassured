package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object for the Worktop user menu, Change Password form and the
 * workspace home indicator. Also usable for the reset-password form.
 */
public class ChangePasswordPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    // TODO: replace - confirm user menu trigger selector
    @FindBy(css = "[data-testid='user-menu'], button.user-menu, button[aria-label*='user menu' i]")
    private WebElement userMenu;

    // TODO: replace - confirm 'Change Password' menu item selector
    @FindBy(css = "[data-testid='change-password'], a[href*='change-password'], button.change-password")
    private WebElement changePasswordMenuItem;

    // TODO: replace - confirm 'Log out' menu item selector
    @FindBy(css = "[data-testid='logout'], a[href*='logout'], button.logout")
    private WebElement logoutItem;

    // TODO: replace - confirm Current password field selector
    @FindBy(css = "input[name='currentPassword'], input[id*='current' i]")
    private WebElement currentPasswordInput;

    // TODO: replace - confirm New password field selector
    @FindBy(css = "input[name='newPassword'], input[id='newPassword'], input[id*='new-password' i]")
    private WebElement newPasswordInput;

    // TODO: replace - confirm Confirm new password field selector
    @FindBy(css = "input[name='confirmPassword'], input[name='confirmNewPassword'], input[id*='confirm' i]")
    private WebElement confirmPasswordInput;

    // TODO: replace - confirm Save button selector
    @FindBy(css = "button[data-testid='save-password'], button[type='submit']")
    private WebElement saveButton;

    // TODO: replace - confirm per-field error selectors
    @FindBy(css = "[data-testid='new-password-error'], #newPassword-error, input[name='newPassword'] ~ .field-error")
    private WebElement newPasswordError;

    @FindBy(css = "[data-testid='confirm-password-error'], #confirmPassword-error, input[name='confirmPassword'] ~ .field-error")
    private WebElement confirmPasswordError;

    // TODO: replace - confirm success confirmation selector
    @FindBy(css = "[data-testid='password-success'], .success-banner, .alert-success, [role='status']")
    private WebElement successMessage;

    private static final By FORM_LOC =
            By.cssSelector("input[name='currentPassword'], input[name='newPassword']");
    private static final By NEW_ERROR_LOC =
            By.cssSelector("[data-testid='new-password-error'], #newPassword-error, input[name='newPassword'] ~ .field-error");
    private static final By CONFIRM_ERROR_LOC =
            By.cssSelector("[data-testid='confirm-password-error'], #confirmPassword-error, input[name='confirmPassword'] ~ .field-error");
    private static final By SUCCESS_LOC =
            By.cssSelector("[data-testid='password-success'], .success-banner, .alert-success, [role='status']");
    // TODO: replace - confirm workspace home indicator selector
    private static final By WORKSPACE_HOME_LOC =
            By.cssSelector("[data-testid='workspace-home'], .workspace-home, [data-testid='user-menu']");

    // ─── Actions ──────────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return isFormDisplayed();
    }

    public void openFromUserMenu() {
        click(userMenu);
        click(changePasswordMenuItem);
        logger.info("Opened Change Password form from user menu");
    }

    public void enterCurrentPassword(String password) {
        type(currentPasswordInput, password);
        logger.info("Entered current password: [hidden]");
    }

    public void enterNewPassword(String password) {
        type(newPasswordInput, password);
        logger.info("Entered new password: [hidden]");
    }

    public void enterConfirmPassword(String password) {
        type(confirmPasswordInput, password);
        logger.info("Entered confirm password: [hidden]");
    }

    public void clickSave() {
        scrollToElement(saveButton);
        click(saveButton);
        logger.info("Clicked Save");
    }

    public String getNewPasswordError() {
        return WaitUtils.isElementVisible(NEW_ERROR_LOC, 5) ? getText(newPasswordError) : "";
    }

    public String getConfirmPasswordError() {
        return WaitUtils.isElementVisible(CONFIRM_ERROR_LOC, 5) ? getText(confirmPasswordError) : "";
    }

    public String getSuccessMessage() {
        return WaitUtils.isElementVisible(SUCCESS_LOC, 10) ? getText(successMessage) : "";
    }

    public String getCurrentPasswordValue() {
        return ElementUtils.getAttribute(currentPasswordInput, "value");
    }

    public boolean isFormDisplayed() {
        return WaitUtils.isElementVisible(FORM_LOC, 10);
    }

    public void logout() {
        click(userMenu);
        click(logoutItem);
        logger.info("Logged out");
    }

    public boolean isWorkspaceHome() {
        return WaitUtils.isElementVisible(WORKSPACE_HOME_LOC, 15);
    }
}
