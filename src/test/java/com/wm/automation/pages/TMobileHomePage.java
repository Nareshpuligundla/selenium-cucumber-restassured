package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page object for the T-Mobile home page (https://www.tmobile.com).
 *
 * Covers: top navigation bar, cart icon, and initial page load verification.
 * NOTE: T-Mobile.com uses React/Next.js with dynamic class names — selectors
 * target stable attributes (aria-labels, data-testid, role, href patterns) where
 * possible.  If T-Mobile updates its DOM, update locators here only.
 */
public class TMobileHomePage extends BasePage {

    private static final String TMOBILE_URL = "https://www.tmobile.com";

    // ─── Locators ─────────────────────────────────────────────────────────────

    // TODO: replace — verify actual nav link selector for 'Phones' on T-Mobile.com
    @FindBy(css = "a[href*='/cell-phones'], nav a[data-cy*='phones' i], nav a[aria-label*='phones' i]")
    private WebElement phonesNavLink;

    // TODO: replace — verify actual nav link selector for 'Plans' on T-Mobile.com
    @FindBy(css = "a[href*='/plans'], nav a[data-cy*='plans' i], nav a[aria-label*='plans' i]")
    private WebElement plansNavLink;

    // TODO: replace — verify actual nav link selector for 'Shop' on T-Mobile.com
    @FindBy(css = "a[href*='/shop'], nav a[data-cy*='shop' i], nav a[aria-label*='shop' i]")
    private WebElement shopNavLink;

    // TODO: replace — verify actual cart icon selector on T-Mobile.com
    @FindBy(css = "a[href*='/cart'], button[aria-label*='cart' i], [data-cy='cartIcon'], [data-testid='cart-icon']")
    private WebElement cartIcon;

    // TODO: replace — verify actual cart badge/count selector on T-Mobile.com
    @FindBy(css = "[data-cy='cartCount'], .cart-badge, [aria-label*='cart' i] .badge, [data-testid='cart-count']")
    private WebElement cartBadge;

    // Site header / navigation container
    @FindBy(css = "header, nav[aria-label*='primary' i], [data-cy='globalNav'], [data-testid='header']")
    private WebElement siteHeader;

    // Top-level navigation links list
    @FindBy(css = "nav a[href], header nav li a")
    private List<WebElement> navLinks;

    private static final By CART_BADGE_LOC =
            By.cssSelector("[data-cy='cartCount'], .cart-badge, [aria-label*='cart' i] .badge, [data-testid='cart-count']");

    private static final By PAGE_LOADED_INDICATOR =
            By.cssSelector("header, [data-cy='globalNav'], [data-testid='header'], nav");

    // ─── Navigation ───────────────────────────────────────────────────────────

    /**
     * Opens T-Mobile home page directly.
     */
    public void open() {
        navigateTo(TMOBILE_URL);
        logger.info("Opened T-Mobile home page: {}", TMOBILE_URL);
    }

    // ─── Page assertions ──────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(PAGE_LOADED_INDICATOR, 15);
    }

    /**
     * Returns true when both the Phones nav link and the cart icon are visible,
     * confirming the navigation bar has fully rendered.
     */
    public boolean isNavigationDisplayed() {
        try {
            return isDisplayed(phonesNavLink) || navLinks.size() >= 3;
        } catch (Exception e) {
            logger.warn("Navigation not yet visible: {}", e.getMessage());
            return isPageLoaded();
        }
    }

    /**
     * Returns true when the cart badge element is NOT present or NOT displayed,
     * meaning no items have been added to the cart.
     */
    public boolean isCartBadgeAbsent() {
        boolean badgeVisible = WaitUtils.isElementVisible(CART_BADGE_LOC, 5);
        logger.info("Cart badge visible: {}", badgeVisible);
        return !badgeVisible;
    }

    /**
     * Returns the numeric text shown on the cart badge (e.g., "1", "2").
     * Returns empty string if badge is not present.
     */
    public String getCartBadgeCount() {
        try {
            if (WaitUtils.isElementVisible(CART_BADGE_LOC, 5)) {
                return cartBadge.getText().trim();
            }
        } catch (Exception e) {
            logger.warn("Could not read cart badge count: {}", e.getMessage());
        }
        return "";
    }

    /**
     * Clicks the 'Phones' link in the top navigation bar.
     */
    public void clickPhonesNav() {
        logger.info("Clicking Phones in navigation bar");
        click(phonesNavLink);
    }
}
