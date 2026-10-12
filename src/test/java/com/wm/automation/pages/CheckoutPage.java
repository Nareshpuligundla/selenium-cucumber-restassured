package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object for the guest checkout page (/checkout).
 * Covers email, shipping address, payment fields, place order button,
 * order confirmation / pending-verification messages, and gift card input state.
 */
public class CheckoutPage extends BasePage {

    private static final String CHECKOUT_PATH = "/checkout";

    // ─── Email ────────────────────────────────────────────────────────────────

    // TODO: replace - actual selector for email address input on shop.example.com checkout unknown
    @FindBy(css = "input[name='email'], input[id*='email' i][id*='address' i], input[placeholder*='email address' i]")
    private WebElement emailInput;

    // TODO: replace - actual selector for confirm email input on shop.example.com checkout unknown
    @FindBy(css = "input[name='confirmEmail'], input[id*='confirm' i][id*='email' i], input[placeholder*='confirm email' i]")
    private WebElement confirmEmailInput;

    // ─── Shipping address ─────────────────────────────────────────────────────

    // TODO: replace - actual selector for address line 1 on shop.example.com checkout unknown
    @FindBy(css = "input[name='addressLine1'], input[id*='address1' i], input[placeholder*='address line 1' i]")
    private WebElement addressLine1Input;

    // TODO: replace - actual selector for address line 2 on shop.example.com checkout unknown
    @FindBy(css = "input[name='addressLine2'], input[id*='address2' i], input[placeholder*='address line 2' i]")
    private WebElement addressLine2Input;

    // TODO: replace - actual selector for city input on shop.example.com checkout unknown
    @FindBy(css = "input[name='city'], input[id*='city' i], input[placeholder*='city' i]")
    private WebElement cityInput;

    // TODO: replace - actual selector for state/province input on shop.example.com checkout unknown
    @FindBy(css = "input[name='state'], input[id*='state' i], input[placeholder*='state' i], input[placeholder*='province' i]")
    private WebElement stateInput;

    // TODO: replace - actual selector for postal code input on shop.example.com checkout unknown
    @FindBy(css = "input[name='postalCode'], input[id*='postal' i], input[placeholder*='postal' i], input[placeholder*='zip' i]")
    private WebElement postalCodeInput;

    // TODO: replace - actual selector for country dropdown on shop.example.com checkout unknown
    @FindBy(css = "select[name='country'], select[id*='country' i]")
    private WebElement countryDropdown;

    // ─── Payment ──────────────────────────────────────────────────────────────

    // TODO: replace - actual selector for card number input on shop.example.com checkout unknown
    @FindBy(css = "input[name='cardNumber'], input[id*='card-number' i], input[placeholder*='card number' i]")
    private WebElement cardNumberInput;

    // TODO: replace - actual selector for expiry input on shop.example.com checkout unknown
    @FindBy(css = "input[name='expiry'], input[id*='expiry' i], input[placeholder*='mm/yy' i], input[placeholder*='expiry' i]")
    private WebElement expiryInput;

    // TODO: replace - actual selector for CVV input on shop.example.com checkout unknown
    @FindBy(css = "input[name='cvv'], input[id*='cvv' i], input[placeholder*='cvv' i], input[placeholder*='cvc' i]")
    private WebElement cvvInput;

    // TODO: replace - actual selector for cardholder name input on shop.example.com checkout unknown
    @FindBy(css = "input[name='cardholderName'], input[id*='cardholder' i], input[placeholder*='cardholder' i], input[placeholder*='name on card' i]")
    private WebElement cardholderNameInput;

    // ─── Gift card ────────────────────────────────────────────────────────────

    // TODO: replace - actual selector for gift card input on shop.example.com checkout unknown
    @FindBy(css = "input[name='giftCard'], input[id*='gift-card' i], input[placeholder*='gift card' i]")
    private WebElement giftCardInput;

    // TODO: replace - actual selector for gift card restriction message on shop.example.com checkout unknown
    @FindBy(css = ".gift-card-restriction, [data-testid='gift-card-disabled-msg'], .gift-card-message")
    private WebElement giftCardRestrictionMessage;

    // ─── Actions / buttons ────────────────────────────────────────────────────

    // TODO: replace - actual selector for Place Order button on shop.example.com checkout unknown
    @FindBy(css = "button[data-testid='place-order'], button.place-order-btn, button[type='submit'][id*='place' i]")
    private WebElement placeOrderButton;

    // ─── Confirmation / status messages ───────────────────────────────────────

    // TODO: replace - actual selector for order confirmation message on shop.example.com confirmation page unknown
    @FindBy(css = ".order-confirmation, [data-testid='order-confirmation'], .confirmation-heading")
    private WebElement orderConfirmationMessage;

