package com.wm.automation.stepdefinitions;

import com.wm.automation.base.ScenarioContext;
import com.wm.automation.pages.ContactPage;
import com.wm.automation.pages.LoginPage;
import com.wm.automation.pages.MyAccountPage;
import com.wm.automation.reporting.ReportLogger;
import com.wm.automation.utils.AssertionUtils;
import com.wm.automation.utils.TestDataUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountSteps {

    private static final Logger logger = LogManager.getLogger(AccountSteps.class);
    private final ScenarioContext context;

    private LoginPage loginPage;
    private MyAccountPage myAccountPage;
    private ContactPage contactPage;

    public AccountSteps(ScenarioContext context) {
        this.context = context;
    }

    private LoginPage loginPage() {
        if (loginPage == null) loginPage = new LoginPage();
        return loginPage;
    }

    private MyAccountPage accountPage() {
        if (myAccountPage == null) myAccountPage = new MyAccountPage();
        return myAccountPage;
    }

    private ContactPage contactPage() {
        if (contactPage == null) contactPage = new ContactPage();
        return contactPage;
    }

    // ─── Login page ───────────────────────────────────────────────────────────

    @Given("the user is on the WM login page")
    public void theUserIsOnTheLoginPage() {
        loginPage().open();
        AssertionUtils.assertTrue(loginPage().isPageLoaded(),
                "Login page should load successfully");
        ReportLogger.info("Login page opened");
    }

    @Then("the login form should be displayed")
    public void theLoginFormShouldBeDisplayed() {
        AssertionUtils.assertTrue(loginPage().isEmailFieldVisible(),
                "Email field should be visible on login page");
        AssertionUtils.assertTrue(loginPage().isPasswordFieldVisible(),
                "Password field should be visible on login page");
        ReportLogger.pass("Login form is fully displayed");
    }

    @Then("the Forgot Password link should be visible")
    public void theForgotPasswordLinkShouldBeVisible() {
        AssertionUtils.assertTrue(loginPage().isForgotPasswordLinkVisible(),
                "Forgot Password link should be visible");
        ReportLogger.pass("Forgot Password link is visible");
    }

    @Then("the Create Account link should be visible")
    public void theCreateAccountLinkShouldBeVisible() {
        AssertionUtils.assertTrue(loginPage().isCreateAccountLinkVisible(),
                "Create Account link should be visible");
        ReportLogger.pass("Create Account link is visible");
    }

    @When("the user enters valid credentials")
    public void theUserEntersValidCredentials() {
        String email = context.contains("testEmail")
                ? context.get("testEmail") : TestDataUtils.getEmail();
        String password = "TestPassword@123";
        loginPage().enterEmail(email);
        loginPage().enterPassword(password);
        ReportLogger.info("Entered valid credentials for: " + email);
    }

    @When("the user enters email {string} and password {string}")
    public void theUserEntersEmailAndPassword(String email, String password) {
        loginPage().loginWith(email, password);
        ReportLogger.info("Submitted login form for: " + email);
    }

    @When("the user submits the login form")
    public void theUserSubmitsTheLoginForm() {
        loginPage().clickSignIn();
        ReportLogger.info("Clicked Sign In button");
    }

    @Then("a login error message should be displayed")
    public void aLoginErrorMessageShouldBeDisplayed() {
        AssertionUtils.assertTrue(loginPage().isErrorMessageDisplayed(),
                "An error message should be shown for invalid login");
        ReportLogger.info("Login error message: " + loginPage().getErrorMessageText());
    }

    @When("the user clicks on Forgot Password")
    public void theUserClicksForgotPassword() {
        loginPage().clickForgotPassword();
        ReportLogger.info("Clicked Forgot Password");
    }

    @When("the user clicks on Create Account")
    public void theUserClicksCreateAccount() {
        loginPage().clickCreateAccount();
        ReportLogger.info("Clicked Create Account link");
    }

    // ─── My Account page ──────────────────────────────────────────────────────

    @Given("the user is on the My Account page")
    public void theUserIsOnMyAccountPage() {
        accountPage().open();
        AssertionUtils.assertTrue(accountPage().isPageLoaded(),
                "My Account page should load");
        ReportLogger.info("My Account page opened");
    }

    @Then("the account dashboard should be displayed")
    public void theAccountDashboardShouldBeDisplayed() {
        AssertionUtils.assertTrue(accountPage().isPageLoaded(),
                "My Account dashboard should be displayed");
        ReportLogger.pass("Account dashboard is displayed");
    }

    @Then("the sign out link should be visible")
    public void theSignOutLinkShouldBeVisible() {
        AssertionUtils.assertTrue(accountPage().isSignOutLinkVisible(),
                "Sign Out link should be visible on My Account page");
        ReportLogger.pass("Sign Out link is visible");
    }

    @When("the user navigates to the Billing section")
    public void theUserNavigatesToBillingSection() {
        accountPage().goToBilling();
        ReportLogger.info("Navigated to Billing section");
    }

    @When("the user navigates to the Services section")
    public void theUserNavigatesToServicesSection() {
        accountPage().goToServices();
        ReportLogger.info("Navigated to Services section");
    }

    @When("the user signs out")
    public void theUserSignsOut() {
        accountPage().signOut();
        ReportLogger.info("User signed out");
    }

    // ─── Contact page ─────────────────────────────────────────────────────────

    @Given("the user is on the Contact Us page")
    public void theUserIsOnContactUsPage() {
        contactPage().open();
        AssertionUtils.assertTrue(contactPage().isPageLoaded(),
                "Contact Us page should load");
        ReportLogger.info("Contact Us page opened");
    }

    @Then("the contact form should be displayed")
    public void theContactFormShouldBeDisplayed() {
        AssertionUtils.assertTrue(contactPage().isPageLoaded(),
                "Contact form should be displayed");
        ReportLogger.pass("Contact form is displayed");
    }

    @When("the user fills in the contact form with valid details")
    public void theUserFillsContactFormWithValidDetails() {
        contactPage().fillAndSubmit(
                TestDataUtils.getCustomerName().split(" ")[0],
                TestDataUtils.getCustomerName().contains(" ")
                        ? TestDataUtils.getCustomerName().split(" ")[1] : "User",
                TestDataUtils.getEmail(),
                TestDataUtils.getPhoneNumber(),
                "I need help with my waste collection service at my address."
        );
        ReportLogger.info("Filled and submitted contact form");
    }

    @When("the user submits the contact form without filling required fields")
    public void theUserSubmitsContactFormWithoutRequiredFields() {
        contactPage().submitForm();
        ReportLogger.info("Submitted empty contact form");
    }

    @Then("a form submission confirmation should be displayed")
    public void aFormSubmissionConfirmationShouldBeDisplayed() {
        AssertionUtils.assertTrue(contactPage().isConfirmationDisplayed(),
                "Confirmation message should be displayed after form submission");
        ReportLogger.pass("Contact form confirmation: " + contactPage().getConfirmationText());
    }

    @Then("contact form validation errors should be displayed")
    public void contactFormValidationErrorsShouldBeDisplayed() {
        AssertionUtils.assertTrue(contactPage().hasFieldErrors(),
                "Validation errors should appear for empty required fields");
        ReportLogger.info("Field error count: " + contactPage().getFieldErrorCount());
    }

    @Then("the support phone number should be visible")
    public void theSupportPhoneNumberShouldBeVisible() {
        String phone = contactPage().getSupportPhoneNumber();
        AssertionUtils.assertNotEmpty(phone, "Support phone number should be visible");
        ReportLogger.pass("Support phone number: " + phone);
    }
}
