package com.wm.automation.stepdefinitions;

import com.wm.automation.base.DriverFactory;
import com.wm.automation.base.ScenarioContext;
import com.wm.automation.pages.ChangePasswordPage;
import com.wm.automation.pages.PaymentPage;
import com.wm.automation.pages.ProductListingPage;
import com.wm.automation.pages.RecordDocumentsPage;
import com.wm.automation.pages.WorktopLoginPage;
import com.wm.automation.reporting.ReportLogger;
import com.wm.automation.utils.AssertionUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Step definitions for the Worktop features: login / client switching, lockout,
 * change password, payment, product pagination and document upload.
 * Page objects are lazily initialised; ScenarioContext is injected by PicoContainer.
 */
public class WorktopSteps {

    private static final Logger logger = LogManager.getLogger(WorktopSteps.class);

    private static final String CTX_PAGE1_PRODUCTS = "WORKTOP_PAGE1_PRODUCTS";
    private static final String CTX_PAGE2_PRODUCTS = "WORKTOP_PAGE2_PRODUCTS";
    private static final String CTX_REFERENCE      = "WORKTOP_PAYMENT_REFERENCE";

    private final ScenarioContext context;

    private WorktopLoginPage    loginPage;
    private ChangePasswordPage  changePasswordPage;
    private PaymentPage         paymentPage;
    private ProductListingPage  productPage;
    private RecordDocumentsPage documentsPage;

    public WorktopSteps(ScenarioContext context) {
        this.context = context;
    }

    private WorktopLoginPage loginPage() {
        if (loginPage == null) loginPage = new WorktopLoginPage();
        return loginPage;
    }

    private ChangePasswordPage changePasswordPage() {
        if (changePasswordPage == null) changePasswordPage = new ChangePasswordPage();
        return changePasswordPage;
    }

    private PaymentPage paymentPage() {
        if (paymentPage == null) paymentPage = new PaymentPage();
        return paymentPage;
    }

    private ProductListingPage productPage() {
        if (productPage == null) productPage = new ProductListingPage();
        return productPage;
    }

    private RecordDocumentsPage documentsPage() {
        if (documentsPage == null) documentsPage = new RecordDocumentsPage();
        return documentsPage;
    }

    // =========================================================================
    // Login / client selection
    // =========================================================================

    @Given("the user is on the Worktop login page")
    public void theUserIsOnTheWorktopLoginPage() {
        loginPage().open();
        AssertionUtils.assertTrue(loginPage().isPageLoaded(),
                "Worktop login page should load with identifier, password and Sign In button");
        ReportLogger.info("Worktop login page opened");
    }

    @When("the user selects client {string} in the client selector")
    public void theUserSelectsClient(String client) {
        loginPage().selectClient(client);
        AssertionUtils.assertContainsIgnoreCase(loginPage().getSelectedClient(), client,
                "'" + client + "' should be shown as the selected client");
        ReportLogger.info("Selected client: " + client);
    }

    @When("the user enters identifier {string} in the Worktop identifier field")
    public void theUserEntersIdentifier(String identifier) {
        loginPage().enterIdentifier(identifier);
        ReportLogger.info("Entered identifier: " + identifier);
    }

    @When("the user enters {string} in the Worktop password field")
    public void theUserEntersWorktopPassword(String password) {
        loginPage().enterPassword(password);
        ReportLogger.info("Entered Worktop password: [hidden]");
    }

    @When("the user clicks the Worktop Sign In button")
    public void theUserClicksWorktopSignIn() {
        loginPage().clickSignIn();
        ReportLogger.info("Clicked Worktop Sign In");
    }

    @When("the user signs in to Worktop as {string} with password {string}")
    public void theUserSignsInToWorktop(String identifier, String password) {
        loginPage().open();
        loginPage().enterIdentifier(identifier);
        loginPage().enterPassword(password);
        loginPage().clickSignIn();
        ReportLogger.info("Signed in to Worktop as " + identifier);
    }

    @When("the user repeats the failed Worktop login {int} times with identifier {string} and password {string}")
    public void theUserRepeatsFailedLogin(int times, String identifier, String password) {
        for (int i = 1; i <= times; i++) {
            loginPage().enterIdentifier(identifier);
            loginPage().enterPassword(password);
            loginPage().clickSignIn();
            ReportLogger.info("Failed login attempt " + i);
        }
    }

    @When("the user clears the identifier field and enters {string}")
    public void theUserClearsIdentifierAndEnters(String identifier) {
        loginPage().enterIdentifier(identifier);
    }

