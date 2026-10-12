package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents the order confirmation page at /checkout/confirmation.
 * Covers: confirmation heading, order ID, guest email, and payment method display.
 */
public class OrderConfirmationPage extends BasePage {

    private static final String CONFIRMATION_PATH = "/checkout/confirmation";

    // ─── Locators ─────────────────────────────────────────────────────────────

    // TODO: replace - confirm exact selector for the "Order Confirmed!" heading with dev team
    @FindBy(css = "h1[data-testid='confirmation-heading'], h1.confirmation-title, " +
                  ".order-confirmed-heading, [data-testid='order-success'] h1")
    private WebElement confirmationHeading;

    // TODO: replace - confirm exact selector for the order ID element on the confirmation page
    @FindBy(css = "[data-testid='order-id'], .order-id, .order-number, " +
                  "#order-id, .confirmation-order-id, [class*='order-id']")
    private WebElement orderIdElement;

    // TODO: replace - confirm exact selector for the guest email shown on the confirmation page
    @FindBy(css = "[data-testid='confirmation-email'], .confirmation-email, " +
                  ".guest-email, .email-confirmation, [class*='confirmation-email']")
    private WebElement guestEmailElement;

    // TODO: replace - confirm exact selector for the payment method section on the confirmation page
    @FindBy(css = "[data-testid='payment-method'], .payment-method, " +
                  ".payment-summary, .payment-info, [class*='payment-method']")
    private WebElement paymentMethodElement;

    private static final By CONFIRMATION_HEADING_LOC =
            By.cssSelector("h1[data-testid='confirmation-heading'], h1.confirmation-title, " +
                           ".order-confirmed-heading, [data-testid='order-success'] h1");

    // ─── Page readiness ────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        try {
            WaitUtils.waitForUrlContains(CONFIRMATION_PATH);
            WaitUtils.waitForPresence(CONFIRMATION_HEADING_LOC);
            return isDisplayed(confirmationHeading);
        } catch (Exception e) {
            logger.warn("Confirmation page load check failed: {}", e.getMessage());
            return false;
        }
    }

    // ─── Page actions / assertions ─────────────────────────────────────────────

    public boolean isConfirmationHeadingDisplayed() {
        return isDisplayed(confirmationHeading);
    }

    public String getConfirmationHeadingText() {
        try {
            return getText(confirmationHeading);
        } catch (Exception e) {
            logger.warn("Could not read confirmation heading: {}", e.getMessage());
            return "";
        }
    }

    public boolean isOrderIdDisplayed() {
        return isDisplayed(orderIdElement);
    }

    public String getOrderId() {
        try {
            return getText(orderIdElement);
        } catch (Exception e) {
            logger.warn("Could not read order ID: {}", e.getMessage());
            return "";
        }
    }

    public boolean isOrderIdPresent() {
        String orderId = getOrderId();
        return orderId != null && !orderId.trim().isEmpty();
    }

    public String getGuestEmail() {
        try {
            return getText(guestEmailElement);
        } catch (Exception e) {
            logger.warn("Could not read guest email on confirmation page: {}", e.getMessage());
            return "";
        }
    }

    public boolean containsGuestEmail(String email) {
        return getGuestEmail().contains(email);
    }

    public String getPaymentMethod() {
        try {
            return getText(paymentMethodElement);
        } catch (Exception e) {
            logger.warn("Could not read payment method: {}", e.getMessage());
            return "";
        }
    }

    public boolean isUrlConfirmationPage() {
        return getCurrentUrl().contains(CONFIRMATION_PATH);
    }
}
