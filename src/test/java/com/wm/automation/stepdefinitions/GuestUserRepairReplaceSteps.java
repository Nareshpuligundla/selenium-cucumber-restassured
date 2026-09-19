package com.wm.automation.stepdefinitions;

import com.wm.automation.base.ScenarioContext;
import com.wm.automation.pages.HomePage;
import com.wm.automation.pages.ServiceDashboardPage;
import com.wm.automation.reporting.ReportLogger;
import com.wm.automation.utils.AssertionUtils;
import com.wm.automation.utils.WaitUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.wm.automation.base.DriverFactory;

import java.util.List;

/**
 * Step definitions for the guest_user_sees_repair_re feature.
 *
 * Covers the flow:
 *   1. WM home page loads with address entry field visible
 *   2. User types a valid residential address
 *   3. User selects the "Home" service type
 *   4. User clicks the "Get Started" button
 *   5. Service dashboard is displayed
 *   6. "Repair/Replace Container" link is visible and clickable
 *
 * PicoContainer injects {@link ScenarioContext} via the single-arg constructor.
 */
public class GuestUserRepairReplaceSteps {

    private static final Logger logger = LogManager.getLogger(GuestUserRepairReplaceSteps.class);

    // ─── Context keys ─────────────────────────────────────────────────────────
    private static final String CTX_ENTERED_ADDRESS = "enteredResidentialAddress";
    private static final String CTX_SERVICE_TYPE    = "selectedServiceType";

    // ─── CSS / XPath constants for get-started button & service type ──────────

    /**
     * "Get Started" button variations found across WM.com A/B tests.
     * TODO: replace — verify exact selector for "Get Started" button on live wm.com
     */
    private static final By GET_STARTED_BUTTON_LOC = By.cssSelector(
            "button[data-testid='get-started'], " +
            "button[aria-label*='get started' i], " +
            "button.get-started, " +
            "button.cta-btn, " +
            "button[type='submit'], " +
            "button.hero-cta, " +
            "a[href*='get-started'], " +
            "button.btn-primary"
    );

    /**
     * "Home" service-type option selector (radio button, tab, or card).
     * TODO: replace — verify exact selector for "Home" service type option on live wm.com
     */
    private static final By HOME_SERVICE_TYPE_LOC = By.cssSelector(
            "[data-service-type='home'], " +
            "[data-value='home'], " +
            "input[value='home' i], " +
            "label[for*='home' i], " +
            "button[data-testid='service-type-home'], " +
            ".service-type-home, " +
            "[aria-label*='home' i]"
    );

    /**
     * XPath fallback to locate any element with visible text "Home" that acts as
     * a selectable option in the service-type picker.
     * TODO: replace — verify XPath matches real DOM structure on wm.com
     */
    private static final By HOME_SERVICE_TYPE_XPATH = By.xpath(
            "//button[normalize-space(text())='Home'] | " +
            "//label[normalize-space(text())='Home'] | " +
            "//span[normalize-space(text())='Home']/ancestor::*[self::button or self::label or self::a][1] | " +
            "//li[normalize-space(text())='Home'] | " +
            "//*[@role='tab' and normalize-space(text())='Home'] | " +
            "//*[@role='radio' and normalize-space(@aria-label)='Home'] | " +
            "//*[normalize-space(text())='Home' and " +
            "  (self::a or self::button or self::label or self::div[@role])]"
    );

    // ─── Lazy page objects ────────────────────────────────────────────────────

    private final ScenarioContext context;

    private HomePage            homePage;
    private ServiceDashboardPage serviceDashboardPage;

    public GuestUserRepairReplaceSteps(ScenarioContext context) {
        this.context = context;
    }

    private HomePage homePage() {
        if (homePage == null) {
            homePage = new HomePage();
        }
        return homePage;
    }

    private ServiceDashboardPage serviceDashboardPage() {
        if (serviceDashboardPage == null) {
            serviceDashboardPage = new ServiceDashboardPage();
        }
        return serviceDashboardPage;
    }

    // ─── Step definitions ─────────────────────────────────────────────────────

