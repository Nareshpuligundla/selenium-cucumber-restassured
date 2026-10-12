package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderConfirmationPage extends BasePage {

    @FindBy(css = ".order-confirmed-msg, .confirmation-heading, [data-testid='order-placed-msg'], h1.success")
    private WebElement orderPlacedMessage;

    @FindBy(css = ".order-reference, .order-number, [data-testid='order-reference'], [class*='order-ref']")
    private WebElement orderReferenceElement;

    @FindBy(css = ".pending-verification-msg, [data-testid='avs-pending-msg'], .avs-hold-message")
    private WebElement pendingVerificationMessage;

    @FindBy(css = ".tax-amount, [data-testid='tax'], .order-tax")
    private WebElement taxElement;

    @FindBy(css = ".shipping-cost, [data-testid='shipping'], .order-shipping")
    private WebElement shippingCostElement;

    @FindBy(css = ".grand-total, [data-testid='grand-total'], .order-total")
    private WebElement grandTotalElement;

    @FindBy(css = ".estimated-delivery, [data-testid='delivery-date'], .delivery-estimate")
    private WebElement estimatedDeliveryDateElement;

    private static final By PAGE_READY_LOC =
            By.cssSelector(".order-confirmed-msg, .order-reference, [data-testid='order-placed-msg'], " +
                           ".pending-verification-msg, [data-testid='avs-pending-msg'], h1");

    private static final By ORDER_PLACED_LOC =
            By.cssSelector(".order-confirmed-msg, [data-testid='order-placed-msg'], h1.success");

    private static final By PENDING_VERIFICATION_LOC =
            By.cssSelector(".pending-verification-msg, [data-testid='avs-pending-msg'], .avs-hold-message");

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(PAGE_READY_LOC, 20);
    }

    public boolean isOrderPlacedMessageDisplayed() {
        return WaitUtils.isElementVisible(ORDER_PLACED_LOC, 10);
    }

    public boolean isPendingVerificationMessageDisplayed() {
        return WaitUtils.isElementVisible(PENDING_VERIFICATION_LOC, 10);
    }

    public String getConfirmationMessage() {
        try {
            return getText(orderPlacedMessage);
        } catch (Exception e) {
            logger.warn("Could not read confirmation message: {}", e.getMessage());
            return "";
        }
    }

    public String getOrderReferenceNumber() {
        try {
            return getText(orderReferenceElement);
        } catch (Exception e) {
            logger.warn("Could not read order reference number: {}", e.getMessage());
            return "";
        }
    }

    public boolean hasTaxDisplayed() {
        try {
            return isDisplayed(taxElement);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasShippingCostDisplayed() {
        try {
            return isDisplayed(shippingCostElement);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasGrandTotalDisplayed() {
        try {
            return isDisplayed(grandTotalElement);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasEstimatedDeliveryDateDisplayed() {
        try {
            return isDisplayed(estimatedDeliveryDateElement);
        } catch (Exception e) {
            return false;
        }
    }
}