    // TODO: replace - actual selector for pending-verification message on shop.example.com unknown
    @FindBy(css = ".pending-verification, [data-testid='pending-verification-msg'], .avs-hold-message")
    private WebElement pendingVerificationMessage;

    // TODO: replace - actual selector for order reference number on shop.example.com checkout/confirmation unknown
    @FindBy(css = ".order-reference, [data-testid='order-reference'], .order-number")
    private WebElement orderReferenceNumber;

    // ─── By-locator constants for wait methods ────────────────────────────────

    private static final By PAGE_READY_LOC =
            By.cssSelector("input[name='email'], input[id*='email' i], form.checkout-form");

    private static final By ORDER_CONFIRMATION_LOC =
            By.cssSelector(".order-confirmation, [data-testid='order-confirmation'], .confirmation-heading");

    private static final By PENDING_VERIFICATION_LOC =
            By.cssSelector(".pending-verification, [data-testid='pending-verification-msg'], .avs-hold-message");

    private static final By GIFT_CARD_INPUT_LOC =
            By.cssSelector("input[name='giftCard'], input[id*='gift-card' i], input[placeholder*='gift card' i]");

    // ─── Page lifecycle ───────────────────────────────────────────────────────

    public void open(String baseUrl) {
        navigateTo(baseUrl + CHECKOUT_PATH);
        logger.info("Opened Checkout page");
    }

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(PAGE_READY_LOC, 15);
    }

    // ─── Email actions ────────────────────────────────────────────────────────

    public void enterEmail(String email) {
        type(emailInput, email);
        logger.info("Entered email: {}", email);
    }

    public void enterConfirmEmail(String email) {
        type(confirmEmailInput, email);
        logger.info("Entered confirm email: {}", email);
    }

    // ─── Shipping address actions ─────────────────────────────────────────────

    public void enterAddressLine1(String address) {
        type(addressLine1Input, address);
        logger.info("Entered address line 1: {}", address);
    }

    public void enterAddressLine2(String address) {
        type(addressLine2Input, address);
        logger.info("Entered address line 2: {}", address);
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

    // ─── Payment actions ──────────────────────────────────────────────────────

    public void enterCardNumber(String cardNumber) {
        type(cardNumberInput, cardNumber);
        logger.info("Entered card number: [masked]");
    }

    public void enterExpiry(String expiry) {
        type(expiryInput, expiry);
        logger.info("Entered expiry: {}", expiry);
    }

    public void enterCvv(String cvv) {
        type(cvvInput, cvv);
        logger.info("Entered CVV: [masked]");
    }

    public void enterCardholderName(String name) {
        type(cardholderNameInput, name);
        logger.info("Entered cardholder name: {}", name);
    }

    // ─── Gift card actions ────────────────────────────────────────────────────

    public boolean isGiftCardInputDisabled() {
        try {
            WebElement el = driver.findElement(GIFT_CARD_INPUT_LOC);
            boolean disabled = !el.isEnabled() || "true".equals(el.getAttribute("disabled"));
            logger.info("Gift card input disabled: {}", disabled);
            return disabled;
        } catch (Exception e) {
            logger.warn("Gift card input element not found: {}", e.getMessage());
            return true;
        }
    }

    public boolean isGiftCardRestrictionMessageDisplayed() {
        boolean visible = WaitUtils.isElementVisible(
                By.cssSelector(".gift-card-restriction, [data-testid='gift-card-disabled-msg'], .gift-card-message"), 5);
        logger.info("Gift card restriction message displayed: {}", visible);
        return visible;
    }

    public void attemptTypeInGiftCardField(String code) {
        try {
            WebElement el = driver.findElement(GIFT_CARD_INPUT_LOC);
            el.sendKeys(code);
            logger.info("Attempted to type in gift card field (field may be disabled)");
        } catch (Exception e) {
            logger.info("Gift card field did not accept input (expected when disabled): {}", e.getMessage());
        }
    }

    // ─── Order submission ─────────────────────────────────────────────────────

    public void clickPlaceOrder() {
        scrollToElement(placeOrderButton);
        click(placeOrderButton);
        logger.info("Clicked Place Order button");
    }

    // ─── Post-submission state ────────────────────────────────────────────────

    public boolean isOrderConfirmationDisplayed() {
        boolean visible = WaitUtils.isElementVisible(ORDER_CONFIRMATION_LOC, 15);
        logger.info("Order confirmation displayed: {}", visible);
        return visible;
    }

    public boolean isOrderPendingVerificationDisplayed() {
        boolean visible = WaitUtils.isElementVisible(PENDING_VERIFICATION_LOC, 15);
        logger.info("Order pending-verification message displayed: {}", visible);
        return visible;
    }

    public String getOrderReferenceNumber() {
        try {
            return getText(orderReferenceNumber);
        } catch (Exception e) {
            logger.warn("Could not read order reference number: {}", e.getMessage());
            return "";
        }
    }
}