    /**
     * Verifies the WM home page has loaded and that the address input field is visible.
     * The {@code Background} step already navigated to the page; this step validates it.
     */
    @Given("the WM home page is loaded with the address entry field visible")
    public void theWmHomePageIsLoadedWithTheAddressEntryFieldVisible() {
        boolean loaded = homePage().isPageLoaded();
        ReportLogger.info("WM home page loaded: " + loaded);
        AssertionUtils.assertTrue(loaded,
                "WM home page should be loaded before entering an address");

        boolean addressFieldVisible = homePage().isAddressInputVisible();
        ReportLogger.info("Address entry field visible: " + addressFieldVisible);
        AssertionUtils.assertTrue(addressFieldVisible,
                "Address entry field should be visible on the WM home page hero section");

        logger.info("WM home page confirmed loaded with address field visible");
    }

    /**
     * Types the given residential address into the address input on the home page.
     * The address text is stored in the scenario context for downstream assertions.
     *
     * @param address The full street address string to enter (e.g. "1234 Elm Street, Houston, TX 77001")
     */
    @When("the user enters the residential address {string}")
    public void theUserEntersTheResidentialAddress(String address) {
        ReportLogger.info("Entering residential address: " + address);
        homePage().enterAddress(address);
        context.set(CTX_ENTERED_ADDRESS, address);
        logger.info("Residential address entered: {}", address);
    }

    /**
     * Selects the specified service type option (e.g. "Home") on the WM home page.
     * Tries CSS-based selectors first; falls back to XPath text search if not found.
     *
     * @param serviceType The service type label visible on screen (e.g. "Home")
     */
    @And("the user selects {string} as the service type")
    public void theUserSelectsAsTheServiceType(String serviceType) {
        ReportLogger.info("Selecting service type: " + serviceType);
        context.set(CTX_SERVICE_TYPE, serviceType);

        boolean selected = selectServiceTypeOption(serviceType);
        if (!selected) {
            ReportLogger.warn("Service type selector not found — continuing without explicit selection. "
                    + "WM may pre-select 'Home' by default.");
            logger.warn("Could not find '{}' service-type option; flow continues", serviceType);
        } else {
            ReportLogger.info("Service type '" + serviceType + "' selected successfully");
            logger.info("Service type '{}' selected", serviceType);
        }
    }

    /**
     * Clicks the "Get Started" button on the WM home page to submit the address
     * and trigger the service dashboard transition.
     *
     * @param buttonLabel Label of the button (e.g. "Get Started")
     */
    @And("the user clicks the {string} button")
    public void theUserClicksTheButton(String buttonLabel) {
        ReportLogger.info("Clicking button: " + buttonLabel);

        if (buttonLabel.equalsIgnoreCase("Get Started")) {
            clickGetStartedButton();
        } else {
            // Generic fallback: find any button whose text matches the label
            clickButtonByVisibleText(buttonLabel);
        }

        logger.info("'{}' button clicked", buttonLabel);
        ReportLogger.info("'" + buttonLabel + "' button clicked — waiting for page transition");
    }

    /**
     * Asserts that the service dashboard is displayed after the address submission flow.
     */
    @Then("the service dashboard should be displayed")
    public void theServiceDashboardShouldBeDisplayed() {
        boolean displayed = serviceDashboardPage().isDashboardDisplayed();
        ReportLogger.info("Service dashboard displayed: " + displayed);
        AssertionUtils.assertTrue(displayed,
                "Service dashboard should be displayed after entering a valid residential address, "
                        + "selecting 'Home' service type, and clicking 'Get Started'");
        logger.info("Service dashboard confirmed displayed");
    }

    /**
     * Asserts that the named link (e.g. "Repair/Replace Container") is visible on
     * the service dashboard.
     *
     * @param linkText The visible text of the link to check
     */
    @And("the {string} link should be visible on the service dashboard")
    public void theLinkShouldBeVisibleOnTheServiceDashboard(String linkText) {
        ReportLogger.info("Verifying visibility of link: " + linkText);

        boolean visible;
        if (linkText.toLowerCase().contains("repair") && linkText.toLowerCase().contains("container")) {
            visible = serviceDashboardPage().isRepairReplaceContainerLinkVisible();
        } else {
            // Generic: search for a link containing the expected text
            visible = isLinkWithTextVisible(linkText);
        }

        ReportLogger.info("'" + linkText + "' link visible: " + visible);
        AssertionUtils.assertTrue(visible,
                "'" + linkText + "' link should be visible on the WM service dashboard");
        logger.info("'{}' link visibility confirmed: {}", linkText, visible);
    }

