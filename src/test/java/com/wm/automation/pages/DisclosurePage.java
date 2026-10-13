package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object for the /application/disclosure page.
 * Covers: page heading, scrollable disclosure panel, acceptance checkbox,
 * Continue button, and inline validation message.
 */
public class DisclosurePage extends BasePage {

    private static final String DISCLOSURE_PATH = "/application/disclosure";

    // ─── Locators ─────────────────────────────────────────────────────────────

    // TODO: replace - confirm exact h1 selector on /application/disclosure page
    @FindBy(css = "h1")
    private WebElement pageHeading;

    // TODO: replace - confirm scrollable disclosure panel selector (e.g. data-testid, class name)
    @FindBy(css = ".disclosure-panel, [data-testid='disclosure-panel'], .disclosure-text-container")
    private WebElement disclosurePanel;

    // TODO: replace - confirm acceptance checkbox selector on disclosure page
    @FindBy(css = "input[type='checkbox'][name*='agree' i], input[type='checkbox'][id*='disclosure' i], [data-testid='disclosure-checkbox']")
    private WebElement acceptCheckbox;

    // TODO: replace - confirm Continue button selector on disclosure page
    @FindBy(css = "button[data-testid='continue-btn'], button.continue-btn, button[type='submit']")
    private WebElement continueButton;

    // TODO: replace - confirm inline validation message selector on disclosure page
    @FindBy(css = ".disclosure-validation-msg, [data-testid='disclosure-validation'], .validation-message")
    private WebElement validationMessage;

    private static final By PAGE_READY_LOC =
            By.cssSelector("h1, .disclosure-panel, [data-testid='disclosure-panel']");

    private static final By VALIDATION_MSG_LOC =
            By.cssSelector(".disclosure-validation-msg, [data-testid='disclosure-validation'], .validation-message");

    private static final By ACCEPT_CHECKBOX_LOC =
            By.cssSelector("input[type='checkbox'][name*='agree' i], input[type='checkbox'][id*='disclosure' i], [data-testid='disclosure-checkbox']");

    // ─── Actions ──────────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(PAGE_READY_LOC, 15);
    }

    public void open(String baseUrl) {
        navigateTo(baseUrl + DISCLOSURE_PATH);
        logger.info("Opened Disclosure page");
    }

    public String getPageHeading() {
        try {
            waitForVisibility(pageHeading);
            return getText(pageHeading);
        } catch (Exception e) {
            logger.warn("Could not read page heading: {}", e.getMessage());
            return "";
        }
    }

    public boolean isDisclosurePanelVisible() {
        return isDisplayed(disclosurePanel);
    }

    public boolean isDisclosurePanelScrollable() {
        try {
            String overflow = disclosurePanel.getCssValue("overflow-y");
            long scrollHeight = (Long) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].scrollHeight", disclosurePanel);
            long clientHeight = (Long) ((JavascriptExecutor) driver)
                    .executeScript("return arguments[0].clientHeight", disclosurePanel);
            boolean hasScrollableOverflow = overflow.equals("auto") || overflow.equals("scroll");
            boolean contentExceedsPanel = scrollHeight > clientHeight;
            logger.info("Disclosure panel overflow-y={}, scrollHeight={}, clientHeight={}",
                    overflow, scrollHeight, clientHeight);
            return hasScrollableOverflow && contentExceedsPanel;
        } catch (Exception e) {
            logger.warn("Could not determine panel scrollability: {}", e.getMessage());
            return false;
        }
    }

    public void scrollDisclosurePanelToBottom() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollTop = arguments[0].scrollHeight", disclosurePanel);
            logger.info("Scrolled disclosure panel to bottom");
        } catch (Exception e) {
            logger.warn("JS scroll on panel failed, falling back to page scroll: {}", e.getMessage());
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        }
    }

    public boolean isAcceptCheckboxDisabled() {
        try {
            String ariaDisabled = acceptCheckbox.getAttribute("aria-disabled");
            boolean htmlDisabled = !acceptCheckbox.isEnabled();
            logger.info("Accept checkbox aria-disabled={}, htmlDisabled={}", ariaDisabled, htmlDisabled);
            return htmlDisabled || "true".equalsIgnoreCase(ariaDisabled);
        } catch (Exception e) {
            logger.warn("Could not determine checkbox disabled state: {}", e.getMessage());
            return true;
        }
    }

    public boolean isAcceptCheckboxEnabled() {
        return !isAcceptCheckboxDisabled();
    }

    public boolean isAcceptCheckboxChecked() {
        try {
            return acceptCheckbox.isSelected();
        } catch (Exception e) {
            logger.warn("Could not determine checkbox checked state: {}", e.getMessage());
            return false;
        }
    }

    public void clickAcceptCheckbox() {
        waitForClickability(acceptCheckbox);
        click(acceptCheckbox);
        logger.info("Clicked acceptance checkbox");
    }

    public boolean isContinueButtonDisabled() {
        try {
            String ariaDisabled = continueButton.getAttribute("aria-disabled");
            boolean htmlDisabled = !continueButton.isEnabled();
            logger.info("Continue button aria-disabled={}, htmlDisabled={}", ariaDisabled, htmlDisabled);
            return htmlDisabled || "true".equalsIgnoreCase(ariaDisabled);
        } catch (Exception e) {
            logger.warn("Could not determine Continue button disabled state: {}", e.getMessage());
            return true;
        }
    }

    public boolean isContinueButtonEnabled() {
        return !isContinueButtonDisabled();
    }

    public void clickContinueButton() {
        click(continueButton);
        logger.info("Clicked Continue button");
    }

    public boolean isValidationMessageDisplayed() {
        return WaitUtils.isElementVisible(VALIDATION_MSG_LOC, 5);
    }

    public String getValidationMessage() {
        try {
            return getText(validationMessage);
        } catch (Exception e) {
            logger.warn("Could not read validation message: {}", e.getMessage());
            return "";
        }
    }
}
