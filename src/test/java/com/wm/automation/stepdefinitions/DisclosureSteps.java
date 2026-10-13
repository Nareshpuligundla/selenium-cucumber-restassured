package com.wm.automation.stepdefinitions;

import com.wm.automation.base.ScenarioContext;
import com.wm.automation.pages.ApplicationSummaryPage;
import com.wm.automation.pages.DashboardPage;
import com.wm.automation.pages.DisclosurePage;
import com.wm.automation.pages.LoginPage;
import com.wm.automation.reporting.ReportLogger;
import com.wm.automation.utils.AssertionUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Step definitions for TC-001, TC-002, TC-003 — Disclosure page flows.
 *
 * Page objects used (lazy-initialised per project convention):
 *   LoginPage            – email, password, sign-in
 *   DashboardPage        – in-progress application click
 *   DisclosurePage       – heading, panel, scroll, checkbox, continue, validation
 *   ApplicationSummaryPage – heading and disclosure-accepted indicator
 *
 * PicoContainer injects ScenarioContext via constructor.
 */
public class DisclosureSteps {

    private static final Logger logger = LogManager.getLogger(DisclosureSteps.class);

    private static final String PROXY_EMAIL    = "proxy.user@test.com";
    private static final String PROXY_PASSWORD = "ProxyPass1!";

    private final ScenarioContext context;

    private LoginPage            loginPage;
    private DashboardPage        dashboardPage;
    private DisclosurePage       disclosurePage;
    private ApplicationSummaryPage summaryPage;

    public DisclosureSteps(ScenarioContext context) {
        this.context = context;
    }

    // ── lazy accessors ────────────────────────────────────────────────────────

    private LoginPage loginPage() {
        if (loginPage == null) loginPage = new LoginPage();
        return loginPage;
    }

    private DashboardPage dashboardPage() {
        if (dashboardPage == null) dashboardPage = new DashboardPage();
        return dashboardPage;
    }

    private DisclosurePage disclosurePage() {
        if (disclosurePage == null) disclosurePage = new DisclosurePage();
        return disclosurePage;
    }

    private ApplicationSummaryPage summaryPage() {
        if (summaryPage == null) summaryPage = new ApplicationSummaryPage();
        return summaryPage;
    }

    // =========================================================================
    // Login / authentication steps (proxy applicant)
    // =========================================================================

    @Given("the proxy applicant is on the login page")
    public void theProxyApplicantIsOnTheLoginPage() {
        loginPage().open();
        AssertionUtils.assertTrue(loginPage().isPageLoaded(),
                "Login page should load with email, password fields and Sign In button visible");
        ReportLogger.info("Login page loaded for proxy applicant flow");
    }

    @When("the proxy applicant enters their email and password")
    public void theProxyApplicantEntersTheirEmailAndPassword() {
        loginPage().enterEmail(PROXY_EMAIL);
        loginPage().enterPassword(PROXY_PASSWORD);
        ReportLogger.info("Entered proxy applicant credentials – email: " + PROXY_EMAIL);
    }

    @When("the proxy applicant clicks the Sign In button")
    public void theProxyApplicantClicksTheSignInButton() {
        loginPage().clickSignIn();
        ReportLogger.info("Clicked Sign In button");
        logger.info("Proxy applicant clicked Sign In");
    }

    @Then("the proxy applicant should be redirected to the dashboard")
    public void theProxyApplicantShouldBeRedirectedToTheDashboard() {
        AssertionUtils.assertTrue(dashboardPage().isPageLoaded(),
                "Dashboard page should load after successful proxy applicant login");
        ReportLogger.pass("Proxy applicant redirected to /dashboard");
        logger.info("Dashboard page loaded successfully");
    }

    // =========================================================================
    // Dashboard → Disclosure navigation
    // =========================================================================

