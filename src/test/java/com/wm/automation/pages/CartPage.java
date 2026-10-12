package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    @FindBy(css = ".cart-subtotal, .subtotal-amount, [data-testid='cart-subtotal']")
    private WebElement subtotalElement;

    @FindBy(css = "button.proceed-to-checkout, a.proceed-to-checkout, [data-testid='proceed-to-checkout'], button[aria-label*='checkout' i]")
    private WebElement proceedToCheckoutButton;

    @FindBy(css = ".cart-items, .cart-container, [data-testid='cart-items'], #cart")
    private WebElement cartContainer;

    private static final By CART_READY_LOC =
            By.cssSelector(".cart-items, .cart-container, [data-testid='cart-items'], #cart, .cart-summary");

    private static final By PROCEED_BTN_LOC =
            By.cssSelector("button.proceed-to-checkout, a.proceed-to-checkout, [data-testid='proceed-to-checkout']");

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(CART_READY_LOC, 15);
    }

    public void open(String cartUrl) {
        navigateTo(cartUrl);
        logger.info("Opened cart page: {}", cartUrl);
    }

    public String getSubtotal() {
        try {
            return getText(subtotalElement);
        } catch (Exception e) {
            logger.warn("Could not read cart subtotal: {}", e.getMessage());
            return "";
        }
    }

    public void clickProceedToCheckout() {
        WaitUtils.waitForClickability(proceedToCheckoutButton);
        click(proceedToCheckoutButton);
        logger.info("Clicked Proceed to Checkout");
    }

    public boolean isProceedToCheckoutButtonVisible() {
        return WaitUtils.isElementVisible(PROCEED_BTN_LOC, 10);
    }
}
