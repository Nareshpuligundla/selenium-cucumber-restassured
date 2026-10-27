package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Page object for the Worktop product listing page and its pagination controls.
 */
public class ProductListingPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    // TODO: replace - confirm 'Products' top navigation link selector
    @FindBy(css = "nav a[href*='products'], a[data-testid='nav-products']")
    private WebElement productsNavLink;

    // TODO: replace - confirm pagination summary text selector ('Page 1 of 3')
    @FindBy(css = ".pagination-summary, [data-testid='pagination-summary'], .page-indicator")
    private WebElement paginationSummary;

    // TODO: replace - confirm Previous button selector
    @FindBy(css = "button.pagination-prev, [data-testid='pagination-prev'], button[aria-label='Previous']")
    private WebElement previousButton;

    // TODO: replace - confirm Next button selector
    @FindBy(css = "button.pagination-next, [data-testid='pagination-next'], button[aria-label='Next']")
    private WebElement nextButton;

    // TODO: replace - confirm 'Add to Cart' button selector on product cards
    @FindBy(css = ".product-card button.add-to-cart, [data-testid='add-to-cart']")
    private List<WebElement> addToCartButtons;

    // TODO: replace - confirm cart icon count badge selector
    @FindBy(css = ".cart-count, [data-testid='cart-count'], .cart-icon .badge")
    private WebElement cartCount;

    private static final By PRODUCT_CARD_LOC =
            By.cssSelector(".product-card, [data-testid='product-card']");

    // TODO: replace - confirm product name selector inside a card
    private static final By PRODUCT_NAME_LOC =
            By.cssSelector(".product-name, [data-testid='product-name']");

    // ─── Actions ──────────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(PRODUCT_CARD_LOC, 15);
    }

    public void openViaTopNav() {
        click(productsNavLink);
        logger.info("Opened product listing via top navigation");
    }

    public int getProductCardCount() {
        isPageLoaded();
        return ElementUtils.findAll(PRODUCT_CARD_LOC).size();
    }

    public String getPaginationText() {
        try {
            return getText(paginationSummary);
        } catch (Exception e) {
            logger.warn("Could not read pagination text: {}", e.getMessage());
            return "";
        }
    }

    public boolean isPreviousEnabled() {
        return previousButton.isEnabled()
                && !"true".equalsIgnoreCase(previousButton.getAttribute("aria-disabled"));
    }

    public boolean isNextEnabled() {
        return nextButton.isEnabled()
                && !"true".equalsIgnoreCase(nextButton.getAttribute("aria-disabled"));
    }

    public void clickNext() {
        scrollToElement(nextButton);
        click(nextButton);
        logger.info("Clicked Next page");
    }

    public void clickPage(int pageNumber) {
        // TODO: replace - confirm numbered page link selector
        By pageLoc = By.xpath("//*[contains(@class,'pagination') or @data-testid='pagination']"
                + "//*[self::a or self::button][normalize-space()='" + pageNumber + "']");
        click(pageLoc);
        logger.info("Clicked page {}", pageNumber);
    }

    public void addFirstProductToCart() {
        WebElement first = addToCartButtons.get(0);
        scrollToElement(first);
        click(first);
        logger.info("Added first product to cart");
    }

    public int getCartCount() {
        try {
            return Integer.parseInt(getText(cartCount).trim());
        } catch (Exception e) {
            logger.warn("Could not read cart count: {}", e.getMessage());
            return 0;
        }
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement el : ElementUtils.findAll(PRODUCT_NAME_LOC)) {
            names.add(el.getText().trim());
        }
        return names;
    }
}