    @When("the proxy applicant clicks the in-progress application to resume it")
    public void theProxyApplicantClicksTheInProgressApplicationToResumeIt() {
        dashboardPage().clickInProgressApplication();
        ReportLogger.info("Clicked in-progress application on dashboard");
        logger.info("Clicked in-progress application link");
    }

    @Then("the Disclosure page should be displayed")
    public void theDisclosurePageShouldBeDisplayed() {
        AssertionUtils.assertTrue(disclosurePage().isPageLoaded(),
                "Disclosure page should load at /application/disclosure");
        ReportLogger.pass("Disclosure page loaded");
        logger.info("Disclosure page is displayed");
    }

    // =========================================================================
    // Disclosure page content assertions (TC-001)
    // =========================================================================

    @Then("the page heading should read exactly {string}")
    public void thePageHeadingShouldReadExactly(String expectedHeading) {
        String actualHeading = disclosurePage().getPageHeading();
        ReportLogger.info("Disclosure page heading: '" + actualHeading + "'");
        AssertionUtils.assertEquals(actualHeading, expectedHeading,
                "Page heading must read exactly \"" + expectedHeading + "\" in an <h1> element");
        logger.info("Page heading verified: '{}'", actualHeading);
    }

    @Then("the disclosure body text should be visible in a scrollable panel")
    public void theDisclosureBodyTextShouldBeVisibleInAScrollablePanel() {
        AssertionUtils.assertTrue(disclosurePage().isDisclosurePanelVisible(),
                "Disclosure text panel should be visible and readable");
        AssertionUtils.assertTrue(disclosurePage().isDisclosurePanelScrollable(),
                "Disclosure panel should have a vertical scrollbar (overflow-y: scroll or auto)");
        ReportLogger.pass("Disclosure panel is visible and scrollable");
        logger.info("Disclosure panel visibility and scrollability confirmed");
    }

    @When("the proxy applicant scrolls to the bottom of the disclosure text panel")
    public void theProxyApplicantScrollsToTheBottomOfTheDisclosureTextPanel() {
        disclosurePage().scrollDisclosurePanelToBottom();
        ReportLogger.info("Scrolled disclosure panel to the bottom");
        logger.info("Scrolled disclosure text panel to bottom");
    }

    @Then("the acceptance checkbox should become enabled")
    public void theAcceptanceCheckboxShouldBecomeEnabled() {
        AssertionUtils.assertTrue(disclosurePage().isAcceptCheckboxEnabled(),
                "Acceptance checkbox should be enabled after scrolling to the bottom of the disclosure panel");
        ReportLogger.pass("Acceptance checkbox is enabled after scroll");
        logger.info("Acceptance checkbox enabled after scrolling to bottom");
    }

    // =========================================================================
    // Acceptance + Continue flow (TC-002)
    // =========================================================================

    @When("the proxy applicant clicks the acceptance checkbox")
    public void theProxyApplicantClicksTheAcceptanceCheckbox() {
        disclosurePage().clickAcceptCheckbox();
        ReportLogger.info("Clicked 'I have read and agree to the disclosure' checkbox");
        logger.info("Clicked acceptance checkbox");
    }

    @Then("the acceptance checkbox should be checked")
    public void theAcceptanceCheckboxShouldBeChecked() {
        AssertionUtils.assertTrue(disclosurePage().isAcceptCheckboxChecked(),
                "Acceptance checkbox should display a checkmark after being clicked");
        ReportLogger.pass("Acceptance checkbox is in checked state");
    }

    @Then("the Continue button should become enabled")
    public void theContinueButtonShouldBecomeEnabled() {
        AssertionUtils.assertTrue(disclosurePage().isContinueButtonEnabled(),
                "Continue button should be enabled after the acceptance checkbox is checked");
        ReportLogger.pass("Continue button is enabled");
        logger.info("Continue button enabled after checkbox check");
    }

    @When("the proxy applicant clicks the Continue button")
    public void theProxyApplicantClicksTheContinueButton() {
        disclosurePage().clickContinueButton();
        ReportLogger.info("Clicked Continue button on Disclosure page");
        logger.info("Clicked Continue button");
    }

