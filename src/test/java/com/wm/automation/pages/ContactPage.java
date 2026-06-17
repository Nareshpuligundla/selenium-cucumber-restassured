package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ConfigReader;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Represents the WM.com Contact Us / Support page.
 * Covers: contact form submission, phone/chat availability, FAQ link,
 * and form validation messages.
 */
public class ContactPage extends BasePage {

    private static final String CONTACT_PATH = "/contact-us";

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(css = "h1.contact-title, [data-testid='contact-heading'], h1")
    private WebElement pageHeading;

    // ─ Contact form fields ────────────────────────────────────────────────────
    @FindBy(css = "input[name='firstName'], input[id*='firstName' i], input[placeholder*='first name' i]")
    private WebElement firstNameInput;

    @FindBy(css = "input[name='lastName'], input[id*='lastName' i], input[placeholder*='last name' i]")
    private WebElement lastNameInput;

    @FindBy(css = "input[type='email'], input[name='email'], input[id*='email' i]")
    private WebElement emailInput;

    @FindBy(css = "input[type='tel'], input[name='phone'], input[id*='phone' i]")
    private WebElement phoneInput;

    @FindBy(css = "input[name='accountNumber'], input[id*='account' i], input[placeholder*='account' i]")
    private WebElement accountNumberInput;

    @FindBy(css = "select[name='topic'], select[id*='topic' i], select[name='subject']")
    private WebElement topicDropdown;

    @FindBy(css = "textarea[name='message'], textarea[id*='message' i], textarea[placeholder*='message' i]")
    private WebElement messageTextarea;

    @FindBy(css = "button[type='submit'], button.contact-submit, [data-testid='submit-contact']")
    private WebElement submitButton;

    // ─ Confirmation & errors ─────────────────────────────────────────────────
    @FindBy(css = ".success-banner, .confirmation-msg, [data-testid='form-success'], .form-submitted")
    private WebElement confirmationMessage;

    @FindBy(css = ".field-error, .validation-error, [data-testid='field-error']")
    private List<WebElement> fieldErrors;

    @FindBy(css = ".global-error, .alert-danger, [role='alert']")
    private WebElement globalError;

    // ─ Support channels ───────────────────────────────────────────────────────
    @FindBy(css = ".phone-number, a[href^='tel:'], [data-testid='support-phone']")
    private WebElement supportPhoneNumber;

    @FindBy(css = "button.chat-btn, .chat-widget-trigger, [data-testid='chat-btn']")
    private WebElement liveChatButton;

    @FindBy(css = "a[href*='faq'], a[href*='help'], .faq-link")
    private WebElement faqLink;

    private static final By FORM_READY_LOC =
            By.cssSelector("input[name='firstName'], textarea[name='message'], h1");

    private static final By CONFIRMATION_LOC =
            By.cssSelector(".success-banner, .confirmation-msg, [data-testid='form-success']");

    // ─── Actions ──────────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(FORM_READY_LOC, 10);
    }

    public void open() {
        navigateTo(ConfigReader.getInstance().getBaseUrl() + CONTACT_PATH);
        logger.info("Opened Contact Us page");
    }

    public void enterFirstName(String firstName) {
        type(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        type(lastNameInput, lastName);
    }

    public void enterEmail(String email) {
        type(emailInput, email);
    }

    public void enterPhone(String phone) {
        type(phoneInput, phone);
    }

    public void enterAccountNumber(String accountNumber) {
        type(accountNumberInput, accountNumber);
    }

    public void selectTopic(String topicText) {
        ElementUtils.selectByVisibleText(topicDropdown, topicText);
        logger.info("Selected topic: {}", topicText);
    }

    public void enterMessage(String message) {
        type(messageTextarea, message);
    }

    public void submitForm() {
        scrollToElement(submitButton);
        click(submitButton);
        logger.info("Clicked Submit on Contact form");
    }

    /**
     * Fills and submits the entire contact form in one call.
     */
    public void fillAndSubmit(String firstName, String lastName, String email,
                               String phone, String message) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPhone(phone);
        enterMessage(message);
        submitForm();
        logger.info("Contact form submitted for: {} {}", firstName, lastName);
    }

    public boolean isConfirmationDisplayed() {
        return WaitUtils.isElementVisible(CONFIRMATION_LOC, 10);
    }

    public String getConfirmationText() {
        try {
            return getText(confirmationMessage);
        } catch (Exception e) {
            return "";
        }
    }

    public int getFieldErrorCount() {
        return fieldErrors.size();
    }

    public boolean hasFieldErrors() {
        return !fieldErrors.isEmpty();
    }

    public String getSupportPhoneNumber() {
        try {
            return getText(supportPhoneNumber);
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isLiveChatAvailable() {
        return isDisplayed(liveChatButton);
    }

    public void clickLiveChat() {
        click(liveChatButton);
        logger.info("Opened live chat");
    }

    public void clickFaqLink() {
        click(faqLink);
        logger.info("Navigated to FAQ page");
    }

    public boolean isFaqLinkVisible() {
        return isDisplayed(faqLink);
    }

    public String getPageHeading() {
        try {
            return getText(pageHeading);
        } catch (Exception e) {
            return "";
        }
    }
}
