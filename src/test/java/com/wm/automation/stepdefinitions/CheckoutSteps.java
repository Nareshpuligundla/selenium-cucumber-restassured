package com.wm.automation.stepdefinitions;

import com.wm.automation.base.ScenarioContext;
import com.wm.automation.pages.CartPage;
import com.wm.automation.pages.CheckoutPage;
import com.wm.automation.pages.OrderConfirmationPage;
import com.wm.automation.reporting.ReportLogger;
import com.wm.automation.utils.AssertionUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckoutSteps {

    private static final Logger logger = LogManager.getLogger(CheckoutSteps.class);

    private final ScenarioContext context;

    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage confirmationPage;

    public CheckoutSteps(ScenarioContext context) {
        this.context = context;
    }

    // ─── Lazy page object accessors ────────────────────────────────────────────

    private CartPage cartPage() {
        if (cartPage == null) cartPage = new CartPage();
        return cartPage;
    }

    private CheckoutPage checkoutPage() {
        if (checkoutPage == null) checkoutPage = new CheckoutPage();
        return checkoutPage;
    }

    private OrderConfirmationPage confirmationPage() {
        if (confirmationPage == null) confirmationPage = new OrderConfirmationPage();
        return confirmationPage;
    }

    // ─── Cart navigation ───────────────────────────────────────────────────────

    @Given("the user navigates to the checkout cart page")
    public void theUserNavigatesToTheCheckoutCartPage() {
        cartPage().openCart();
        ReportLogger.info("Navigated to checkout cart page");
    }

    @Given("the user navigates to the checkout cart page with a subscription product")
    public void theUserNavigatesToTheCheckoutCartPageWithASubscriptionProduct() {
        cartPage().openSubscriptionCart();
        ReportLogger.info("Navigated to checkout cart page with subscription product");
    }

    // ─── Cart assertions ───────────────────────────────────────────────────────

    @Then("the cart page should be displayed with a Proceed to Checkout button")
    public void theCartPageShouldBeDisplayedWithAProceedToCheckoutButton() {
        boolean visible = cartPage().isProceedToCheckoutButtonVisible();
        ReportLogger.info("Proceed to Checkout button visible: " + visible);
        AssertionUtils.assertTrue(visible,
                "Cart page should display a Proceed to Checkout button");
    }

    @Then("the cart page should display {string} at {string}")
    public void theCartPageShouldDisplayProductAtPrice(String productName, String price) {
        boolean nameMatch = cartPage().isItemDisplayed(productName);
        boolean priceMatch = cartPage().isTotalDisplayed(price);
        ReportLogger.info("Cart shows '" + productName + "': " + nameMatch
                + ", shows '" + price + "': " + priceMatch);
        AssertionUtils.assertTrue(nameMatch,
                "Cart should display product: " + productName);
        AssertionUtils.assertTrue(priceMatch,
                "Cart should display price: " + price);
    }

    // ─── Checkout navigation ───────────────────────────────────────────────────

    @When("the user clicks the Proceed to Checkout button")
    public void theUserClicksTheProceedToCheckoutButton() {
        cartPage().clickProceedToCheckout();
        ReportLogger.info("Clicked Proceed to Checkout button");
    }

    // ─── Checkout options page ─────────────────────────────────────────────────

    @Then("the checkout options page should display Guest Checkout and Sign In options")
    public void theCheckoutOptionsPageShouldDisplayGuestCheckoutAndSignInOptions() {
        boolean guestVisible = checkoutPage().isGuestCheckoutButtonVisible();
        boolean signInVisible = checkoutPage().isSignInButtonVisible();
        ReportLogger.info("Guest Checkout visible: " + guestVisible
                + ", Sign In visible: " + signInVisible);
        AssertionUtils.assertTrue(guestVisible,
                "Guest Checkout option should be visible on the checkout page");
        AssertionUtils.assertTrue(signInVisible,
                "Sign In option should be visible on the checkout page");
    }

    @When("the user clicks the Guest Checkout button")
    public void theUserClicksTheGuestCheckoutButton() {
        checkoutPage().clickGuestCheckout();
        ReportLogger.info("Clicked Guest Checkout button");
    }

    // ─── Guest checkout form ───────────────────────────────────────────────────

    @Then("the guest checkout form should be displayed")
    public void theGuestCheckoutFormShouldBeDisplayed() {
        boolean displayed = checkoutPage().isGuestFormDisplayed();
        ReportLogger.info("Guest checkout form displayed: " + displayed);
        AssertionUtils.assertTrue(displayed,
                "Guest checkout form should be displayed after clicking Guest Checkout");
    }

    @Then("the guest checkout form should include a Gift Card field")
    public void theGuestCheckoutFormShouldIncludeAGiftCardField() {
        boolean formDisplayed = checkoutPage().isGuestFormDisplayed();
        boolean giftCardPresent = checkoutPage().isGiftCardFieldPresent();
        ReportLogger.info("Guest form displayed: " + formDisplayed
                + ", Gift Card field present: " + giftCardPresent);
        AssertionUtils.assertTrue(formDisplayed,
                "Guest checkout form should be displayed");
        AssertionUtils.assertTrue(giftCardPresent,
                "Guest checkout form should include a Gift Card input field");
    }

    // ─── Form field entry ──────────────────────────────────────────────────────

    @When("the user enters {string} in the Email Address field")
    public void theUserEntersInTheEmailAddressField(String email) {
        checkoutPage().enterEmail(email);
        ReportLogger.info("Entered email: " + email);
    }

    @And("the user enters {string} in the Street Address field")
    public void theUserEntersInTheStreetAddressField(String streetAddress) {
        checkoutPage().enterStreetAddress(streetAddress);
        ReportLogger.info("Entered street address: " + streetAddress);
    }

    @And("the user enters {string} in the City field")
    public void theUserEntersInTheCityField(String city) {
        checkoutPage().enterCity(city);
        ReportLogger.info("Entered city: " + city);
    }

    @And("the user enters {string} in the State field")
    public void theUserEntersInTheStateField(String state) {
        checkoutPage().enterState(state);
        ReportLogger.info("Entered state: " + state);
    }

    @And("the user enters {string} in the Postal Code field")
    public void theUserEntersInThePostalCodeField(String postalCode) {
        checkoutPage().enterPostalCode(postalCode);
        ReportLogger.info("Entered postal code: " + postalCode);
    }

    @And("the user selects {string} from the Country dropdown")
    public void theUserSelectsFromTheCountryDropdown(String country) {
        checkoutPage().selectCountry(country);
        ReportLogger.info("Selected country: " + country);
    }

    @And("the user enters {string} in the Card Number field")
    public void theUserEntersInTheCardNumberField(String cardNumber) {
        checkoutPage().enterCardNumber(cardNumber);
        ReportLogger.info("Entered card number (masked)");
    }

    @And("the user enters {string} in the Expiry Date field")
    public void theUserEntersInTheExpiryDateField(String expiryDate) {
        checkoutPage().enterExpiryDate(expiryDate);
        ReportLogger.info("Entered expiry date: " + expiryDate);
    }

    @And("the user enters {string} in the CVV field")
    public void theUserEntersInTheCVVField(String cvv) {
        checkoutPage().enterCvv(cvv);
        ReportLogger.info("Entered CVV (masked)");
    }

    @And("the user enters {string} in the Gift Card field")
    public void theUserEntersInTheGiftCardField(String giftCardCode) {
        checkoutPage().enterGiftCard(giftCardCode);
        ReportLogger.info("Entered gift card code: " + giftCardCode);
    }

    // ─── Place order ───────────────────────────────────────────────────────────

    @When("the user clicks the Place Order button")
    public void theUserClicksThePlaceOrderButton() {
        checkoutPage().clickPlaceOrder();
        ReportLogger.info("Clicked Place Order button");
    }

    // ─── Order confirmation ────────────────────────────────────────────────────

    @Then("the order confirmation page should be displayed")
    public void theOrderConfirmationPageShouldBeDisplayed() {
        boolean loaded = confirmationPage().isPageLoaded();
        ReportLogger.info("Order confirmation page loaded: " + loaded);
        AssertionUtils.assertTrue(loaded,
                "Order confirmation page should load at /checkout/confirmation after placing order");
    }

    @And("the confirmation heading {string} should be visible")
    public void theConfirmationHeadingShouldBeVisible(String expectedHeading) {
        boolean visible = confirmationPage().isConfirmationHeadingDisplayed();
        String actualHeading = confirmationPage().getConfirmationHeadingText();
        ReportLogger.info("Confirmation heading: '" + actualHeading + "'");
        AssertionUtils.assertTrue(visible,
                "Confirmation heading should be visible on the confirmation page");
        AssertionUtils.assertContainsIgnoreCase(actualHeading, expectedHeading,
                "Confirmation heading should contain: " + expectedHeading);
    }

    @And("the confirmation page should display the guest email {string}")
    public void theConfirmationPageShouldDisplayTheGuestEmail(String expectedEmail) {
        String actualEmail = confirmationPage().getGuestEmail();
        ReportLogger.info("Guest email on confirmation page: " + actualEmail);
        AssertionUtils.assertContains(actualEmail, expectedEmail,
                "Confirmation page should display guest email: " + expectedEmail);
    }

    @And("the confirmation page should display a unique order ID")
    public void theConfirmationPageShouldDisplayAUniqueOrderId() {
        boolean orderIdPresent = confirmationPage().isOrderIdPresent();
        String orderId = confirmationPage().getOrderId();
        ReportLogger.info("Order ID on confirmation page: " + orderId);
        AssertionUtils.assertTrue(orderIdPresent,
                "Confirmation page should display a unique order ID");
        context.set("confirmedOrderId", orderId);
    }

    // ─── Address verification (TC-002) ─────────────────────────────────────────

    @Then("the address verification prompt should be displayed for high-value orders")
    public void theAddressVerificationPromptShouldBeDisplayedForHighValueOrders() {
        // TODO: NEEDS_HUMAN_REVIEW - confirm with dev the exact UI component type (inline banner /
        // modal dialog / page redirect) and the exact system message before marking this step verified.
        // Expected message: "We need to verify your shipping address for orders over $500. Please confirm your address to continue."
        boolean displayed = checkoutPage().isAddressVerificationPromptDisplayed();
        ReportLogger.info("Address verification prompt displayed: " + displayed);
        AssertionUtils.assertTrue(displayed,
                "Address verification prompt should be displayed for orders above $500");
    }

    @And("the verification prompt should show the entered address")
    public void theVerificationPromptShouldShowTheEnteredAddress() {
        String addressText = checkoutPage().getVerificationAddressText();
        ReportLogger.info("Verification prompt address text: '" + addressText + "'");
        AssertionUtils.assertNotEmpty(addressText,
                "Address verification prompt should display the entered shipping address");
    }

    @And("the Confirm Address and Edit Address buttons should be visible")
    public void theConfirmAddressAndEditAddressButtonsShouldBeVisible() {
        boolean confirmVisible = checkoutPage().isConfirmAddressButtonVisible();
        boolean editVisible = checkoutPage().isEditAddressButtonVisible();
        ReportLogger.info("Confirm Address visible: " + confirmVisible
                + ", Edit Address visible: " + editVisible);
        AssertionUtils.assertTrue(confirmVisible,
                "Confirm Address button should be visible in the address verification prompt");
        AssertionUtils.assertTrue(editVisible,
                "Edit Address button should be visible in the address verification prompt");
    }

    @When("the user clicks the Confirm Address button")
    public void theUserClicksTheConfirmAddressButton() {
        checkoutPage().clickConfirmAddress();
        ReportLogger.info("Clicked Confirm Address button");
    }

    // ─── Gift card error assertions (TC-003) ───────────────────────────────────

    @Then("the gift card error message {string} should be displayed")
    public void theGiftCardErrorMessageShouldBeDisplayed(String expectedMessage) {
        boolean errorDisplayed = checkoutPage().isGiftCardErrorDisplayed();
        String actualMessage = checkoutPage().getGiftCardErrorMessage();
        ReportLogger.info("Gift card error message: '" + actualMessage + "'");
        AssertionUtils.assertTrue(errorDisplayed,
                "Gift card error banner should be displayed");
        AssertionUtils.assertEquals(actualMessage, expectedMessage,
                "Gift card error message should read exactly: " + expectedMessage);
    }

    @And("the gift card field should be cleared")
    public void theGiftCardFieldShouldBeCleared() {
        String fieldValue = checkoutPage().getGiftCardFieldValue();
        ReportLogger.info("Gift card field value after error: '" + fieldValue + "'");
        AssertionUtils.assertEquals(fieldValue, "",
                "Gift card field should be cleared after an invalid application");
    }

    @And("the order total should remain {string}")
    public void theOrderTotalShouldRemain(String expectedTotal) {
        String actualTotal = checkoutPage().getOrderTotal();
        ReportLogger.info("Order total displayed: '" + actualTotal + "'");
        String numericExpected = expectedTotal.replace("$", "");
        AssertionUtils.assertTrue(
                actualTotal.contains(numericExpected) || actualTotal.contains(expectedTotal),
                "Order total should remain " + expectedTotal + " but was: " + actualTotal);
    }

    @And("the Place Order button should be visible and enabled")
    public void thePlaceOrderButtonShouldBeVisibleAndEnabled() {
        boolean visible = checkoutPage().isPlaceOrderButtonVisible();
        boolean enabled = checkoutPage().isPlaceOrderButtonEnabled();
        ReportLogger.info("Place Order button visible: " + visible + ", enabled: " + enabled);
        AssertionUtils.assertTrue(visible,
                "Place Order button should still be visible after gift card error");
        AssertionUtils.assertTrue(enabled,
                "Place Order button should still be enabled after gift card error");
    }
}