    @Then("the application summary page should be displayed")
    public void theApplicationSummaryPageShouldBeDisplayed() {
        AssertionUtils.assertTrue(summaryPage().isPageLoaded(),
                "Application Summary page should load after clicking Continue");
        ReportLogger.pass("Application Summary page loaded at /application/summary");
        logger.info("Application Summary page is displayed");
    }

    @Then("the summary page heading should show {string}")
    public void theSummaryPageHeadingShouldShow(String expectedHeading) {
        String actualHeading = summaryPage().getPageHeading();
        ReportLogger.info("Summary page heading: '" + actualHeading + "'");
        AssertionUtils.assertContainsIgnoreCase(actualHeading, expectedHeading,
                "Summary page heading should contain: " + expectedHeading);
        logger.info("Summary page heading verified: '{}'", actualHeading);
    }

    @Then("the disclosure accepted indicator should be displayed")
    public void theDisclosureAcceptedIndicatorShouldBeDisplayed() {
        AssertionUtils.assertTrue(summaryPage().isDisclosureAcceptedIndicatorDisplayed(),
                "A 'Disclosure Accepted' status indicator should be shown on the Application Summary page");
        ReportLogger.pass("Disclosure Accepted indicator is visible on summary page");
        logger.info("Disclosure accepted indicator confirmed on summary page");
    }

    // =========================================================================
    // Disabled-state assertions (TC-003 — negative / boundary-scroll)
    // =========================================================================

    @Then("the acceptance checkbox should be disabled")
    public void theAcceptanceCheckboxShouldBeDisabled() {
        AssertionUtils.assertTrue(disclosurePage().isAcceptCheckboxDisabled(),
                "Acceptance checkbox should be disabled (aria-disabled=\"true\") before the user scrolls to the bottom");
        ReportLogger.pass("Acceptance checkbox is disabled before scrolling");
        logger.info("Confirmed acceptance checkbox is disabled");
    }

    @Then("the Continue button should be disabled")
    public void theContinueButtonShouldBeDisabled() {
        AssertionUtils.assertTrue(disclosurePage().isContinueButtonDisabled(),
                "Continue button should be disabled before the user has scrolled to the bottom of the disclosure");
        ReportLogger.pass("Continue button is disabled before scrolling");
        logger.info("Confirmed Continue button is disabled");
    }

    @When("the proxy applicant attempts to click the Continue button without scrolling or accepting")
    public void theProxyApplicantAttemptsToClickTheContinueButtonWithoutScrollingOrAccepting() {
        disclosurePage().clickContinueButton();
        ReportLogger.info("Attempted to click Continue button before scrolling/accepting");
        logger.info("Attempted premature Continue button click");
    }

    @Then("the page should remain on the Disclosure page")
    public void thePageShouldRemainOnTheDisclosurePage() {
        String currentUrl = disclosurePage().getCurrentUrl();
        ReportLogger.info("Current URL after premature Continue click: " + currentUrl);
        AssertionUtils.assertTrue(currentUrl != null && currentUrl.contains("/application/disclosure"),
                "Page should remain on /application/disclosure after a premature Continue click");
        logger.info("URL confirmed as disclosure page: {}", currentUrl);
    }

    @Then("an inline validation message {string} should be displayed")
    public void anInlineValidationMessageShouldBeDisplayed(String expectedMessage) {
        AssertionUtils.assertTrue(disclosurePage().isValidationMessageDisplayed(),
                "An inline validation message should appear after clicking Continue prematurely");
        String actualMessage = disclosurePage().getValidationMessage();
        ReportLogger.info("Inline validation message: '" + actualMessage + "'");
        AssertionUtils.assertEquals(actualMessage, expectedMessage,
                "Inline validation message should read exactly: \"" + expectedMessage + "\"");
        logger.info("Inline validation message verified: '{}'", actualMessage);
    }
}
