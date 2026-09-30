package com.wm.automation.stepdefinitions;

import com.wm.automation.base.DriverFactory;
import com.wm.automation.base.ScenarioContext;
import com.wm.automation.pages.LoginPage;
import com.wm.automation.pages.NavigationPage;
import com.wm.automation.reporting.ReportLogger;
import com.wm.automation.utils.AssertionUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Step definitions for the login_fails feature (TC-002).
 *
 * Covers:
 *  - Navigating to the login page via the nav bar Sign In button
 *  - Entering invalid credentials
 *  - Verifying the error message is displayed
 *  - Verifying the user remains on the login page after a failed attempt
 *
 * Page objects used (lazy-initialised, never in constructors):
 *   LoginPage     – email, password, sign-in button, error message
 *   NavigationPage – Sign In link in header
 *
 * ScenarioContext is injected via PicoContainer (shared with other step-def classes).
 */
public class LoginFailsSteps {

    private static final Logger logger = LogManager.getLogger(LoginFailsSteps.class);

    // ── Context keys ──────────────────────────────────────────────────────────
    private static final String CTX_INVALID_EMAIL    = "INVALID_EMAIL";
    private static final String CTX_INVALID_PASSWORD = "INVALID_PASSWORD";

    // ── PicoContainer DI ──────────────────────────────────────────────────────
    private final ScenarioContext context;

    public LoginFailsSteps(ScenarioContext context) {
        this.context = context;
    }

    // ── Lazy page-object accessors ─────────────────────────────────────────────
    private LoginPage     loginPage;
    private NavigationPage navigationPage;

    private LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    private NavigationPage navigationPage() {
        if (navigationPage == null) {
            navigationPage = new NavigationPage();
        }
        return navigationPage;
    }

    // =========================================================================
    // Given steps
    // =========================================================================

    /**
     * Verifies the Sign In link is visible in the nav bar.
     * Reuses NavigationPage which already knows about the sign-in link locator.
     */
    @Given("the Sign In link is visible in the navigation bar")
    public void theSignInLinkIsVisibleInTheNavigationBar() {
        boolean visible = navigationPage().isSignInLinkVisible();
        ReportLogger.info("Sign In link visible in nav bar: " + visible);
        AssertionUtils.assertTrue(visible,
                "Sign In link should be visible in the navigation bar on the home page");
    }

    // =========================================================================
    // When steps
    // =========================================================================

    /**
     * Clicks the Sign In link in the persistent header/nav bar.
     * Navigates to the WM login page.
     */
    @When("the user clicks the Sign In button from the nav bar")
    public void theUserClicksTheSignInButtonFromTheNavBar() {
        ReportLogger.info("Clicking Sign In link from the navigation bar");
        navigationPage().clickSignIn();
        logger.info("Clicked Sign In link from nav bar");
    }

    /**
     * Enters a specific email into the email field on the login page.
     * Stores the value in scenario context for later assertions.
     */
    @When("the user enters email {string} in the email field")
    public void theUserEntersEmailInTheEmailField(String email) {
        ReportLogger.info("Entering email: " + email);
        loginPage().enterEmail(email);
        context.set(CTX_INVALID_EMAIL, email);
        logger.info("Entered email: {}", email);
    }

    /**
     * Enters a specific password into the password field on the login page.
     * Stores the value in scenario context for later assertions.
     */
    @When("the user enters password {string} in the password field")
    public void theUserEntersPasswordInThePasswordField(String password) {
        ReportLogger.info("Entering password: [hidden]");
        loginPage().enterPassword(password);
        context.set(CTX_INVALID_PASSWORD, password);
        logger.info("Entered password (masked)");
    }

    /**
     * Clicks the Sign In submit button on the login form.
     */
    @When("the user clicks the Sign In submit button")
    public void theUserClicksTheSignInSubmitButton() {
        ReportLogger.info("Clicking Sign In submit button");
        loginPage().clickSignIn();
        logger.info("Clicked Sign In submit button");
    }

    // =========================================================================
    // Then / And steps
    // =========================================================================

    /**
     * Asserts that the login form is fully displayed with all three required
     * elements visible: email field, password field, and sign-in button.
     */
    @Then("the login form should be fully visible with email, password and sign-in button")
    public void theLoginFormShouldBeFullyVisibleWithEmailPasswordAndSignInButton() {
        boolean emailVisible    = loginPage().isEmailFieldVisible();
        boolean passwordVisible = loginPage().isPasswordFieldVisible();
        boolean buttonEnabled   = loginPage().isSignInButtonEnabled();

        ReportLogger.info("Login form – email visible: " + emailVisible
                + ", password visible: " + passwordVisible
                + ", sign-in button enabled: " + buttonEnabled);

        AssertionUtils.assertTrue(emailVisible,
                "Email field should be visible on the login form");
        AssertionUtils.assertTrue(passwordVisible,
                "Password field should be visible on the login form");
        AssertionUtils.assertTrue(buttonEnabled,
                "Sign In button should be enabled on the login form");
    }

