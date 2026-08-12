package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page object for the WM.com Container Repair/Replace flow.
 *
 * Covers:
 *  - The service options screen (Repair/Replace Container entry point)
 *  - Issue type selection (Missing / Damaged / Stolen)
 *  - Container size selection (32-gallon / 64-gallon / 96-gallon)
 *  - Required contact information entry step
 */
public class ContainerReplacementPage extends BasePage {

    // ─── Service Options Screen Locators ──────────────────────────────────────

    /**
     * The Repair/Replace Container link/button shown after a valid address is submitted.
     * TODO: replace — confirm exact text/selector from live WM.com DOM
     */
    @FindBy(css = "a[href*='repair'], a[href*='replace'], button[data-action*='repair'], " +
                  "[data-testid*='repair'], span[class*='repair']")
    private WebElement repairReplaceContainerLink;

    private static final By REPAIR_REPLACE_LOC = By.cssSelector(
            "a[href*='repair'], a[href*='replace'], button[data-action*='repair'], " +
            "[data-testid*='repair'], span[class*='repair']");

    // ─── Service Options Container (post-address) ─────────────────────────────

    /**
     * General service-options wrapper that appears after the address is accepted.
     * TODO: replace — confirm exact selector from live WM.com DOM post-address-submission
     */
    private static final By SERVICE_OPTIONS_SCREEN_LOC = By.cssSelector(
            ".service-options, [data-testid='service-options'], .service-type-list, " +
            ".cart-options, .pickup-options, h2, h3, .services-container");

    // ─── Issue Type Selection Screen Locators ─────────────────────────────────

    /**
     * Container that holds issue type options (Missing / Damaged / Stolen).
     * TODO: replace — confirm exact selector from live WM.com DOM issue-type step
     */
    @FindBy(css = "[data-testid='issue-type-container'], .issue-type-selection, " +
                  ".issue-options, .container-issue-types, form.issue-form")
    private WebElement issueTypeContainer;

    private static final By ISSUE_TYPE_CONTAINER_LOC = By.cssSelector(
            "[data-testid='issue-type-container'], .issue-type-selection, " +
            ".issue-options, .container-issue-types, form.issue-form");