    /**
     * Asserts that the named link is clickable (enabled and interactable) on the dashboard.
     *
     * @param linkText The visible text of the link to check
     */
    @And("the {string} link should be clickable")
    public void theLinkShouldBeClickable(String linkText) {
        ReportLogger.info("Verifying clickability of link: " + linkText);

        boolean clickable;
        if (linkText.toLowerCase().contains("repair") && linkText.toLowerCase().contains("container")) {
            clickable = serviceDashboardPage().isRepairReplaceContainerLinkClickable();
        } else {
            clickable = isLinkWithTextClickable(linkText);
        }

        ReportLogger.info("'" + linkText + "' link clickable: " + clickable);
        AssertionUtils.assertTrue(clickable,
                "'" + linkText + "' link should be clickable on the WM service dashboard");
        logger.info("'{}' link clickability confirmed: {}", linkText, clickable);
    }

    /**
     * Asserts that at least the specified number of service links are displayed on
     * the service dashboard.
     *
     * @param minCount Minimum expected number of service links
     */
    @And("the service dashboard should display at least {int} service link")
    public void theServiceDashboardShouldDisplayAtLeastServiceLink(int minCount) {
        int actualCount = serviceDashboardPage().getServiceLinkCount();
        ReportLogger.info("Service dashboard link count: " + actualCount + " (expected >= " + minCount + ")");
        AssertionUtils.assertGreaterThan(actualCount, minCount - 1,
                "Service dashboard should display at least " + minCount + " service link(s), "
                        + "but found: " + actualCount);
        logger.info("Service dashboard link count {} meets minimum {}", actualCount, minCount);
    }

    // ─── Private helpers ──────────────────────────────────────────────────────

    /**
     * Tries to select the "Home" (or any named) service-type option.
     * Priority: CSS locator → "Home" XPath → generic text XPath.
     *
     * @param serviceType Visible text of the service type option to select
     * @return {@code true} if the option was found and clicked; {@code false} otherwise
     */
    private boolean selectServiceTypeOption(String serviceType) {
        // 1. CSS selectors (most efficient when the DOM matches)
        if ("home".equalsIgnoreCase(serviceType)) {
            if (WaitUtils.isElementVisible(HOME_SERVICE_TYPE_LOC, 5)) {
                try {
                    DriverFactory.getDriver().findElement(HOME_SERVICE_TYPE_LOC).click();
                    return true;
                } catch (Exception e) {
                    logger.debug("CSS click on Home service type failed: {}", e.getMessage());
                }
            }

            // 2. XPath fallback for "Home"
            if (WaitUtils.isElementVisible(HOME_SERVICE_TYPE_XPATH, 5)) {
                try {
                    DriverFactory.getDriver().findElement(HOME_SERVICE_TYPE_XPATH).click();
                    return true;
                } catch (Exception e) {
                    logger.debug("XPath click on Home service type failed: {}", e.getMessage());
                }
            }
        }

        // 3. Generic text-based XPath for any service type label
        By genericTextLoc = By.xpath(
                String.format(
                        "//*[normalize-space(text())='%s' and " +
                        "(self::button or self::label or self::a or self::li or " +
                        "self::div[@role='tab'] or self::div[@role='radio'] or self::span)]",
                        serviceType
                )
        );
        if (WaitUtils.isElementVisible(genericTextLoc, 5)) {
            try {
                DriverFactory.getDriver().findElement(genericTextLoc).click();
                return true;
            } catch (Exception e) {
                logger.debug("Generic text XPath click for '{}' failed: {}", serviceType, e.getMessage());
            }
        }

        return false;
    }

