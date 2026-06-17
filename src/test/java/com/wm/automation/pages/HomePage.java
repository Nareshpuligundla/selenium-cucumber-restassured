package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object for the WM.com home page.
 *
 * NOTE: Locators are based on the WM.com DOM as of mid-2024.  WM.com is a
 * heavily A/B-tested site; if locators break after a deploy, update them here
 * without touching any step-definition class.
 */
public class HomePage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(css = "input[placeholder*='address' i], input[aria-label*='address' i], input[name*='address' i]")
    private WebElement addressInput;

    @FindBy(css = "button[aria-label*='search' i], button[type='submit'], button.search-btn")
    private WebElement searchButton;

    @FindBy(css = "header, nav[role='navigation'], .wm-header, #header")
    private WebElement siteHeader;

    @FindBy(css = ".hero-section, .homepage-hero, main h1, [data-testid='hero']")
    private WebElement heroSection;

    @FindBy(css = ".nav-logo, .wm-logo, header img[alt*='WM' i]")
    private WebElement wmLogo;

    private static final By ADDRESS_INPUT_LOC =
            By.cssSelector("input[placeholder*='address' i], input[aria-label*='address' i]");
    private static final By PAGE_LOADED_INDICATOR =
            By.cssSelector("body.page-loaded, header, nav, main");

    // ─── Page actions ─────────────────────────────────────────────────────────

    public void open() {
        navigateToBaseUrl();
        logger.info("Opened WM home page");
    }

    @Override
    public boolean isPageLoaded() {
        try {
            WaitUtils.waitForPresence(PAGE_LOADED_INDICATOR);
            String title = getPageTitle();
            boolean loaded = title != null && !title.isEmpty();
            logger.info("Home page loaded: {} (title='{}')", loaded, title);
            return loaded;
        } catch (Exception e) {
            logger.warn("Home page load check failed: {}", e.getMessage());
            return false;
        }
    }

    public boolean isAddressInputVisible() {
        boolean visible = WaitUtils.isElementVisible(ADDRESS_INPUT_LOC, 10);
        logger.info("Address input visible: {}", visible);
        return visible;
    }

    public void enterAddress(String address) {
        try {
            WebElement input = WaitUtils.waitForVisibility(addressInput);
            input.clear();
            input.sendKeys(address);
            logger.info("Entered address: {}", address);
        } catch (Exception e) {
            logger.warn("Could not find address input via FindBy – trying fallback locator");
            type(ADDRESS_INPUT_LOC, address);
        }
    }

    public void submitAddress() {
        try {
            WaitUtils.waitForClickability(addressInput);
            addressInput.sendKeys(Keys.ENTER);
            logger.info("Submitted address via ENTER key");
        } catch (Exception e) {
            logger.warn("Address submit via ENTER failed, trying search button");
            try {
                click(searchButton);
            } catch (Exception ex) {
                logger.warn("Search button click also failed: {}", ex.getMessage());
            }
        }
    }

    public void enterAddressAndSubmit(String address) {
        enterAddress(address);
        submitAddress();
    }

    public boolean isLogoVisible() {
        return isDisplayed(wmLogo);
    }

    public boolean isHeaderVisible() {
        return isDisplayed(siteHeader);
    }

    public String getTitle() {
        return getPageTitle();
    }
}