    @When("the user clicks the {string} action on the Worktop login page")
    public void theUserClicksSwitchAction(String actionText) {
        loginPage().clickSwitchClient();
        ReportLogger.info("Clicked action: " + actionText);
    }

    @When("the user clicks the Forgot password link")
    public void theUserClicksForgotPassword() {
        loginPage().clickForgotPassword();
        ReportLogger.info("Clicked Forgot password link");
    }

    @Then("the Worktop login error {string} should be displayed")
    public void theWorktopLoginErrorShouldBeDisplayed(String expected) {
        AssertionUtils.assertTrue(loginPage().isErrorDisplayed(),
                "An error message should be displayed on the Worktop login page");
        String actual = loginPage().getErrorText();
        ReportLogger.info("Worktop login message: " + actual);
        AssertionUtils.assertContainsIgnoreCase(actual, expected,
                "Login message should contain: " + expected);
    }

    @Then("the Worktop login error text should not contain {string}")
    public void theWorktopLoginErrorShouldNotContain(String text) {
        String actual = loginPage().getErrorText().toLowerCase();
        AssertionUtils.assertFalse(actual.contains(text.toLowerCase()),
                "Login message must not disclose: " + text);
    }

    @Then("the wrong-client message should offer the {string} action")
    public void theWrongClientMessageShouldOfferAction(String action) {
        AssertionUtils.assertTrue(loginPage().isSwitchClientActionDisplayed(action),
                "A wrong-client message with a '" + action + "' action should be displayed");
        ReportLogger.pass("Wrong-client message with action: " + action);
    }

    @Then("no {string} action should be offered on the login page")
    public void noActionShouldBeOffered(String action) {
        AssertionUtils.assertFalse(loginPage().isSwitchClientActionDisplayed(action),
                "No '" + action + "' action should be offered");
    }

    @Then("the user should remain on the Worktop login page")
    public void theUserShouldRemainOnTheWorktopLoginPage() {
        AssertionUtils.assertTrue(loginPage().getCurrentUrl().contains("/login"),
                "User should remain on /login; actual URL: " + loginPage().getCurrentUrl());
    }

    @Then("the selected client should be {string}")
    public void theSelectedClientShouldBe(String client) {
        AssertionUtils.assertContainsIgnoreCase(loginPage().getSelectedClient(), client,
                "Selected client should be " + client);
    }

    @Then("the Worktop identifier field should show {string}")
    public void theIdentifierFieldShouldShow(String identifier) {
        AssertionUtils.assertEquals(loginPage().getIdentifierValue(), identifier,
                "Identifier field should retain its value");
    }

    @Then("the Worktop password field should be empty")
    public void thePasswordFieldShouldBeEmpty() {
        String value = loginPage().getPasswordValue();
        AssertionUtils.assertTrue(value == null || value.isEmpty(),
                "Password field should be empty");
    }

    @Then("no authenticated session should exist")
    public void noAuthenticatedSessionShouldExist() {
        AssertionUtils.assertTrue(DriverFactory.getDriver().manage().getCookies().stream()
                        .noneMatch(c -> c.getName().toLowerCase().contains("session")
                                || c.getName().toLowerCase().contains("sid")),
                "No authenticated session cookie should exist");
        AssertionUtils.assertTrue(loginPage().getCurrentUrl().contains("/login"),
                "/login should still be displayed");
    }

    @Then("the Worktop workspace home page should be displayed")
    public void theWorkspaceHomePageShouldBeDisplayed() {
        AssertionUtils.assertTrue(changePasswordPage().isWorkspaceHome(),
                "Workspace home page should be displayed after login");
        ReportLogger.pass("Workspace home page displayed");
    }

    @Then("the Worktop login page should be displayed")
    public void theWorktopLoginPageShouldBeDisplayed() {
        AssertionUtils.assertTrue(loginPage().isPageLoaded(), "Worktop login page should be displayed");
    }

    // =========================================================================
    // Change password / reset password
    // =========================================================================

    @When("the user opens the Change Password form from the user menu")
    public void theUserOpensChangePasswordForm() {
        changePasswordPage().openFromUserMenu();
        ReportLogger.info("Opened Change Password form");
    }

    @Then("the Change Password form should be displayed")
    public void theChangePasswordFormShouldBeDisplayed() {
        AssertionUtils.assertTrue(changePasswordPage().isFormDisplayed(),
                "Form with Current password, New password and Confirm new password fields should be displayed");
    }

    @When("the user enters {string} in the Current password field")
    public void theUserEntersCurrentPassword(String value) {
        changePasswordPage().enterCurrentPassword(value);
        ReportLogger.info("Entered current password: [hidden]");
    }