    /**
     * Asserts that no inline validation error appears on the email field immediately
     * after the user types — only checked before form submission.
     */
    @Then("no inline validation error should appear on the email field")
    public void noInlineValidationErrorShouldAppearOnTheEmailField() {
        // Check that the generic error container is NOT yet visible (pre-submit)
        boolean errorVisible = loginPage().isErrorMessageDisplayed();
        ReportLogger.info("Inline email validation error visible before submit: " + errorVisible);
        // We expect NO error before form submission
        AssertionUtils.assertFalse(errorVisible,
                "No inline validation error should be visible on the email field before form submission");
    }

    /**
     * Asserts that no inline validation error appears on the password field immediately
     * after the user types — only checked before form submission.
     */
    @Then("no inline validation error should appear on the password field")
    public void noInlineValidationErrorShouldAppearOnThePasswordField() {
        boolean errorVisible = loginPage().isErrorMessageDisplayed();
        ReportLogger.info("Inline password validation error visible before submit: " + errorVisible);
        AssertionUtils.assertFalse(errorVisible,
                "No inline validation error should be visible on the password field before form submission");
    }

    /**
     * Verifies that a specific error message text is displayed after a failed login.
     * Uses a contains-ignore-case check to be resilient to minor copy changes.
     */
    @Then("the login error message {string} should be displayed")
    public void theLoginErrorMessageShouldBeDisplayed(String expectedMessage) {
        boolean errorDisplayed = loginPage().isErrorMessageDisplayed();
        ReportLogger.info("Login error displayed: " + errorDisplayed);
        AssertionUtils.assertTrue(errorDisplayed,
                "A login error message should be displayed after submitting invalid credentials");

        String actualMessage = loginPage().getErrorMessageText();
        ReportLogger.info("Actual error message: " + actualMessage);
        ReportLogger.info("Expected error message: " + expectedMessage);

        AssertionUtils.assertContainsIgnoreCase(actualMessage, expectedMessage,
                "The error message should contain: " + expectedMessage);
        logger.info("Login error message verified: '{}'", actualMessage);
    }

    /**
     * Verifies that a login failure error message is displayed (generic variant
     * used in the Outline scenario where exact text is not asserted).
     */
    @Then("a login failure error message should be displayed")
    public void aLoginFailureErrorMessageShouldBeDisplayed() {
        boolean errorDisplayed = loginPage().isErrorMessageDisplayed();
        ReportLogger.info("Login failure error message displayed: " + errorDisplayed);
        AssertionUtils.assertTrue(errorDisplayed,
                "A login failure error message should be displayed after invalid credentials");

        String actualMessage = loginPage().getErrorMessageText();
        ReportLogger.info("Error message text: " + actualMessage);
        logger.info("Login failure error message: '{}'", actualMessage);
    }

    /**
     * Verifies the user has NOT been redirected away from the login page —
     * i.e. the login form is still loaded and the URL still contains the login path.
     */
    @Then("the user should remain on the login page")
    public void theUserShouldRemainOnTheLoginPage() {
        boolean loginPageLoaded = loginPage().isPageLoaded();
        String  currentUrl      = loginPage().getCurrentUrl();

        ReportLogger.info("Current URL after failed login: " + currentUrl);
        ReportLogger.info("Login page still loaded: " + loginPageLoaded);

        AssertionUtils.assertTrue(loginPageLoaded,
                "User should remain on the login page after a failed authentication attempt");

        // The URL should NOT redirect back to the WM home page root without /login
        AssertionUtils.assertFalse(
                currentUrl != null && currentUrl.matches("https?://[^/]+/?$"),
                "User should NOT have been redirected to the home page after a failed login");

        logger.info("Confirmed user remains on login page. URL: {}", currentUrl);
    }

    /**
     * Verifies the email input field is still visible on the login page —
     * confirms the page has not been replaced by a post-login dashboard.
     */
    @Then("the email field should still be visible on the login page")
    public void theEmailFieldShouldStillBeVisibleOnTheLoginPage() {
        boolean visible = loginPage().isEmailFieldVisible();
        ReportLogger.info("Email field still visible after failed login: " + visible);
        AssertionUtils.assertTrue(visible,
                "Email field should still be visible on the login page after a failed login attempt");
    }

    /**
     * Verifies the password input field is still visible on the login page.
     */
    @Then("the password field should still be visible on the login page")
    public void thePasswordFieldShouldStillBeVisibleOnTheLoginPage() {
        boolean visible = loginPage().isPasswordFieldVisible();
        ReportLogger.info("Password field still visible after failed login: " + visible);
        AssertionUtils.assertTrue(visible,
                "Password field should still be visible on the login page after a failed login attempt");
    }

    /**
     * Verifies the Sign In button is still visible (and enabled) on the login page.
     */
    @Then("the sign-in button should still be visible on the login page")
    public void theSignInButtonShouldStillBeVisibleOnTheLoginPage() {
        boolean enabled = loginPage().isSignInButtonEnabled();
        ReportLogger.info("Sign In button still enabled after failed login: " + enabled);
        AssertionUtils.assertTrue(enabled,
                "Sign In button should still be visible and enabled on the login page after a failed login attempt");
    }
}
