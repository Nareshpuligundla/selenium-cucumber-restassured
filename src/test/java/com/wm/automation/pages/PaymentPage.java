package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * Page object for the Worktop cart, payment form, 3-D Secure modal,
 * confirmation page and order status.
 */
public class PaymentPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    // TODO: replace - confirm cart icon selector
    @FindBy(css = ".cart-icon, [data-testid='cart-icon'], a[href*='cart']")
    private WebElement cartIcon;

    // TODO: replace - confirm Checkout button selector
    @FindBy(css = "button.checkout, [data-testid='checkout-btn']")
    private WebElement checkoutButton;

    // TODO: replace - confirm card number input selector
    @FindBy(css = "input[name='cardNumber'], input[id*='card' i], [data-testid='card-number']")
    private WebElement cardInput;

    // TODO: replace - confirm expiry input selector
    @FindBy(css = "input[name='expiry'], input[id*='expiry' i], [data-testid='expiry']")
    private WebElement expiryInput;

    // TODO: replace - confirm CVV input selector
    @FindBy(css = "input[name='cvv'], input[id*='cvv' i], [data-testid='cvv']")
    private WebElement cvvInput;

    // TODO: replace - confirm Pay button selector
    @FindBy(css = "button.pay-btn, [data-testid='pay-btn']")
    private WebElement payButton;

    // TODO: replace - confirm per-field inline error selectors
    @FindBy(css = "[data-testid='card-error'], #card-error, .card-number-error")
    private WebElement cardError;

    @FindBy(css = "[data-testid='expiry-error'], #expiry-error, .expiry-error")
    private WebElement expiryError;

    @FindBy(css = "[data-testid='cvv-error'], #cvv-error, .cvv-error")
    private WebElement cvvError;

    // TODO: replace - confirm payment banner selector
    @FindBy(css = ".payment-banner, [data-testid='payment-banner'], .alert-danger")
    private WebElement banner;

    // TODO: replace - confirm 3-D Secure modal and its password field / submit button
    @FindBy(css = "[data-testid='3ds-password'], .threeds-modal input[type='password']")
    private WebElement threeDsPassword;

    @FindBy(css = "[data-testid='3ds-submit'], .threeds-modal button")
    private WebElement threeDsSubmit;

    // TODO: replace - confirm confirmation reference number selector
    @FindBy(css = ".payment-reference, [data-testid='payment-reference']")
    private WebElement referenceNumber;

    // TODO: replace - confirm top-nav Orders link and order status selectors
    @FindBy(css = "nav a[href*='orders'], a[data-testid='nav-orders']")
    private WebElement ordersNavLink;

    @FindBy(css = ".order-row, [data-testid='pending-order']")
    private WebElement pendingOrder;

    @FindBy(css = ".order-status, [data-testid='order-status']")
    private WebElement orderStatus;

    private static final By PAY_FORM_LOC =
            By.cssSelector("input[name='cardNumber'], input[id*='card' i], [data-testid='card-number']");

    private static final By THREE_DS_MODAL_LOC =
            By.cssSelector(".threeds-modal, [data-testid='3ds-modal'], [role='dialog']");

    // ─── Actions ──────────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(PAY_FORM_LOC, 15);
    }

    public void checkoutFromCart() {
        click(cartIcon);
        click(checkoutButton);
        logger.info("Opened cart and clicked Checkout");
    }

    public void enterCard(String card) {
        type(cardInput, card);
    }

    public void enterExpiry(String expiry) {
        type(expiryInput, expiry);
    }

    public void enterCvv(String cvv) {
        type(cvvInput, cvv);
    }

    public void clickPay() {
        scrollToElement(payButton);
        click(payButton);
        logger.info("Clicked Pay");
    }

    public void doubleClickPay() {
        new Actions(driver).doubleClick(payButton).perform();
        logger.info("Double-clicked Pay");
    }

    public String getCardError() {
        return safeText(cardError);
    }

    public String getExpiryError() {
        return safeText(expiryError);
    }

    public String getCvvError() {
        return safeText(cvvError);
    }

    public String getBannerText() {
        return safeText(banner);
    }

    public boolean isPayEnabled() {
        return payButton.isEnabled()
                && !"true".equalsIgnoreCase(payButton.getAttribute("aria-disabled"));
    }

    public boolean isThreeDSecureModalDisplayed() {
        return WaitUtils.isElementVisible(THREE_DS_MODAL_LOC, 15);
    }

    public void completeThreeDSecure(String password) {
        type(threeDsPassword, password);
        click(threeDsSubmit);
        logger.info("Submitted 3-D Secure challenge");
    }

    public String getReferenceNumber() {
        return safeText(referenceNumber);
    }

    /** Opens Orders from the top nav and the pending order; returns its status text. */
    public String openPendingOrderStatus() {
        click(ordersNavLink);
        click(pendingOrder);
        return safeText(orderStatus);
    }

    private String safeText(WebElement element) {
        try {
            return getText(element);
        } catch (Exception e) {
            logger.warn("Could not read element text: {}", e.getMessage());
            return "";
        }
    }
}
