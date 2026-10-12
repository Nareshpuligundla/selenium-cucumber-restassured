package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents the guest checkout page at /checkout.
 * Covers: checkout option selection, guest form entry (shipping + payment),
 * gift card application, address verification prompt, and form-level errors.
 */
public class CheckoutPage extends BasePage {

    // ─── Checkout option buttons ───────────────────────────────────────────────

    // TODO: replace - confirm exact selector for Guest Checkout button with dev team
    @FindBy(css = "button[data-testid='guest-checkout'], button.guest-checkout-btn, " +
                  "a[data-testid='guest-checkout'], .guest-checkout-option button")
    private WebElement guestCheckoutButton;

    // TODO: replace - confirm exact selector for Sign In option on the checkout options page
    @FindBy(css = "button[data-testid='sign-in'], a[href*='login'], button.sign-in-btn, " +
                  ".sign-in-option button, a[data-testid='sign-in']")
    private WebElement signInButton;

    // ─── Guest checkout form – contact ─────────────────────────────────────────

    @FindBy(css = "input[name='email'], input[type='email'], input[id*='email' i]")
    private WebElement emailInput;

    @FindBy(css = "input[name='firstName'], input[id*='firstName' i], " +
                  "input[placeholder*='first name' i], input[autocomplete='given-name']")
    private WebElement firstNameInput;

    @FindBy(css = "input[name='lastName'], input[id*='lastName' i], " +
                  "input[placeholder*='last name' i], input[autocomplete='family-name']")
    private WebElement lastNameInput;

    // ─── Guest checkout form – shipping address ────────────────────────────────

    @FindBy(css = "input[name='streetAddress'], input[name='address1'], " +
                  "input[id*='street' i], input[autocomplete='address-line1'], " +
                  "input[placeholder*='street' i]")
    private WebElement streetAddressInput;

    @FindBy(css = "input[name='city'], input[id*='city' i], " +
                  "input[placeholder*='city' i], input[autocomplete='address-level2']")
    private WebElement cityInput;

    @FindBy(css = "input[name='state'], input[id*='state' i], " +
                  "input[placeholder*='state' i], input[autocomplete='address-level1']")
    private WebElement stateInput;

    @FindBy(css = "input[name='postalCode'], input[name='zip'], input[id*='postal' i], " +
                  "input[id*='zip' i], input[placeholder*='zip' i], input[autocomplete='postal-code']")
    private WebElement postalCodeInput;

    @FindBy(css = "select[name='country'], select[id*='country' i], " +
                  "select[data-testid='country'], select[autocomplete='country']")
    private WebElement countryDropdown;

    // ─── Guest checkout form – payment ─────────────────────────────────────────

    // TODO: replace - card number may be inside an iframe (Stripe/Braintree); update selector if so
    @FindBy(css = "input[name='cardNumber'], input[id*='card-number' i], " +
                  "input[data-testid='card-number'], input[placeholder*='card number' i], " +
                  "input[autocomplete='cc-number']")
    private WebElement cardNumberInput;

    // TODO: replace - expiry date may be inside an iframe; update selector if so
    @FindBy(css = "input[name='expiryDate'], input[name='expiry'], input[id*='expiry' i], " +
                  "input[id*='exp-date' i], input[placeholder*='MM/YY' i], " +
                  "input[placeholder*='expiry' i], input[autocomplete='cc-exp']")
    private WebElement expiryDateInput;

    // TODO: replace - CVV may be inside an iframe; update selector if so
    @FindBy(css = "input[name='cvv'], input[name='cvc'], input[id*='cvv' i], " +
                  "input[id*='cvc' i], input[placeholder*='CVV' i], input[autocomplete='cc-csc']")
    private WebElement cvvInput;

    // ─── Gift card field ───────────────────────────────────────────────────────

    // TODO: replace - confirm exact selector for the gift card input with dev team
    @FindBy(css = "input[name='giftCard'], input[id*='gift-card' i], " +
                  "input[placeholder*='gift card' i], input[data-testid='gift-card-input']")
    private WebElement giftCardInput;