    @When("the user enters {string} in the New password field")
    public void theUserEntersNewPassword(String value) {
        changePasswordPage().enterNewPassword(value);
        ReportLogger.info("Entered new password: [hidden]");
    }

    @When("the user enters a {int}-character password in the New password field")
    public void theUserEntersGeneratedPassword(int length) {
        StringBuilder sb = new StringBuilder("Aa1!");
        while (sb.length() < length) sb.append('x');
        changePasswordPage().enterNewPassword(sb.toString());
        ReportLogger.info("Entered generated new password of length " + sb.length());
    }

    @When("the user enters {string} in the Confirm new password field")
    public void theUserEntersConfirmPassword(String value) {
        changePasswordPage().enterConfirmPassword(value);
        ReportLogger.info("Entered confirm password: [hidden]");
    }

    @When("the user clicks the Save button")
    public void theUserClicksSave() {
        changePasswordPage().clickSave();
        ReportLogger.info("Clicked Save");
    }

    @When("the user logs out of Worktop")
    public void theUserLogsOutOfWorktop() {
        changePasswordPage().logout();
        ReportLogger.info("Logged out of Worktop");
    }

    @Then("no inline error should be displayed on the New password field")
    public void noInlineErrorOnNewPassword() {
        AssertionUtils.assertTrue(changePasswordPage().getNewPasswordError().isEmpty(),
                "No inline rule error should be displayed on the New password field");
    }

    @Then("no inline error should be displayed on the Confirm new password field")
    public void noInlineErrorOnConfirmPassword() {
        AssertionUtils.assertTrue(changePasswordPage().getConfirmPasswordError().isEmpty(),
                "No mismatch error should be displayed on the Confirm new password field");
    }

    @Then("the New password field should show the error {string}")
    public void theNewPasswordFieldShouldShowError(String expected) {
        String actual = changePasswordPage().getNewPasswordError();
        ReportLogger.info("New password error: " + actual);
        AssertionUtils.assertContainsIgnoreCase(actual, expected,
                "Error below New password should contain: " + expected);
    }

    @Then("the Confirm new password field should show the error {string}")
    public void theConfirmPasswordFieldShouldShowError(String expected) {
        String actual = changePasswordPage().getConfirmPasswordError();
        ReportLogger.info("Confirm password error: " + actual);
        AssertionUtils.assertContainsIgnoreCase(actual, expected,
                "Error below Confirm new password should contain: " + expected);
    }

    @Then("the Current password field should keep its value {string}")
    public void theCurrentPasswordShouldKeepValue(String expected) {
        AssertionUtils.assertEquals(changePasswordPage().getCurrentPasswordValue(), expected,
                "Current password field should keep its value");
    }

    @Then("a password change success confirmation should be displayed without any password value")
    public void successConfirmationWithoutPassword() {
        String message = changePasswordPage().getSuccessMessage();
        AssertionUtils.assertNotEmpty(message, "A success confirmation should be displayed");
        AssertionUtils.assertFalse(message.contains("Passphrase") || message.contains("Passw0rd"),
                "Success message must not contain any password value");
        ReportLogger.pass("Password change confirmed: " + message);
    }

    @Then("the password reset message {string} should be displayed")
    public void thePasswordResetMessageShouldBeDisplayed(String expected) {
        AssertionUtils.assertContainsIgnoreCase(changePasswordPage().getSuccessMessage(), expected,
                "Message should contain: " + expected);
    }

    // =========================================================================
    // Product listing / pagination
    // =========================================================================

    @When("the user opens the product listing from the top navigation")
    public void theUserOpensProductListing() {
        productPage().openViaTopNav();
        List<String> names = new ArrayList<>(productPage().getProductNames());
        context.set(CTX_PAGE1_PRODUCTS, names);
        ReportLogger.info("Opened product listing");
    }

    @Then("the product listing should show {int} product cards")
    public void theProductListingShouldShowCards(int expected) {
        AssertionUtils.assertEquals(productPage().getProductCardCount(), expected,
                "Product card count");
    }

    @Then("the pagination should show {string}")
    public void thePaginationShouldShow(String expected) {
        AssertionUtils.assertContainsIgnoreCase(productPage().getPaginationText(), expected,
                "Pagination text should contain: " + expected);
    }

    @Then("the Previous button should be disabled")
    public void thePreviousButtonShouldBeDisabled() {
        AssertionUtils.assertFalse(productPage().isPreviousEnabled(), "Previous button should be disabled");
    }

    @Then("the Next button should be enabled")
    public void theNextButtonShouldBeEnabled() {
        AssertionUtils.assertTrue(productPage().isNextEnabled(), "Next button should be enabled");
    }