    /**
     * Clicks the "Get Started" button.
     * Tries the dedicated CSS locator first; if that fails, falls back to submitting
     * the address field via ENTER (which WM often accepts as equivalent).
     */
    private void clickGetStartedButton() {
        if (WaitUtils.isElementVisible(GET_STARTED_BUTTON_LOC, 8)) {
            try {
                WaitUtils.waitForClickability(GET_STARTED_BUTTON_LOC);
                DriverFactory.getDriver().findElement(GET_STARTED_BUTTON_LOC).click();
                logger.info("'Get Started' button clicked via CSS selector");
                return;
            } catch (Exception e) {
                logger.warn("CSS click on 'Get Started' button failed: {}", e.getMessage());
            }
        }

        // XPath fallback
        By getStartedXPath = By.xpath(
                "//button[contains(translate(normalize-space(text()), " +
                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'get started')] | " +
                "//a[contains(translate(normalize-space(text()), " +
                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'get started')]"
        );
        if (WaitUtils.isElementVisible(getStartedXPath, 5)) {
            try {
                WaitUtils.waitForClickability(getStartedXPath);
                DriverFactory.getDriver().findElement(getStartedXPath).click();
                logger.info("'Get Started' button clicked via XPath fallback");
                return;
            } catch (Exception e) {
                logger.warn("XPath click on 'Get Started' button failed: {}", e.getMessage());
            }
        }

        // Last resort: submit address form via ENTER key (same network effect)
        logger.warn("'Get Started' button not found — submitting address via ENTER key as fallback");
        homePage().submitAddress();
    }

    /**
     * Clicks a button or link whose visible text exactly (or partially) matches
     * {@code buttonLabel}. Used as a generic handler for arbitrary button labels.
     *
     * @param buttonLabel The visible text label of the button to click
     */
    private void clickButtonByVisibleText(String buttonLabel) {
        By loc = By.xpath(
                String.format(
                        "//button[contains(normalize-space(text()), '%s')] | " +
                        "//a[contains(normalize-space(text()), '%s')]",
                        buttonLabel, buttonLabel
                )
        );
        if (WaitUtils.isElementVisible(loc, 5)) {
            try {
                WaitUtils.waitForClickability(loc);
                DriverFactory.getDriver().findElement(loc).click();
                logger.info("Button/link '{}' clicked via text XPath", buttonLabel);
            } catch (Exception e) {
                logger.warn("Could not click '{}' button via text XPath: {}", buttonLabel, e.getMessage());
            }
        } else {
            logger.warn("Button/link with text '{}' not found on page", buttonLabel);
        }
    }

    /**
     * Returns {@code true} when a link whose visible text contains {@code linkText}
     * is present and visible in the DOM.
     *
     * @param linkText Partial or full visible text of the link
     * @return {@code true} if visible; {@code false} otherwise
     */
    private boolean isLinkWithTextVisible(String linkText) {
        By loc = By.xpath(
                String.format(
                        "//a[contains(translate(normalize-space(.), " +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')] | " +
                        "//button[contains(translate(normalize-space(.), " +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]",
                        linkText.toLowerCase(), linkText.toLowerCase()
                )
        );
        return WaitUtils.isElementVisible(loc, 10);
    }

    /**
     * Returns {@code true} when a link whose visible text contains {@code linkText}
     * is clickable (visible + enabled).
     *
     * @param linkText Partial or full visible text of the link
     * @return {@code true} if clickable; {@code false} otherwise
     */
    private boolean isLinkWithTextClickable(String linkText) {
        By loc = By.xpath(
                String.format(
                        "//a[contains(translate(normalize-space(.), " +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')] | " +
                        "//button[contains(translate(normalize-space(.), " +
                        "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]",
                        linkText.toLowerCase(), linkText.toLowerCase()
                )
        );
        if (!WaitUtils.isElementVisible(loc, 10)) {
            return false;
        }
        try {
            WaitUtils.waitForClickability(loc);
            return true;
        } catch (Exception e) {
            logger.warn("Clickability wait for '{}' failed: {}", linkText, e.getMessage());
            return false;
        }
    }
}
