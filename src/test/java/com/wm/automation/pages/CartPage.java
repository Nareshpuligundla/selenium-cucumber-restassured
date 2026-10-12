package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Represents the shopping cart page at https://checkout.example.com/cart.
 * Covers: cart item display, totals, and proceeding to checkout.
 */
public class CartPage extends BasePage {

    // TODO: replace - confirm the exact cart URL with the dev team; may differ per environment
    private static final String CART_URL = "https://checkout.example.com/cart";

    // TODO: replace - confirm the exact URL for seeding a subscription product into the cart
    private static final String SUBSCRIPTION_CART_URL =
            "https://checkout.example.com/cart?sku=SUB-MONTHLY-001";

    // ─── Locators ─────────────────────────────────────────────────────────────

    // TODO: replace - update with the actual Proceed to Checkout button selector once DOM is known
    @FindBy(css = "button[data-testid='proceed-to-checkout'], button.proceed-checkout, " +
                  "a.checkout-btn, a[href*='checkout'], button.btn-checkout")
    private WebElement proceedToCheckoutButton;

    // TODO: replace - update with the actual cart item name selector once DOM is known
    @FindBy(css = ".cart-item-name, [data-testid='product-name'], .product-title, " +
                  ".line-item-title, .item-name")
    private WebElement cartItemName;

    // TODO: replace - update with the actual cart total / subtotal selector once DOM is known
    @FindBy(css = ".cart-total, [data-testid='cart-total'], .order-total, " +
                  ".subtotal-amount, .cart-subtotal")
    private WebElement cartTotal;

    private static final By PROCEED_BTN_LOC =
            By.cssSelector("button[data-testid='proceed-to-checkout'], button.proceed-checkout, " +
                           "a.checkout-btn, a[href*='checkout'], button.btn-checkout");

    // ─── Page actions ─────────────────────────────────────────────────────────

    public void openCart() {
        navigateTo(CART_URL);
        logger.info("Opened standard cart page: {}", CART_URL);
    }

    public void openSubscriptionCart() {
        navigateTo(SUBSCRIPTION_CART_URL);
        logger.info("Opened subscription cart page: {}", SUBSCRIPTION_CART_URL);
    }

    @Override
    public boolean isPageLoaded() {
        try {
            WaitUtils.waitForPresence(PROCEED_BTN_LOC);
            return isDisplayed(proceedToCheckoutButton);
        } catch (Exception e) {
            logger.warn("Cart page load check failed: {}", e.getMessage());
            return false;
        }
    }

    public boolean isProceedToCheckoutButtonVisible() {
        return WaitUtils.isElementVisible(PROCEED_BTN_LOC, 15);
    }

    public void clickProceedToCheckout() {
        click(proceedToCheckoutButton);
        logger.info("Clicked Proceed to Checkout button");
    }

    public String getCartItemName() {
        try {
            return getText(cartItemName);
        } catch (Exception e) {
            logger.warn("Could not read cart item name: {}", e.getMessage());
            return "";
        }
    }

    public String getCartTotal() {
        try {
            return getText(cartTotal);
        } catch (Exception e) {
            logger.warn("Could not read cart total: {}", e.getMessage());
            return "";
        }
    }

    public boolean isItemDisplayed(String productName) {
        return getCartItemName().contains(productName);
    }

    public boolean isTotalDisplayed(String expectedTotal) {
        String total = getCartTotal();
        return total.contains(expectedTotal.replace("$", "")) || total.contains(expectedTotal);
    }
}