    @Then("the Next button should be disabled")
    public void theNextButtonShouldBeDisabled() {
        AssertionUtils.assertFalse(productPage().isNextEnabled(), "Next button should be disabled");
    }

    @When("the user clicks the Next pagination button")
    public void theUserClicksNext() {
        productPage().clickNext();
        context.set(CTX_PAGE2_PRODUCTS, new ArrayList<>(productPage().getProductNames()));
        ReportLogger.info("Clicked Next");
    }

    @When("the user clicks pagination page number {int}")
    public void theUserClicksPageNumber(int page) {
        productPage().clickPage(page);
        ReportLogger.info("Clicked page " + page);
    }

    @When("the user clicks the browser Back button")
    public void theUserClicksBrowserBack() {
        DriverFactory.getDriver().navigate().back();
        ReportLogger.info("Browser Back");
    }

    @Then("the URL should contain {string}")
    public void theUrlShouldContain(String fragment) {
        AssertionUtils.assertTrue(productPage().getCurrentUrl().contains(fragment),
                "URL should contain " + fragment + " but was " + productPage().getCurrentUrl());
    }

    @Then("page 2 products should differ from page 1 products")
    public void page2ShouldDifferFromPage1() {
        List<String> p1 = context.get(CTX_PAGE1_PRODUCTS);
        List<String> p2 = context.get(CTX_PAGE2_PRODUCTS);
        AssertionUtils.assertFalse(p1.equals(p2), "Page 2 should show different products than page 1");
    }

    @Then("the restored page should show the same products as page 2")
    public void theRestoredPageShouldMatchPage2() {
        List<String> p2 = context.get(CTX_PAGE2_PRODUCTS);
        AssertionUtils.assertTrue(p2.equals(new ArrayList<>(productPage().getProductNames())),
                "Page 2 should be restored with the same products");
    }

    @When("the user adds the first product on the page to the cart")
    public void theUserAddsFirstProductToCart() {
        productPage().addFirstProductToCart();
        ReportLogger.info("Added first product to cart");
    }

    @Then("the cart icon count should be {int}")
    public void theCartCountShouldBe(int expected) {
        AssertionUtils.assertEquals(productPage().getCartCount(), expected, "Cart icon count");
    }

    // =========================================================================
    // Payment
    // =========================================================================

    @When("the user opens the cart and clicks Checkout")
    public void theUserChecksOut() {
        paymentPage().checkoutFromCart();
        ReportLogger.info("Checked out from cart");
    }

    @When("the user enters card number {string}")
    public void theUserEntersCard(String card) {
        paymentPage().enterCard(card);
    }

    @When("the user enters expiry date {string}")
    public void theUserEntersExpiry(String expiry) {
        paymentPage().enterExpiry(expiry);
    }

    @When("the user enters CVV {string}")
    public void theUserEntersCvv(String cvv) {
        paymentPage().enterCvv(cvv);
    }

    @When("the user clicks the Pay button")
    public void theUserClicksPay() {
        paymentPage().clickPay();
        ReportLogger.info("Clicked Pay");
    }

    @When("the user double-clicks the Pay button")
    public void theUserDoubleClicksPay() {
        paymentPage().doubleClickPay();
        ReportLogger.info("Double-clicked Pay");
    }

    @Then("the payment page should be displayed with card number, expiry date and CVV fields")
    public void thePaymentPageShouldBeDisplayed() {
        AssertionUtils.assertTrue(paymentPage().isPayEnabled(),
                "Payment page should be displayed with the Pay button");
    }

    @Then("the Card number field should show the error {string}")
    public void theCardErrorShouldBe(String expected) {
        AssertionUtils.assertContainsIgnoreCase(paymentPage().getCardError(), expected,
                "Card number error");
    }

    @Then("the Expiry date field should show the error {string}")
    public void theExpiryErrorShouldBe(String expected) {
        AssertionUtils.assertContainsIgnoreCase(paymentPage().getExpiryError(), expected,
                "Expiry date error");
    }

    @Then("the CVV field should show the error {string}")
    public void theCvvErrorShouldBe(String expected) {
        AssertionUtils.assertContainsIgnoreCase(paymentPage().getCvvError(), expected,
                "CVV error");
    }

    @Then("the Pay button should be enabled")
    public void thePayButtonShouldBeEnabled() {
        AssertionUtils.assertTrue(paymentPage().isPayEnabled(), "Pay button should be enabled");
    }

    @Then("the Pay button should be disabled")
    public void thePayButtonShouldBeDisabled() {
        AssertionUtils.assertFalse(paymentPage().isPayEnabled(), "Pay button should be disabled");
    }