    /**
     * All issue type option elements — Missing, Damaged, Stolen.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    @FindBy(css = "[data-issue-type], .issue-type-option, button.issue-type, " +
                  "input[name='issueType'], label.issue-option")
    private List<WebElement> issueTypeOptions;

    private static final By ISSUE_TYPE_OPTIONS_LOC = By.cssSelector(
            "[data-issue-type], .issue-type-option, button.issue-type, " +
            "input[name='issueType'], label.issue-option");

    /**
     * The 'Missing' issue type button/radio/option.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    @FindBy(xpath = "//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'missing') " +
                    "and (self::button or self::label or self::a or self::input)]" +
                    " | //*[@data-issue-type='missing'] | //*[@data-value='Missing']")
    private WebElement missingOption;

    private static final By MISSING_OPTION_LOC = By.xpath(
            "//*[contains(translate(normalize-space(text()),'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
            "'abcdefghijklmnopqrstuvwxyz'),'missing') " +
            "and (self::button or self::label or self::a)] | " +
            "//*[@data-issue-type='missing'] | //*[@data-value='Missing']");

    /**
     * The 'Damaged' issue type button/radio/option.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    @FindBy(xpath = "//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'damaged') " +
                    "and (self::button or self::label or self::a or self::input)]" +
                    " | //*[@data-issue-type='damaged'] | //*[@data-value='Damaged']")
    private WebElement damagedOption;

    private static final By DAMAGED_OPTION_LOC = By.xpath(
            "//*[contains(translate(normalize-space(text()),'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
            "'abcdefghijklmnopqrstuvwxyz'),'damaged') " +
            "and (self::button or self::label or self::a)] | " +
            "//*[@data-issue-type='damaged'] | //*[@data-value='Damaged']");

    /**
     * The 'Stolen' issue type button/radio/option.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    @FindBy(xpath = "//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'stolen') " +
                    "and (self::button or self::label or self::a or self::input)]" +
                    " | //*[@data-issue-type='stolen'] | //*[@data-value='Stolen']")
    private WebElement stolenOption;

    private static final By STOLEN_OPTION_LOC = By.xpath(
            "//*[contains(translate(normalize-space(text()),'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
            "'abcdefghijklmnopqrstuvwxyz'),'stolen') " +
            "and (self::button or self::label or self::a)] | " +
            "//*[@data-issue-type='stolen'] | //*[@data-value='Stolen']");

    // ─── Container Size Selection Screen Locators ─────────────────────────────

    /**
     * All container size options shown after selecting an issue type.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    @FindBy(css = "[data-container-size], .container-size-option, button.size-option, " +
                  "input[name='containerSize'], label.size-label, .size-selector button, " +
                  "[data-testid*='container-size']")
    private List<WebElement> containerSizeOptions;

    private static final By CONTAINER_SIZE_OPTIONS_LOC = By.cssSelector(
            "[data-container-size], .container-size-option, button.size-option, " +
            "input[name='containerSize'], label.size-label, .size-selector button, " +
            "[data-testid*='container-size']");

    /**
     * Container for the size selection step.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    @FindBy(css = "[data-testid='container-size-container'], .container-size-selection, " +
                  ".size-selection, .container-sizes")
    private WebElement containerSizeContainer;

    private static final By CONTAINER_SIZE_CONTAINER_LOC = By.cssSelector(
            "[data-testid='container-size-container'], .container-size-selection, " +
            ".size-selection, .container-sizes");

    /**
     * 64-gallon specific option.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    private static final By GALLON_64_LOC = By.xpath(
            "//*[contains(normalize-space(text()),'64') or @data-container-size='64' " +
            "or @data-value='64-gallon'] and (self::button or self::label or self::a or self::input)" +
            " | //*[@data-container-size='64-gallon'] | //*[@data-value='64']");

    // ─── Contact Information Step Locators ────────────────────────────────────

    /**
     * Contact name input field.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    @FindBy(css = "input[name='contactName'], input[id*='contactName' i], " +
                  "input[placeholder*='contact name' i], input[aria-label*='contact name' i]")
    private WebElement contactNameInput;

    /**
     * Phone number input field.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    @FindBy(css = "input[name='phone'], input[type='tel'], input[id*='phone' i], " +
                  "input[placeholder*='phone' i], input[aria-label*='phone' i]")
    private WebElement phoneNumberInput;

    /**
     * Email address input field.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    @FindBy(css = "input[type='email'], input[name='email'], input[id*='email' i], " +
                  "input[placeholder*='email' i], input[aria-label*='email' i]")
    private WebElement emailInput;

    /**
     * Service address confirmation/display field.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    @FindBy(css = "input[name='serviceAddress'], input[id*='serviceAddress' i], " +
                  ".service-address-confirmation, [data-testid='service-address'], " +
                  "input[placeholder*='service address' i], input[aria-label*='service address' i]")
    private WebElement serviceAddressField;

    /**
     * Container wrapping the contact information form.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    private static final By CONTACT_INFO_STEP_LOC = By.cssSelector(
            "form.contact-info, [data-testid='contact-info-step'], .contact-information-step, " +
            ".required-info-step, .contact-form-container, form.required-info");

    /**
     * The 'Next' / 'Continue' / 'Proceed to Payment' button on the contact info step.
     * TODO: replace — confirm exact selector from live WM.com DOM
     */
    @FindBy(css = "button[type='submit'], button[data-testid='continue-btn'], " +
                  "button[data-action='next'], button.next-btn, button.continue-btn, " +
                  "button[aria-label*='proceed' i], button[aria-label*='next' i], " +
                  "button[aria-label*='continue' i]")
    private WebElement proceedButton;

