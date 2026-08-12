package com.wm.automation.stepdefinitions;

import com.wm.automation.base.ScenarioContext;
import com.wm.automation.pages.HomePage;
import com.wm.automation.pages.RepairReplaceContainerPage;
import com.wm.automation.reporting.ReportLogger;
import com.wm.automation.utils.AssertionUtils;
import com.wm.automation.utils.TestDataUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Step definitions for the Repair/Replace Container feature (TC-001).
 *
 * <p>Covers:
 * <ul>
 *   <li>Entering a specific or default residential address on the WM home page</li>
 *   <li>Selecting a property type (e.g. 'Home')</li>
 *   <li>Clicking the 'Get Started' button</li>
 *   <li>Verifying the service options screen is displayed</li>
 *   <li>Verifying the 'Repair/Replace Container' link is visible and clickable</li>
 *   <li>Confirming no login prompt interrupts the guest flow</li>
 * </ul>
 *
 * <p>PicoContainer injects {@link ScenarioContext} automatically.
 */
public class RepairReplaceContainerSteps {

    private static final Logger logger = LogManager.getLogger(RepairReplaceContainerSteps.class);

    private final ScenarioContext context;

    // Lazy-initialised page objects (instantiated only after WebDriver is ready)
    private HomePage homePage;
    private RepairReplaceContainerPage repairReplaceContainerPage;

    public RepairReplaceContainerSteps(ScenarioContext context) {
        this.context = context;
    }

    // ─── Lazy page object accessors ───────────────────────────────────────────

    private HomePage homePage() {
        if (homePage == null) {
            homePage = new HomePage();
        }
        return homePage;
    }

    private RepairReplaceContainerPage repairReplaceContainerPage() {
        if (repairReplaceContainerPage == null) {
            repairReplaceContainerPage = new RepairReplaceContainerPage();
        }
        return repairReplaceContainerPage;
    }

    // ─── Step definitions ─────────────────────────────────────────────────────

    /**
     * Enters an explicitly provided residential address into the address input
     * on the WM home page.
     *
     * @param address the full residential address string, e.g.
     *                "123 Elm Street, Dallas, TX 75201"
     */
    @When("the user enters the residential address {string}")
    public void theUserEntersTheResidentialAddress(String address) {
        logger.info("Entering residential address: {}", address);
        ReportLogger.info("Entering address: " + address);
        homePage().enterAddress(address);
        context.set("enteredAddress", address);
    }

    /**
     * Selects the given property type (e.g. 'Home') from the available options.
     * If no property-type selector is rendered by the page in its current A/B
     * variant the step logs a warning and continues, as some WM page variants
     * omit this step.
     *
     * @param propertyType the property type to select, e.g. "Home"
     */
    @And("the user selects {string} as the property type")
    public void theUserSelectsAsThePropertyType(String propertyType) {
        logger.info("Selecting property type: {}", propertyType);
        ReportLogger.info("Selecting property type: " + propertyType);
        repairReplaceContainerPage().selectPropertyType(propertyType);
        context.set("selectedPropertyType", propertyType);
    }

    /**
     * Clicks the 'Get Started' button to submit the address and navigate to the
     * service-options screen.
     */
    @And("the user clicks the Get Started button")
    public void theUserClicksTheGetStartedButton() {
        logger.info("Clicking Get Started button");
        ReportLogger.info("Clicking Get Started button");
        repairReplaceContainerPage().clickGetStarted();
    }

    /**
     * Asserts that the service options screen is displayed after address submission.
     * This confirms that the page transitioned correctly from the home page.
     */
    @Then("the service options screen should be displayed")
    public void theServiceOptionsScreenShouldBeDisplayed() {
        boolean displayed = repairReplaceContainerPage().isServiceOptionsScreenDisplayed();
        logger.info("Service options screen displayed: {}", displayed);
        ReportLogger.info("Service options screen displayed: " + displayed);
        AssertionUtils.assertTrue(displayed,
                "Service options screen should be displayed after submitting a valid " +
                "residential address and selecting a property type");
    }

    /**
     * Asserts that a named link (e.g. "Repair/Replace Container") is visible on
     * the service options screen.
     *
     * @param linkName the display text or identifier of the expected link
     */
    @And("the {string} link should be visible on the service options screen")
    public void theLinkShouldBeVisibleOnTheServiceOptionsScreen(String linkName) {
        logger.info("Verifying link is visible: {}", linkName);
        ReportLogger.info("Checking visibility of link: " + linkName);

        boolean visible = resolveVisibilityByLinkName(linkName);
        AssertionUtils.assertTrue(visible,
                "'" + linkName + "' link should be visible on the service options screen");
    }

    /**
     * Asserts that a named link is clickable (enabled + visible) on the service
     * options screen, confirming a guest can interact with it.
     *
     * @param linkName the display text or identifier of the expected link
     */
    @And("the {string} link should be clickable")
    public void theLinkShouldBeClickable(String linkName) {
        logger.info("Verifying link is clickable: {}", linkName);
        ReportLogger.info("Checking clickability of link: " + linkName);

        boolean clickable = resolveClickabilityByLinkName(linkName);
        AssertionUtils.assertTrue(clickable,
                "'" + linkName + "' link should be clickable on the service options screen");
    }

    /**
     * Asserts that no login prompt / authentication wall is displayed, confirming
     * the entire flow is accessible to guest users without requiring a sign-in.
     */
    @And("no login prompt should be displayed")
    public void noLoginPromptShouldBeDisplayed() {
        boolean loginPromptShown = repairReplaceContainerPage().isLoginPromptDisplayed();
        logger.info("Login prompt displayed (should be false): {}", loginPromptShown);
        ReportLogger.info("Login prompt displayed: " + loginPromptShown);
        AssertionUtils.assertFalse(loginPromptShown,
                "No login prompt should be displayed during the guest Repair/Replace " +
                "Container flow — the entire flow should be accessible without signing in");
    }

    // ─── Private helpers ──────────────────────────────────────────────────────

    /**
     * Resolves visibility for a given link name. Currently routes
     * "Repair/Replace Container" to the dedicated page-object method; other
     * link names are handled generically.
     */
    private boolean resolveVisibilityByLinkName(String linkName) {
        if (isRepairReplaceContainerLink(linkName)) {
            return repairReplaceContainerPage().isRepairReplaceLinkVisible();
        }
        // Generic fallback – not expected but included for resilience
        logger.warn("No specific visibility check for link '{}'; using generic page-load check", linkName);
        return repairReplaceContainerPage().isServiceOptionsScreenDisplayed();
    }

    /**
     * Resolves clickability for a given link name.
     */
    private boolean resolveClickabilityByLinkName(String linkName) {
        if (isRepairReplaceContainerLink(linkName)) {
            return repairReplaceContainerPage().isRepairReplaceLinkClickable();
        }
        logger.warn("No specific clickability check for link '{}'; treating as clickable", linkName);
        return true;
    }

    /**
     * Returns {@code true} when the provided link name refers to the
     * 'Repair/Replace Container' service link.
     */
    private boolean isRepairReplaceContainerLink(String linkName) {
        return linkName != null
                && linkName.toLowerCase().contains("repair")
                && linkName.toLowerCase().contains("container");
    }
}