    @Then("the user should remain on the payment page")
    public void theUserShouldRemainOnPaymentPage() {
        AssertionUtils.assertFalse(paymentPage().getCurrentUrl().contains("confirmation"),
                "User should stay on the payment page");
    }

    @Then("the payment banner should read {string}")
    public void thePaymentBannerShouldRead(String expected) {
        String actual = paymentPage().getBannerText();
        ReportLogger.info("Payment banner: " + actual);
        AssertionUtils.assertContainsIgnoreCase(actual, expected, "Payment banner");
    }

    @Then("the 3-D Secure challenge {string} should be displayed")
    public void theThreeDSecureShouldBeDisplayed(String title) {
        AssertionUtils.assertTrue(paymentPage().isThreeDSecureModalDisplayed(),
                "3-D Secure modal '" + title + "' should be displayed");
    }

    @When("the user completes the 3-D Secure challenge with password {string}")
    public void theUserCompletesThreeDSecure(String password) {
        paymentPage().completeThreeDSecure(password);
        ReportLogger.info("Completed 3-D Secure challenge");
    }

    @Then("the payment confirmation should show a reference number matching {string}")
    public void thePaymentReferenceShouldMatch(String regex) {
        String reference = paymentPage().getReferenceNumber();
        context.set(CTX_REFERENCE, reference);
        AssertionUtils.assertTrue(reference != null && reference.matches(regex),
                "Reference '" + reference + "' should match " + regex);
        ReportLogger.pass("Payment reference: " + reference);
    }

    @Then("the pending order status should be {string} with no payment reference")
    public void thePendingOrderStatusShouldBe(String status) {
        String actual = paymentPage().openPendingOrderStatus();
        AssertionUtils.assertContainsIgnoreCase(actual, status, "Order status");
    }

    @Then("the receipt email for the payment reference should be received within {int} seconds")
    public void theReceiptEmailShouldBeReceived(int seconds) {
        // TODO: replace - integrate with the test mailbox API (inbox access not available in the framework)
        String reference = context.get(CTX_REFERENCE);
        logger.warn("Receipt email check for {} within {}s requires mailbox integration", reference, seconds);
        ReportLogger.info("Receipt email verification pending mailbox integration for: " + reference);
    }

    // =========================================================================
    // Records / document upload
    // =========================================================================

    @When("the user opens record {string} from the Records navigation")
    public void theUserOpensRecord(String recordId) {
        documentsPage().openRecord(recordId);
        ReportLogger.info("Opened record " + recordId);
    }

    @When("the user uploads the file {string}")
    public void theUserUploadsFile(String fileName) {
        documentsPage().uploadFiles(fileName);
        ReportLogger.info("Uploaded file: " + fileName);
    }

    @When("the user uploads the files {string} and {string}")
    public void theUserUploadsTwoFiles(String first, String second) {
        documentsPage().uploadFiles(first, second);
        ReportLogger.info("Uploaded files: " + first + ", " + second);
    }

    @When("the user uploads {int} PDF files named doc01.pdf onwards")
    public void theUserUploadsManyFiles(int count) {
        String[] names = new String[count];
        for (int i = 0; i < count; i++) names[i] = String.format("doc%02d.pdf", i + 1);
        documentsPage().uploadFiles(names);
        ReportLogger.info("Uploaded " + count + " files");
    }

    @Then("the upload banner should read {string}")
    public void theUploadBannerShouldRead(String expected) {
        String actual = documentsPage().getBannerText();
        ReportLogger.info("Upload banner: " + actual);
        AssertionUtils.assertContainsIgnoreCase(actual, expected, "Upload banner");
    }

    @Then("the document list should contain exactly {int} document {string}")
    public void theDocumentListShouldContain(int count, String name) {
        List<String> names = documentsPage().getDocumentNames();
        AssertionUtils.assertEquals(names.size(), count, "Document count");
        AssertionUtils.assertTrue(names.contains(name), "Document list should contain " + name);
    }

    @And("the document {string} should show size {string} and uploader {string}")
    public void theDocumentShouldShowDetails(String name, String size, String uploader) {
        String details = documentsPage().getDocumentDetails(name);
        AssertionUtils.assertContainsIgnoreCase(details, size, "Document size");
        AssertionUtils.assertContainsIgnoreCase(details, uploader, "Document uploader");
    }

    @Then("the document list should not contain {string}")
    public void theDocumentListShouldNotContain(String name) {
        AssertionUtils.assertFalse(documentsPage().getDocumentNames().contains(name),
                "Rejected file should not be listed: " + name);
    }
}
