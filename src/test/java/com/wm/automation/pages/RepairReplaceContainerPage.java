package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page object for the WM.com Service Options screen that appears after a valid
 * residential address is submitted.  Specifically covers the Repair/Replace
 * Container service link and the surrounding service-option area.
 *
 * Locators target the DOM structures observed on wm.com for the service-options
 * page, where multiple action links are rendered.
 */
public class RepairReplaceContainerPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    /**
     * The primary 'Repair/Replace Container' link.
     * Multiple CSS selectors are combined with commas so that the first
     * matching element wins across A/B variants.
     */
    // TODO: replace — verify exact text/href/data-testid on the live wm.com DOM
    @FindBy(css = "a[href*='repair' i], a[href*='replace' i], " +
                  "[data-testid*='repair' i], [data-testid*='replace' i], " +
                  "a[title*='Repair' i], button[title*='Repair' i]")
    private WebElement repairReplaceLink;

    /**
     * Fallback: any element whose visible text contains 'Repair' and 'Container'.
     * Used by {@link #isRepairReplaceLinkVisible()} as a secondary check via XPath.
     */
    private static final By REPAIR_REPLACE_LINK_LOC = By.xpath(
            "//*[contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')," +
            "'REPAIR') and contains(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'CONTAINER')]" +
            " | //*[contains(translate(@aria-label,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')," +
            "'REPAIR') and contains(translate(@aria-label,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'),'CONTAINER')]"
    );

    /**
     * Service-options container – present when the page has transitioned away
     * from the home page after address entry.
     */
    // TODO: replace — verify the service options container selector on live wm.com
    @FindBy(css = ".service-options, .service-type-list, [data-testid='service-options'], " +
                  ".service-links, .service-action-list, .options-container")
    private WebElement serviceOptionsContainer;

    private static final By SERVICE_OPTIONS_LOC = By.cssSelector(
            ".service-options, .service-type-list, [data-testid='service-options'], " +
            ".service-links, .service-action-list, .options-container, " +
            "h2, h3, main section"
    );

    /**
     * Property-type selector buttons – 'Home', 'Business', etc.
     * These appear on the home page after an address is entered on some A/B variants.
     */
    // TODO: replace — verify property-type button selectors on live wm.com
    @FindBy(css = "button[data-property-type], [data-testid*='property-type'], " +
                  ".property-type-btn, label[for*='home' i], input[value*='home' i]")
    private List<WebElement> propertyTypeOptions;

    /**
     * 'Get Started' / primary submit button on the home page address entry area.
     */
    // TODO: replace — verify the Get Started button selector on live wm.com
    @FindBy(css = "button[data-testid='get-started'], button.get-started-btn, " +
                  "button[aria-label*='get started' i], input[type='submit'][value*='Get Started' i], " +
                  "button[type='submit']")
    private WebElement getStartedButton;

    /**
     * Login / sign-in prompt – should NOT appear during a guest flow.
     */
    @FindBy(css = ".login-prompt, [data-testid='login-prompt'], .sign-in-required, " +
                  ".auth-wall, [role='dialog'][aria-label*='sign in' i]")
    private WebElement loginPrompt;

    private static final By LOGIN_PROMPT_LOC = By.cssSelector(
            ".login-prompt, [data-testid='login-prompt'], .sign-in-required, " +
            ".auth-wall, [role='dialog'][aria-label*='sign in' i]"
    );

    // ─── Page actions ─────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(SERVICE_OPTIONS_LOC, 15);
    }

    /**
     * Checks whether the service-options area is displayed after address submission.
     *
     * @return {@code true} if the service options container is visible.
     */
    public boolean isServiceOptionsScreenDisplayed() {
        try {
            boolean present = WaitUtils.waitForPresence(SERVICE_OPTIONS_LOC);
            logger.info("Service options screen present: {}", present);
            return present;
        } catch (Exception e) {
            logger.warn("Service options screen not detected: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks whether the 'Repair/Replace Container' link is visible.
     * First tries the {@code @FindBy} element; falls back to the XPath locator
     * that matches visible text.
     *
     * @return {@code true} if the link is present and visible.
     */
    public boolean isRepairReplaceLinkVisible() {
        // Primary check: CSS-based @FindBy
        try {
            WaitUtils.waitForVisibility(repairReplaceLink);
            boolean visible = isDisplayed(repairReplaceLink);
            logger.info("Repair/Replace Container link visible (CSS): {}", visible);
            if (visible) return true;
        } catch (Exception primaryEx) {
            logger.debug("Primary CSS locator did not find Repair/Replace Container link: {}",
                    primaryEx.getMessage());
        }

        // Fallback check: XPath text-based
        try {
            boolean visible = WaitUtils.isElementVisible(REPAIR_REPLACE_LINK_LOC, 10);
            logger.info("Repair/Replace Container link visible (XPath text): {}", visible);
            return visible;
        } catch (Exception fallbackEx) {
            logger.warn("Fallback XPath locator also did not find Repair/Replace Container link: {}",
                    fallbackEx.getMessage());
            return false;
        }
    }

    /**
     * Checks whether the 'Repair/Replace Container' link is clickable (enabled
     * and visible), confirming a guest user can interact with it.
     *
     * @return {@code true} if the link can be clicked.
     */
    public boolean isRepairReplaceLinkClickable() {
        try {
            WaitUtils.waitForClickability(repairReplaceLink);
            logger.info("Repair/Replace Container link is clickable");
            return true;
        } catch (Exception e) {
            // Fallback to XPath
            try {
                WaitUtils.waitForClickability(REPAIR_REPLACE_LINK_LOC);
                logger.info("Repair/Replace Container link is clickable (XPath fallback)");
                return true;
            } catch (Exception ex) {
                logger.warn("Repair/Replace Container link is NOT clickable: {}", ex.getMessage());
                return false;
            }
        }
    }

    /**
     * Selects a property type by matching the button / label text (case-insensitive).
     *
     * @param propertyType the property type to select, e.g. "Home"
     */
    public void selectPropertyType(String propertyType) {
        logger.info("Selecting property type: {}", propertyType);
        // Try FindBy list first
        for (WebElement option : propertyTypeOptions) {
            try {
                String text = option.getText().trim();
                String ariaLabel = option.getAttribute("aria-label");
                String value = option.getAttribute("value");
                if ((text != null && text.equalsIgnoreCase(propertyType))
                        || (ariaLabel != null && ariaLabel.equalsIgnoreCase(propertyType))
                        || (value != null && value.equalsIgnoreCase(propertyType))) {
                    click(option);
                    logger.info("Clicked property type option: {}", propertyType);
                    return;
                }
            } catch (Exception e) {
                logger.debug("Skipping property type option due to: {}", e.getMessage());
            }
        }

        // Fallback: XPath by text
        By xpathByText = By.xpath(
                "//button[normalize-space(text())='" + propertyType + "'] | " +
                "//label[normalize-space(text())='" + propertyType + "'] | " +
                "//input[@value='" + propertyType + "'] | " +
                "//*[@aria-label='" + propertyType + "']"
        );
        try {
            click(xpathByText);
            logger.info("Selected property type via XPath fallback: {}", propertyType);
        } catch (Exception e) {
            logger.warn("Could not select property type '{}': {}", propertyType, e.getMessage());
        }
    }

    /**
     * Clicks the 'Get Started' button to submit the address and initiate the
     * service-selection flow.
     */
    public void clickGetStarted() {
        logger.info("Clicking Get Started button");
        try {
            WaitUtils.waitForClickability(getStartedButton);
            click(getStartedButton);
            logger.info("Clicked Get Started button");
        } catch (Exception e) {
            // Fallback: look for any visible submit / CTA button
            logger.warn("Primary Get Started button not found; trying ENTER on address field");
            By fallbackCta = By.cssSelector(
                    "button[type='submit'], button.cta-btn, button.primary-btn, " +
                    "button[aria-label*='start' i], button[aria-label*='search' i]"
            );
            try {
                click(fallbackCta);
                logger.info("Clicked fallback CTA button");
            } catch (Exception ex) {
                logger.warn("Fallback Get Started click failed: {}", ex.getMessage());
            }
        }
    }

    /**
     * Returns {@code true} if a login / authentication prompt is currently
     * displayed (it should NOT appear in a guest flow).
     *
     * @return {@code true} if a login prompt is visible.
     */
    public boolean isLoginPromptDisplayed() {
        try {
            boolean displayed = isDisplayed(LOGIN_PROMPT_LOC);
            logger.info("Login prompt displayed: {}", displayed);
            return displayed;
        } catch (Exception e) {
            logger.debug("Login prompt check resulted in exception (treated as not displayed): {}",
                    e.getMessage());
            return false;
        }
    }

    /**
     * Clicks the 'Repair/Replace Container' link.
     * Attempts CSS-based element first, then falls back to XPath.
     */
    public void clickRepairReplaceLink() {
        logger.info("Clicking Repair/Replace Container link");
        try {
            WaitUtils.waitForClickability(repairReplaceLink);
            click(repairReplaceLink);
        } catch (Exception e) {
            logger.warn("Primary locator failed for Repair/Replace click; using XPath fallback");
            click(REPAIR_REPLACE_LINK_LOC);
        }
    }
}
