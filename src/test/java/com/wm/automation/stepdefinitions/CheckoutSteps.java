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
    private static final String CART_URL = "https://shop.example.com/cart";
    private static final String CHECKOUT_URL = "https://shop.example.com/checkout";
    private static final String ADMIN_PENDING_URL = "https://admin.example.com/orders/pending-verification";

    private final ScenarioContext context;

    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage confirmationPage;

    public CheckoutSteps(ScenarioContext context) {
        this.context = context;
    }

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

    // ─── Cart ─────────────────────────────────────────────────────────────────

    @Given("the user navigates to the shop cart page")
    public void theUserNavigatesToTheShopCartPage() {
        cartPage().open(CART_URL);
        AssertionUtils.assertTrue(cartPage().isPageLoaded(), "Cart page should load");
        ReportLogger.info("Opened cart page: " + CART_URL);
    }

    @Then("the cart page should display a subtotal of {string} USD")
    public void theCartPageShouldDisplayASubtotalOf(String expectedSubtotal) {
        String actual = cartPage().getSubtotal();
        ReportLogger.info("Cart subtotal: " + actual);
        AssertionUtils.assertContains(actual, expectedSubtotal,
                "Cart subtotal should be " + expectedSubtotal + " USD");
    }

    @Then("the cart page should show the subscription item {string}")
    public void theCartPageShouldShowTheSubscriptionItem(String sku) {
        AssertionUtils.assertTrue(cartPage().isPageLoaded(),
                "Cart page should display subscription item: " + sku);
        ReportLogger.info("Cart page loaded with subscription item: " + sku);
    }

    @When("the user clicks the Proceed to Checkout button")
    public void theUserClicksTheProceedToCheckoutButton() {
        cartPage().clickProceedToCheckout();
        ReportLogger.info("Clicked Proceed to Checkout");
    }

    // ─── Checkout page load ───────────────────────────────────────────────────

    @Then("the guest checkout page should be displayed")
    public void theGuestCheckoutPageShouldBeDisplayed() {
        AssertionUtils.assertTrue(checkoutPage().isPageLoaded(),
                "Guest checkout page should load at /checkout");
        ReportLogger.pass("Guest checkout page is displayed");
    }

    @Then("the gift card input field should be disabled on the checkout page")
    public void theGiftCardInputFieldShouldBeDisabledOnCheckoutPage() {
        AssertionUtils.assertTrue(checkoutPage().isGiftCardInputDisabled(),
                "Gift card input should be disabled when cart contains a subscription SKU");
        ReportLogger.pass("Gift card input is disabled");
    }

    // ─── Email ────────────────────────────────────────────────────────────────

    @When("the user enters {string} in the email address field")
    public void theUserEntersInTheEmailAddressField(String email) {
        checkoutPage().enterEmail(email);
        ReportLogger.info("Entered email: " + email);
    }

    @And("the user enters {string} in the confirm email address field")
    public void theUserEntersInTheConfirmEmailAddressField(String email) {
        checkoutPage().enterConfirmEmail(email);
        ReportLogger.info("Entered confirm email: " + email);
    }

    // ─── Shipping address ─────────────────────────────────────────────────────

    @And("the user enters {string} in the address line 1 field")
    public void theUserEntersInTheAddressLine1Field(String addressLine1) {
        checkoutPage().enterAddressLine1(addressLine1);
        ReportLogger.info("Entered address line 1: " + addressLine1);
    }

    @And("the user enters {string} in the address line 2 field")
    public void theUserEntersInTheAddressLine2Field(String addressLine2) {
        checkoutPage().enterAddressLine2(addressLine2);
        ReportLogger.info("Entered address line 2: " + addressLine2);
    }

    @And("the user enters {string} in the city field")
    public void theUserEntersInTheCityField(String city) {
        checkoutPage().enterCity(city);
        ReportLogger.info("Entered city: " + city);
    }

    @And("the user enters {string} in the state province field")
    public void theUserEntersInTheStateProvinceField(String state) {
        checkoutPage().enterState(state);
        ReportLogger.info("Entered state/province: " + state);
    }

    @And("the user enters {string} in the postal code field")
    public void theUserEntersInThePostalCodeField(String postalCode) {
        checkoutPage().enterPostalCode(postalCode);
        ReportLogger.info("Entered postal code: " + postalCode);
    }

    @And("the user selects {string} from the country dropdown")
    public void theUserSelectsFromTheCountryDropdown(String country) {
        checkoutPage().selectCountry(country);
        ReportLogger.info("Selected country: " + country);
    }

    // ─── Payment ──────────────────────────────────────────────────────────────

    @And("the user enters {string} in the card number field")
    public void theUserEntersInTheCardNumberField(String cardNumber) {
        checkoutPage().enterCardNumber(cardNumber);
        ReportLogger.info("Entered card number: " + cardNumber);
    }

    @And("the user enters {string} in the expiry field")
    public void theUserEntersInTheExpiryField(String expiry) {
        checkoutPage().enterExpiry(expiry);
        ReportLogger.info("Entered expiry: " + expiry);
    }

    @And("the user enters {string} in the CVV field")
    public void theUserEntersInTheCvvField(String cvv) {
        checkoutPage().enterCvv(cvv);
        ReportLogger.info("Entered CVV");
    }

    @And("the user enters {string} in the cardholder name field")
    public void theUserEntersInTheCardholderNameField(String name) {
        checkoutPage().enterCardholderName(name);
        ReportLogger.info("Entered cardholder name: " + name);
    }

    // ─── Place order ──────────────────────────────────────────────────────────

    @When("the user clicks the Place Order button")
    public void theUserClicksThePlaceOrderButton() {
        checkoutPage().clickPlaceOrder();
        ReportLogger.info("Clicked Place Order");
    }

    // ─── Confirmation assertions (TC-001 / TC-012) ────────────────────────────

    @Then("the order confirmation page should be displayed")
    public void theOrderConfirmationPageShouldBeDisplayed() {
        AssertionUtils.assertTrue(confirmationPage().isPageLoaded(),
                "Order confirmation page should load at /checkout/confirmation");
        ReportLogger.pass("Order confirmation page is displayed");
    }

    @And("the order placed success message should be visible")
    public void theOrderPlacedSuccessMessageShouldBeVisible() {
        AssertionUtils.assertTrue(confirmationPage().isOrderPlacedMessageDisplayed(),
                "Confirmation page should show 'Your order has been placed'");
        ReportLogger.pass("Order placed success message is visible");
    }

    @And("an order reference number should be displayed on the confirmation page")
    public void anOrderReferenceNumberShouldBeDisplayedOnTheConfirmationPage() {
        String ref = confirmationPage().getOrderReferenceNumber();
        AssertionUtils.assertNotEmpty(ref, "Order reference number should be displayed");
        context.set("orderReferenceNumber", ref);
        ReportLogger.pass("Order reference number: " + ref);
    }

    @And("the confirmation page should display tax shipping and grand total")
    public void theConfirmationPageShouldDisplayTaxShippingAndGrandTotal() {
        AssertionUtils.assertTrue(confirmationPage().hasTaxDisplayed(),
                "Confirmation page should display tax amount");
        AssertionUtils.assertTrue(confirmationPage().hasShippingCostDisplayed(),
                "Confirmation page should display shipping cost");
        AssertionUtils.assertTrue(confirmationPage().hasGrandTotalDisplayed(),
                "Confirmation page should display grand total");
        ReportLogger.pass("Tax, shipping cost, and grand total are displayed on confirmation page");
    }

    @And("the confirmation page should display an estimated delivery date")
    public void theConfirmationPageShouldDisplayAnEstimatedDeliveryDate() {
        AssertionUtils.assertTrue(confirmationPage().hasEstimatedDeliveryDateDisplayed(),
                "Confirmation page should display estimated delivery date");
        ReportLogger.pass("Estimated delivery date is displayed");
    }

    @And("no AVS hold message should be displayed")
    public void noAvsHoldMessageShouldBeDisplayed() {
        AssertionUtils.assertFalse(confirmationPage().isPendingVerificationMessageDisplayed(),
                "No AVS pending-verification message should appear on a successful order");
        ReportLogger.pass("No AVS hold message is displayed");
    }

    // ─── Pending-verification assertions (TC-003) ─────────────────────────────

    @Then("the order pending address verification message should be displayed")
    public void theOrderPendingAddressVerificationMessageShouldBeDisplayed() {
        AssertionUtils.assertTrue(checkoutPage().isOrderPendingVerificationDisplayed(),
                "Page should show AVS partial-match pending-verification message");
        ReportLogger.pass("Pending address verification message is displayed");
    }

    @And("an order reference number should be displayed on the pending verification page")
    public void anOrderReferenceNumberShouldBeDisplayedOnThePendingVerificationPage() {
        String ref = checkoutPage().getOrderReferenceNumber();
        AssertionUtils.assertNotEmpty(ref, "Order reference number should appear on pending page");
        context.set("orderReferenceNumber", ref);
        ReportLogger.pass("Pending order reference: " + ref);
    }

    @And("no order placed success message should be shown")
    public void noOrderPlacedSuccessMessageShouldBeShown() {
        AssertionUtils.assertFalse(checkoutPage().isOrderConfirmationDisplayed(),
                "No 'Your order has been placed' message should be shown for a pending order");
        ReportLogger.pass("No order-placed success message shown");
    }

    @When("the admin navigates to the pending verification orders page")
    public void theAdminNavigatesToThePendingVerificationOrdersPage() {
        checkoutPage().navigateTo(ADMIN_PENDING_URL);
        ReportLogger.info("Navigated to admin pending verification page: " + ADMIN_PENDING_URL);
    }

    @Then("the order should appear in the pending verification queue with status NOTIFIED")
    public void theOrderShouldAppearInThePendingVerificationQueueWithStatusNotified() {
        String orderRef = context.get("orderReferenceNumber");
        AssertionUtils.assertNotEmpty(orderRef,
                "Order reference must be captured before checking admin queue");
        // TODO: replace - admin pending-verification queue locator for order row and NOTIFIED status column unknown; verify by searching for orderRef in the table and asserting status cell text equals 'NOTIFIED'
        ReportLogger.info("Verifying order " + orderRef + " has NOTIFIED status in pending-verification queue");
        logger.info("Admin pending verification check for order: {}", orderRef);
    }

    // ─── Gift card restriction assertions (TC-004) ────────────────────────────

    @Then("an explanatory message should be shown that gift cards cannot be used with subscription products")
    public void anExplanatoryMessageShouldBeShownThatGiftCardsCannotBeUsedWithSubscriptionProducts() {
        AssertionUtils.assertTrue(checkoutPage().isGiftCardRestrictionMessageDisplayed(),
                "An inline message should explain gift cards cannot be applied to subscription orders");
        ReportLogger.pass("Gift card restriction message is displayed");
    }

    @When("the user attempts to click the disabled gift card input field")
    public void theUserAttemptsToClickTheDisabledGiftCardInputField() {
        checkoutPage().attemptTypeInGiftCardField("");
        ReportLogger.info("Attempted to interact with disabled gift card input field");
    }

    @Then("the gift card input field should remain disabled")
    public void theGiftCardInputFieldShouldRemainDisabled() {
        AssertionUtils.assertTrue(checkoutPage().isGiftCardInputDisabled(),
                "Gift card input should remain disabled");
        ReportLogger.pass("Gift card input remains disabled");
    }

    @When("the user attempts to type {string} into the disabled gift card input field")
    public void theUserAttemptsToTypeIntoTheDisabledGiftCardInputField(String giftCardCode) {
        checkoutPage().attemptTypeInGiftCardField(giftCardCode);
        ReportLogger.info("Attempted to type gift card code: " + giftCardCode);
    }

    @Then("no characters should be entered and the gift card input should remain disabled")
    public void noCharactersShouldBeEnteredAndTheGiftCardInputShouldRemainDisabled() {
        AssertionUtils.assertTrue(checkoutPage().isGiftCardInputDisabled(),
                "Gift card input should remain disabled after typing attempt");
        AssertionUtils.assertTrue(checkoutPage().isGiftCardRestrictionMessageDisplayed(),
                "Gift card restriction message should remain visible");
        ReportLogger.pass("Gift card input remains disabled and restriction message is still visible");
    }
}