    // ─── Submit button ─────────────────────────────────────────────────────────

    // TODO: replace - confirm exact selector for Place Order button with dev team
    @FindBy(css = "button[data-testid='place-order'], button.place-order-btn, " +
                  "button[type='submit'].checkout-submit, input[value='Place Order']")
    private WebElement placeOrderButton;

    // ─── Error / validation messages ───────────────────────────────────────────

    // TODO: replace - confirm exact selector for the gift card restriction error banner with dev team
    @FindBy(css = "[data-testid='gift-card-error'], .gift-card-error, " +
                  ".error-banner[role='alert'], .alert-danger.gift-card")
    private WebElement giftCardErrorBanner;

    // ─── Address verification prompt ───────────────────────────────────────────

    // TODO: NEEDS_HUMAN_REVIEW - confirm the exact UI component (modal / inline banner / redirect)
    // and verify the selector below with dev team before enabling TC-002
    @FindBy(css = "[data-testid='address-verification'], .address-verification-modal, " +
                  ".address-verify-banner, dialog[open], [role='dialog']")
    private WebElement addressVerificationPrompt;

    // TODO: replace - confirm the selector that shows the address text inside the verification UI
    @FindBy(css = "[data-testid='verify-address-text'], .address-verification-modal .address-text, " +
                  "[role='dialog'] .address-display, .verification-address")
    private WebElement verificationAddressText;

    // TODO: replace - confirm exact selector for Confirm Address button in the verification UI
    @FindBy(css = "button[data-testid='confirm-address'], button.confirm-address-btn, " +
                  "[role='dialog'] button.confirm, button[aria-label*='confirm address' i]")
    private WebElement confirmAddressButton;

    // TODO: replace - confirm exact selector for Edit Address button in the verification UI
    @FindBy(css = "button[data-testid='edit-address'], button.edit-address-btn, " +
                  "[role='dialog'] button.edit, button[aria-label*='edit address' i]")
    private WebElement editAddressButton;

    // ─── Order total display ───────────────────────────────────────────────────

    // TODO: replace - confirm the exact selector for the order total amount in the checkout form
    @FindBy(css = "[data-testid='order-total'], .order-total-amount, " +
                  ".checkout-total, .total-price, .cart-summary-total")
    private WebElement orderTotal;

    // ─── By locators ──────────────────────────────────────────────────────────

    private static final By CHECKOUT_OPTIONS_LOC =
            By.cssSelector("button[data-testid='guest-checkout'], .checkout-options, " +
                           ".guest-checkout-option, form[data-testid='guest-form']");

    private static final By GUEST_FORM_LOC =
            By.cssSelector("form[data-testid='guest-form'], form.guest-checkout-form, " +
                           ".guest-checkout-form, [data-testid='guest-checkout-form']");

    private static final By GIFT_CARD_INPUT_LOC =
            By.cssSelector("input[name='giftCard'], input[id*='gift-card' i], " +
                           "input[data-testid='gift-card-input']");

    private static final By ADDRESS_VERIFICATION_LOC =
            By.cssSelector("[data-testid='address-verification'], .address-verification-modal, " +
                           "dialog[open], [role='dialog']");

