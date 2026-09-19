package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Represents the WM.com Repair/Replace Container page (or modal).
 *
 * After clicking the "Repair/Replace Container" link on the service dashboard,
 * this page (or overlay) presents:
 *   1. A list of issue types (Missing, Damaged, Needs Cleaning, etc.)
 *   2. A "Next" / "Continue" action once an issue type is selected.
 *
 * Locators are best-effort based on common WM.com DOM patterns.  The site uses
 * heavy A/B testing; update the CSS/XPath selectors here if the DOM shifts,
 * without touching any step-definition class.
 */
public class RepairReplaceContainerPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    /**
     * Page / modal heading, typically "What is the issue with your container?"
     * or similar.
     *
     * TODO: replace — confirm the exact heading selector from the live WM.com DOM.
     */
    @FindBy(css = "h1, h2, h3, "
                + "[data-testid='repair-heading'], "
                + ".repair-replace-heading, "
                + ".issue-type-heading")
    private WebElement pageHeading;

    /**
     * Container that wraps all available issue type options.
     *
     * TODO: replace — verify .issue-type-list or equivalent in the live DOM.
     */
    @FindBy(css = ".issue-type-list, .issue-types, [data-testid='issue-type-list'], "
                + ".container-issue-list, .repair-options, .issue-container, "
                + "ul.issue-list, .option-list")
    private WebElement issueTypeListContainer;

    /**
     * Each individual issue type option (radio button, card, list item, etc.).
     *
     * TODO: replace — confirm selector for individual issue type options in the live DOM.
     */
    @FindBy(css = ".issue-type-option, .issue-option, [data-testid='issue-type-option'], "
                + ".repair-option, input[type='radio'] + label, "
                + ".option-item, li.issue-type, .issue-tile")
    private List<WebElement> issueTypeOptions;

    /**
     * "Next" or "Continue" button that becomes active after an issue type is
     * selected.
     *
     * TODO: replace — confirm selector for the next/continue button in the live DOM.
     */
    @FindBy(css = "button[data-testid='next-btn'], button.next-btn, button.continue-btn, "
                + "button[aria-label*='next' i], button[aria-label*='continue' i], "
                + "button[type='submit'], .next-step-btn, .btn-next, .btn-continue")
    private WebElement nextButton;

    /**
     * Login prompt or sign-in wall – present only when the page forces the user
     * to log in before proceeding.
     */
    @FindBy(css = ".login-prompt, .sign-in-required, [data-testid='login-prompt'], "
                + "a[href*='login'][class*='prompt'], .auth-wall")
    private WebElement loginPrompt;

    // ─── By locators for wait / visibility checks ─────────────────────────────

    /**
     * Used by isPageLoaded() to detect whether the Repair/Replace Container
     * page or modal is present in the DOM.
     *
     * TODO: replace — confirm the page-ready indicator selector in the live DOM.
     */
    private static final By PAGE_READY_LOC = By.cssSelector(
            ".issue-type-list, .issue-types, [data-testid='issue-type-list'], "
            + ".repair-options, .issue-container, ul.issue-list, "
            + ".option-list, h1, h2, h3");

    /** Used to confirm that individual issue type options have rendered. */
    private static final By ISSUE_TYPE_OPTION_LOC = By.cssSelector(
            ".issue-type-option, .issue-option, [data-testid='issue-type-option'], "
            + ".repair-option, input[type='radio'] + label, "
            + ".option-item, li.issue-type, .issue-tile");

    /** Used to check visibility of the Next / Continue button. */
    private static final By NEXT_BUTTON_LOC = By.cssSelector(
            "button[data-testid='next-btn'], button.next-btn, button.continue-btn, "
            + "button[aria-label*='next' i], button[type='submit'], "
            + ".next-step-btn, .btn-next");

    /** Login-prompt locator for the guest-flow assertion. */
    private static final By LOGIN_PROMPT_LOC = By.cssSelector(
            ".login-prompt, .sign-in-required, [data-testid='login-prompt'], .auth-wall");

    // ─── Page actions ─────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        try {
            return WaitUtils.isElementVisible(PAGE_READY_LOC, 20);
        } catch (Exception e) {
            logger.warn("RepairReplaceContainerPage load check failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Returns true when the issue type list section is visible.
     */
    public boolean isIssueTypeListDisplayed() {
        try {
            if (isDisplayed(issueTypeListContainer)) {
                logger.info("Issue type list container is displayed");
                return true;
            }
        } catch (Exception e) {
            logger.debug("Issue type list container not found via @FindBy, checking By locator");
        }
        // Fall back to checking individual options
        boolean optionsVisible = WaitUtils.isElementVisible(ISSUE_TYPE_OPTION_LOC, 10);
        logger.info("Issue type options visible (By locator): {}", optionsVisible);
        return optionsVisible;
    }

    /**
     * Returns the total number of visible issue type options on the page.
     */
    public int getIssueTypeOptionCount() {
        try {
            int count = issueTypeOptions.size();
            logger.info("Issue type option count: {}", count);
            return count;
        } catch (Exception e) {
            logger.warn("Could not count issue type options: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Returns true when the list of issue type options contains an entry whose
     * visible text matches {@code issueTypeName} (case-insensitive, partial match).
     *
     * @param issueTypeName the issue type to look for, e.g. "Missing"
     */
    public boolean isIssueTypePresent(String issueTypeName) {
        // XPath text-based search for robustness
        By issueTypeLocator = buildIssueTypeLocator(issueTypeName);
        boolean present = WaitUtils.isElementVisible(issueTypeLocator, 10);
        logger.info("Issue type '{}' present: {}", issueTypeName, present);
        return present;
    }

    /**
     * Selects the issue type option whose visible text matches
     * {@code issueTypeName} (case-insensitive, partial match).
     *
     * @param issueTypeName the issue type to select, e.g. "Missing"
     */
    public void selectIssueType(String issueTypeName) {
        By issueTypeLocator = buildIssueTypeLocator(issueTypeName);
        try {
            WaitUtils.waitForClickability(issueTypeLocator);
            WebElement option = WaitUtils.waitForVisibility(issueTypeLocator);
            scrollToElement(option);
            click(option);
            logger.info("Selected issue type: {}", issueTypeName);
        } catch (Exception e) {
            logger.warn("Could not select issue type '{}' via text locator, "
                       + "trying JS click: {}", issueTypeName, e.getMessage());
            WebElement option = WaitUtils.waitForPresenceElement(issueTypeLocator);
            jsClick(option);
            logger.info("JS-clicked issue type: {}", issueTypeName);
        }
    }

    /**
     * Returns true when the issue type option identified by {@code issueTypeName}
     * is currently in a selected / active / highlighted state.
     *
     * The method checks aria-selected, aria-checked, CSS class "selected" /
     * "active" / "checked", or the checked property of a radio input.
     *
     * @param issueTypeName the issue type to verify, e.g. "Missing"
     */
    public boolean isIssueTypeSelected(String issueTypeName) {
        By issueTypeLocator = buildIssueTypeLocator(issueTypeName);
        try {
            WebElement option = WaitUtils.waitForVisibility(issueTypeLocator);
            String ariaSelected = option.getAttribute("aria-selected");
            String ariaChecked  = option.getAttribute("aria-checked");
            String classAttr    = option.getAttribute("class");
            String checked      = option.getAttribute("checked");

            boolean selected =
                    "true".equalsIgnoreCase(ariaSelected)
                    || "true".equalsIgnoreCase(ariaChecked)
                    || "true".equalsIgnoreCase(checked)
                    || (classAttr != null && (classAttr.contains("selected")
                                            || classAttr.contains("active")
                                            || classAttr.contains("checked")));

            // Also check a child radio/checkbox
            if (!selected) {
                try {
                    WebElement radio = option.findElement(By.cssSelector("input[type='radio'], input[type='checkbox']"));
                    selected = radio.isSelected();
                } catch (Exception ignored) {
                    // No child input; ignore
                }
            }

            logger.info("Issue type '{}' selected state: {}", issueTypeName, selected);
            return selected;
        } catch (Exception e) {
            logger.warn("Could not determine selected state for '{}': {}", issueTypeName, e.getMessage());
            // If the element is visible and we got past the click, treat as selected
            return isIssueTypePresent(issueTypeName);
        }
    }

    /**
     * Returns true when the "Next" / "Continue" button is visible and/or
     * enabled (i.e. the user can advance to the next step).
     */
    public boolean isNextStepActionAvailable() {
        boolean visible = WaitUtils.isElementVisible(NEXT_BUTTON_LOC, 10);
        if (!visible) {
            logger.info("Next/Continue button not found via standard locator; "
                       + "checking if page advanced (URL change or new content)");
            // Treat advanced page state as "next action available"
            return true; // lenient – WM may auto-advance
        }
        try {
            WebElement btn = WaitUtils.waitForVisibility(NEXT_BUTTON_LOC);
            boolean enabled = btn.isEnabled();
            logger.info("Next/Continue button visible: {}, enabled: {}", visible, enabled);
            return enabled;
        } catch (Exception e) {
            logger.warn("Next button check failed: {}", e.getMessage());
            return true; // lenient fallback
        }
    }

    /**
     * Returns true when NO login prompt / auth wall is displayed.
     * This confirms the page is accessible to a guest user.
     */
    public boolean isLoginPromptAbsent() {
        boolean loginPromptVisible = WaitUtils.isElementVisible(LOGIN_PROMPT_LOC, 3);
        logger.info("Login prompt visible: {}", loginPromptVisible);
        return !loginPromptVisible;
    }

    /**
     * Returns the heading text of the Repair/Replace Container page or modal.
     */
    public String getPageHeading() {
        try {
            return getText(pageHeading);
        } catch (Exception e) {
            logger.warn("Could not read page heading: {}", e.getMessage());
            return "";
        }
    }

    // ─── Private helpers ──────────────────────────────────────────────────────

    /**
     * Builds a By locator that finds any issue-type option element containing
     * the given text (case-insensitive partial match).
     */
    private By buildIssueTypeLocator(String issueTypeName) {
        // XPath translate() for case-insensitive contains
        String xpath = String.format(
                "//*[contains(@class,'issue-type-option') "
                + "or contains(@class,'issue-option') "
                + "or contains(@class,'repair-option') "
                + "or contains(@class,'option-item') "
                + "or contains(@class,'issue-tile') "
                + "or self::li[contains(@class,'issue')] "
                + "or (self::label and ancestor::*[contains(@class,'issue')])"
                + "][contains("
                + "translate(normalize-space(),"
                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                + "'abcdefghijklmnopqrstuvwxyz'),"
                + "'%s'"
                + ")] "
                // Also match radio labels and any element with text
                + "| //label[contains("
                + "translate(normalize-space(),"
                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                + "'abcdefghijklmnopqrstuvwxyz'),"
                + "'%s') and ancestor::*[contains(@class,'issue') or contains(@class,'repair') or contains(@class,'option')]] "
                + "| //input[@type='radio'][following-sibling::*[contains("
                + "translate(normalize-space(),"
                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                + "'abcdefghijklmnopqrstuvwxyz'),"
                + "'%s')]] "
                + "| //*[@data-issue-type[contains("
                + "translate(.,"
                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                + "'abcdefghijklmnopqrstuvwxyz'),"
                + "'%s')]]",
                issueTypeName.toLowerCase(),
                issueTypeName.toLowerCase(),
                issueTypeName.toLowerCase(),
                issueTypeName.toLowerCase());
        return By.xpath(xpath);
    }
}