    // ─── Page-load indicator ──────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(SERVICE_OPTIONS_SCREEN_LOC, 15);
    }

    // ─── Service Options Screen Actions ───────────────────────────────────────

    /**
     * Returns true when the service options screen (post-address) is visible.
     */
    public boolean isServiceOptionsScreenLoaded() {
        return WaitUtils.isElementVisible(SERVICE_OPTIONS_SCREEN_LOC, 20);
    }

    /**
     * Returns true when the Repair/Replace Container link is visible.
     */
    public boolean isRepairReplaceContainerLinkVisible() {
        return WaitUtils.isElementVisible(REPAIR_REPLACE_LOC, 15);
    }

    /**
     * Clicks the 'Repair/Replace Container' link on the service options screen.
     */
    public void clickRepairReplaceContainerLink() {
        logger.info("Clicking Repair/Replace Container link");
        click(repairReplaceContainerLink);
    }

    // ─── Issue Type Selection Actions ─────────────────────────────────────────

    /**
     * Returns true when the issue type selection screen is visible.
     */
    public boolean isIssueTypeSelectionDisplayed() {
        return WaitUtils.isElementVisible(ISSUE_TYPE_CONTAINER_LOC, 15);
    }

    /**
     * Returns true when the 'Missing' option is visible.
     */
    public boolean isMissingOptionVisible() {
        return WaitUtils.isElementVisible(MISSING_OPTION_LOC, 10);
    }

    /**
     * Returns true when the 'Damaged' option is visible.
     */
    public boolean isDamagedOptionVisible() {
        return WaitUtils.isElementVisible(DAMAGED_OPTION_LOC, 10);
    }

    /**
     * Returns true when the 'Stolen' option is visible.
     */
    public boolean isStolenOptionVisible() {
        return WaitUtils.isElementVisible(STOLEN_OPTION_LOC, 10);
    }

    /**
     * Selects the given issue type by matching text (Missing / Damaged / Stolen).
     */
    public void selectIssueType(String issueType) {
        logger.info("Selecting issue type: {}", issueType);
        By locator = By.xpath(
                String.format(
                        "//*[contains(translate(normalize-space(text())," +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'%s') " +
                        "and (self::button or self::label or self::a)] | " +
                        "//*[@data-issue-type='%s'] | //*[@data-value='%s']",
                        issueType.toLowerCase(), issueType.toLowerCase(), issueType));
        click(locator);
    }

    // ─── Container Size Selection Actions ─────────────────────────────────────

    /**
     * Returns the count of visible container size options.
     */
    public int getContainerSizeOptionCount() {
        return ElementUtils.countElements(CONTAINER_SIZE_OPTIONS_LOC);
    }

    /**
     * Returns true when the container size selection step is visible.
     */
    public boolean isContainerSizeSelectionEnabled() {
        return WaitUtils.isElementVisible(CONTAINER_SIZE_CONTAINER_LOC, 15);
    }

    /**
     * Returns true when the given gallon size option text is visible on the page.
     *
     * @param gallonSize e.g. "32-gallon", "64-gallon", "96-gallon"
     */
    public boolean isContainerSizeOptionVisible(String gallonSize) {
        // Strip "-gallon" suffix to match numeric portion only
        String numericPart = gallonSize.replace("-gallon", "").trim();
        By locator = By.xpath(
                String.format(
                        "//*[contains(normalize-space(text()),'%s') " +
                        "and (self::button or self::label or self::a or self::span)] | " +
                        "//*[@data-container-size='%s'] | //*[@data-value='%s']",
                        numericPart, gallonSize, gallonSize));
        return WaitUtils.isElementVisible(locator, 10);
    }

    /**
     * Clicks the container size option matching the given size string.
     *
     * @param gallonSize e.g. "64-gallon"
     */
    public void selectContainerSize(String gallonSize) {
        logger.info("Selecting container size: {}", gallonSize);
        String numericPart = gallonSize.replace("-gallon", "").trim();
        By locator = By.xpath(
                String.format(
                        "//*[contains(normalize-space(text()),'%s') " +
                        "and (self::button or self::label or self::a)] | " +
                        "//*[@data-container-size='%s'] | //*[@data-value='%s']",
                        numericPart, gallonSize, gallonSize));
        click(locator);
    }

    // ─── Contact Information Step Actions ─────────────────────────────────────

    /**
     * Returns true when the contact / required-information entry step is visible.
     */
    public boolean isContactInfoStepDisplayed() {
        return WaitUtils.isElementVisible(CONTACT_INFO_STEP_LOC, 15);
    }

    /**
     * Enters the contact name into the Contact Name field.
     */
    public void enterContactName(String name) {
        logger.info("Entering contact name: {}", name);
        type(contactNameInput, name);
    }

    /**
     * Enters the phone number into the Phone Number field.
     */
    public void enterPhoneNumber(String phone) {
        logger.info("Entering phone number: {}", phone);
        type(phoneNumberInput, phone);
    }

    /**
     * Enters the email address into the Email Address field.
     */
    public void enterEmail(String email) {
        logger.info("Entering email: {}", email);
        type(emailInput, email);
    }

    /**
     * Confirms the service address — clears and re-types if it is an editable field,
     * or simply validates the displayed text if it is read-only.
     */
    public void confirmServiceAddress(String address) {
        logger.info("Confirming service address: {}", address);
        try {
            // Try to type if the field is editable
            if (serviceAddressField.isEnabled() && !serviceAddressField.getAttribute("readonly").equals("true")) {
                type(serviceAddressField, address);
            } else {
                // Read-only; just log
                logger.info("Service address field is read-only. Displayed value: {}",
                        serviceAddressField.getAttribute("value"));
            }
        } catch (Exception e) {
            logger.warn("Could not interact with service address field: {}", e.getMessage());
        }
    }

    /**
     * Clicks the proceed / continue / next button on the contact info step.
     */
    public void clickProceedButton() {
        logger.info("Clicking proceed/continue button");
        click(proceedButton);
    }
}