    // ─── Page readiness ────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(CHECKOUT_OPTIONS_LOC, 15);
    }

    // ─── Checkout option actions ───────────────────────────────────────────────

    public boolean isGuestCheckoutButtonVisible() {
        return isDisplayed(guestCheckoutButton);
    }

    public boolean isSignInButtonVisible() {
        return isDisplayed(signInButton);
    }

    public void clickGuestCheckout() {
        click(guestCheckoutButton);
        logger.info("Clicked Guest Checkout button");
    }

    // ─── Form presence checks ──────────────────────────────────────────────────

    public boolean isGuestFormDisplayed() {
        try {
            WaitUtils.waitForPresence(GUEST_FORM_LOC);
        } catch (Exception e) {
            logger.warn("Guest form container not found, checking email field directly");
        }
        return isDisplayed(emailInput);
    }

    public boolean isGiftCardFieldPresent() {
        return WaitUtils.isElementVisible(GIFT_CARD_INPUT_LOC, 10);
    }

    // ─── Form field entry ──────────────────────────────────────────────────────

    public void enterEmail(String email) {
        type(emailInput, email);
        logger.info("Entered email: {}", email);
    }

    public void enterFirstName(String firstName) {
        type(firstNameInput, firstName);
        logger.info("Entered first name: {}", firstName);
    }

    public void enterLastName(String lastName) {
        type(lastNameInput, lastName);
        logger.info("Entered last name: {}", lastName);
    }

    public void enterStreetAddress(String address) {
        type(streetAddressInput, address);
        logger.info("Entered street address: {}", address);
    }

    public void enterCity(String city) {
        type(cityInput, city);
        logger.info("Entered city: {}", city);
    }

    public void enterState(String state) {
        type(stateInput, state);
        logger.info("Entered state: {}", state);
    }

    public void enterPostalCode(String postalCode) {
        type(postalCodeInput, postalCode);
        logger.info("Entered postal code: {}", postalCode);
    }

    public void selectCountry(String country) {
        ElementUtils.selectByVisibleText(countryDropdown, country);
        logger.info("Selected country: {}", country);
    }

    public void enterCardNumber(String cardNumber) {
        type(cardNumberInput, cardNumber);
        logger.info("Entered card number (masked for security)");
    }

    public void enterExpiryDate(String expiryDate) {
        type(expiryDateInput, expiryDate);
        logger.info("Entered expiry date: {}", expiryDate);
    }

    public void enterCvv(String cvv) {
        type(cvvInput, cvv);
        logger.info("Entered CVV (masked for security)");
    }

    public void enterGiftCard(String giftCardCode) {
        // TODO: replace - if the UI has an explicit Apply button, add a clickApplyGiftCard() call after typing
        type(giftCardInput, giftCardCode);
        // Press Enter to trigger gift card validation; update if UI uses an Apply button instead
        giftCardInput.sendKeys(Keys.ENTER);
        logger.info("Entered gift card code: {}", giftCardCode);
    }

    // ─── Place order ───────────────────────────────────────────────────────────

    public void clickPlaceOrder() {
        click(placeOrderButton);
        logger.info("Clicked Place Order button");
    }

    public boolean isPlaceOrderButtonVisible() {
        return isDisplayed(placeOrderButton);
    }

    public boolean isPlaceOrderButtonEnabled() {
        return ElementUtils.isEnabled(placeOrderButton);
    }

    // ─── Gift card error assertions ────────────────────────────────────────────

    public boolean isGiftCardErrorDisplayed() {
        return isDisplayed(giftCardErrorBanner);
    }

    public String getGiftCardErrorMessage() {
        return getText(giftCardErrorBanner);
    }

    public String getGiftCardFieldValue() {
        try {
            return ElementUtils.getAttribute(giftCardInput, "value");
        } catch (Exception e) {
            logger.warn("Could not read gift card field value: {}", e.getMessage());
            return "";
        }
    }

    // ─── Order total ───────────────────────────────────────────────────────────

    public String getOrderTotal() {
        try {
            return getText(orderTotal);
        } catch (Exception e) {
            logger.warn("Could not read order total: {}", e.getMessage());
            return "";
        }
    }

    // ─── Address verification prompt ───────────────────────────────────────────

    public boolean isAddressVerificationPromptDisplayed() {
        return WaitUtils.isElementVisible(ADDRESS_VERIFICATION_LOC, 15);
    }

    public String getVerificationAddressText() {
        try {
            return getText(verificationAddressText);
        } catch (Exception e) {
            logger.warn("Could not read verification address text: {}", e.getMessage());
            return "";
        }
    }

    public boolean isConfirmAddressButtonVisible() {
        return isDisplayed(confirmAddressButton);
    }

    public boolean isEditAddressButtonVisible() {
        return isDisplayed(editAddressButton);
    }

    public void clickConfirmAddress() {
        click(confirmAddressButton);
        logger.info("Clicked Confirm Address button");
    }
}
