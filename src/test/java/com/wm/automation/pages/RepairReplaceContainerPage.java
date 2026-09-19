package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page object representing the WM.com service options page that appears after
 * a valid residential address has been submitted and a service type selected.
 *
 * This page should display service options including the
 * 'Repair/Replace Container' link, accessible to guest (unauthenticated) users.
 *
 * TC-002: Guest user sees Repair/Replace Container link after entering valid
 *          residential address ('456 Oak Ave, Dallas, TX 75201') and selecting 'Home'.
 */
public class RepairReplaceContainerPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    /**
     * The 'Repair/Replace Container' link or button displayed on the service
     * options page.
     * TODO: replace — verify the exact selector in the live WM.com DOM; the
     *       link text may be slightly different (e.g. "Repair or Replace Container").
     */
    @FindBy(css = "a[href*='repair' i], a[href*='replace' i], " +
                  "button[data-service*='repair' i], " +
                  "[data-testid*='repair' i], [data-testid*='replace' i]")
    private WebElement repairReplaceLink;

    /**
     * Container holding all service option cards / links after address lookup.
     * TODO: replace — confirm CSS selector against live WM.com service-options
     *       page DOM; the container class name may differ by A/B test variant.
     */
    @FindBy(css = ".service-options, .service-type-list, " +
                  "[data-testid='service-options'], .cart-options, " +
                  ".pickup-options, .services-container, .available-services")
    private WebElement serviceOptionsContainer;

    /**
     * All individual service option cards/links on the page.
     * TODO: replace — confirm list item selector against live WM.com DOM.
     */
    @FindBy(css = ".service-card, .service-option, [data-testid='service-card'], " +
                  ".service-item, .service-link")
    private List<WebElement> allServiceOptionItems;

    /**
     * Login / sign-in form or modal that must NOT be present for a guest user.
     * TODO: replace — validate that the selector accurately matches any
     *       authentication wall that WM.com may display.
     */
    @FindBy(css = "form.login-form, #loginModal, [data-testid='login-modal'], " +
                  ".sign-in-required, .auth-wall, form[action*='login' i]")
    private WebElement loginPrompt;

    /**
     * Page-level heading confirming that we are on the service options page.
     * TODO: replace — confirm heading selector against live WM.com service
     *       options page to ensure the right element is targeted.
     */
    @FindBy(css = "h1, h2.page-title, [data-testid='page-heading'], " +
                  ".service-page-title, .selection-title")
    private WebElement pageHeading;

    // ─── Static By locators (used for explicit waits) ──────────────────────────

    /**
     * By locator for the Repair/Replace Container link.
     * TODO: replace — adjust text/attribute values once real DOM is confirmed.
     */
    private static final By REPAIR_REPLACE_LOC = By.cssSelector(
            "a[href*='repair' i], a[href*='replace' i], " +
            "button[data-service*='repair' i], " +
            "[data-testid*='repair' i], [data-testid*='replace' i]");

    /**
     * By locator for any visible element that signals the service-options page
     * has fully loaded.
     * TODO: replace — tighten selector once the live DOM structure is confirmed.
     */
    private static final By SERVICE_OPTIONS_LOADED_LOC = By.cssSelector(
            ".service-options, .service-type-list, [data-testid='service-options'], " +
            ".cart-options, .pickup-options, .services-container, " +
            ".available-services, h2, h3");

    /**
     * By locator for the login prompt – absence expected for guest user.
     * TODO: replace — verify selector against any authentication-required
     *       overlays on WM.com.
     */
    private static final By LOGIN_PROMPT_LOC = By.cssSelector(
            "form.login-form, #loginModal, [data-testid='login-modal'], " +
            ".sign-in-required, .auth-wall, form[action*='login' i]");

    /**
     * Link text matcher used as an additional fallback to locate the
     * Repair/Replace Container element by its visible label.
     * TODO: replace — confirm exact link text from the live WM.com page.
     */
    private static final By REPAIR_REPLACE_LINK_TEXT_LOC = By.xpath(
            "//*[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),"
            + "'REPAIR') or contains(translate(text(),'abcdefghijklmnopqrstuvwxyz',"
            + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'REPLACE')]"
            + "[self::a or self::button or self::span[parent::a] or self::span[parent::button]]");

    // ─── Page actions ─────────────────────────────────────────────────────────

    /**
     * Returns {@code true} when the service-options page has loaded and at least
     * one service-option element (heading, container, or individual card) is present.
     */
    @Override
    public boolean isPageLoaded() {
        try {
            WaitUtils.waitForPresence(SERVICE_OPTIONS_LOADED_LOC);
            logger.info("Service options page loaded successfully");
            return true;
        } catch (Exception e) {
            logger.warn("Service options page did not load within timeout: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Waits up to {@code timeoutSeconds} seconds for the service-options page to
     * load and then returns {@code true} if it is visible.
     *
     * @param timeoutSeconds maximum wait time in seconds
     * @return {@code true} if service options are visible within the timeout
     */
    public boolean isServiceOptionsPageLoaded(long timeoutSeconds) {
        boolean visible = WaitUtils.isElementVisible(SERVICE_OPTIONS_LOADED_LOC, timeoutSeconds);
        logger.info("Service options page visible within {}s: {}", timeoutSeconds, visible);
        return visible;
    }

    /**
     * Returns {@code true} if the 'Repair/Replace Container' element is visible
     * on the page using either CSS selector or link-text XPath fallback.
     */
    public boolean isRepairReplaceContainerLinkVisible() {
        // Primary check via CSS selector
        boolean visible = WaitUtils.isElementVisible(REPAIR_REPLACE_LOC, 10);
        if (visible) {
            logger.info("'Repair/Replace Container' link found via CSS selector");
            return true;
        }
        // Fallback: locate by visible text content
        boolean visibleByText = WaitUtils.isElementVisible(REPAIR_REPLACE_LINK_TEXT_LOC, 5);
        logger.info("'Repair/Replace Container' link found via text XPath fallback: {}", visibleByText);
        return visibleByText;
    }

    /**
     * Returns {@code true} if the 'Repair/Replace Container' element is both
     * visible and clickable (enabled).
     */
    public boolean isRepairReplaceContainerLinkClickable() {
        try {
            // Try primary CSS locator first
            WebElement link;
            try {
                link = WaitUtils.waitForClickability(REPAIR_REPLACE_LOC);
            } catch (Exception e) {
                logger.info("Primary locator not clickable, trying text-based XPath");
                link = WaitUtils.waitForClickability(REPAIR_REPLACE_LINK_TEXT_LOC);
            }
            boolean clickable = link != null && link.isEnabled();
            logger.info("'Repair/Replace Container' link clickable: {}", clickable);
            return clickable;
        } catch (Exception e) {
            logger.warn("'Repair/Replace Container' link not found or not clickable: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Returns {@code true} if a login prompt (modal, form, or page) is currently
     * displayed; expected to be {@code false} for guest user flows.
     */
    public boolean isLoginPromptDisplayed() {
        boolean displayed = WaitUtils.isElementVisible(LOGIN_PROMPT_LOC, 5);
        logger.info("Login prompt displayed: {}", displayed);
        return displayed;
    }

    /**
     * Returns the visible text of the page heading on the service-options page,
     * or an empty string when no heading is found.
     */
    public String getPageHeadingText() {
        try {
            String text = getText(pageHeading);
            logger.info("Service options page heading: '{}'", text);
            return text;
        } catch (Exception e) {
            logger.warn("Could not read page heading: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Clicks the 'Repair/Replace Container' link/button.
     * Tries the CSS selector first; falls back to the text-based XPath locator.
     */
    public void clickRepairReplaceContainerLink() {
        try {
            click(REPAIR_REPLACE_LOC);
            logger.info("Clicked 'Repair/Replace Container' via CSS selector");
        } catch (Exception e) {
            logger.warn("CSS selector click failed, attempting text-based XPath click");
            click(REPAIR_REPLACE_LINK_TEXT_LOC);
            logger.info("Clicked 'Repair/Replace Container' via text XPath fallback");
        }
    }

    /**
     * Returns the number of service-option items currently displayed on the page.
     */
    public int getServiceOptionCount() {
        int count = allServiceOptionItems != null ? allServiceOptionItems.size() : 0;
        logger.info("Number of service option items on page: {}", count);
        return count;
    }
}
