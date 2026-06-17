package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Represents the persistent header / navigation bar present on every WM.com page.
 * Covers: logo, primary nav links, account icon, hamburger menu (mobile).
 */
public class NavigationPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(css = "a.wm-logo, header a[href='/'], img[alt*='WM' i]")
    private WebElement wmLogoLink;

    @FindBy(css = "nav a[href*='residential'], a[data-nav='residential']")
    private WebElement residentialNavLink;

    @FindBy(css = "nav a[href*='commercial'], a[data-nav='commercial']")
    private WebElement commercialNavLink;

    @FindBy(css = "nav a[href*='dumpster'], a[data-nav='dumpster']")
    private WebElement dumpsterRentalNavLink;

    @FindBy(css = "nav a[href*='recycling'], a[data-nav='recycling']")
    private WebElement recyclingNavLink;

    @FindBy(css = "a[href*='login'], a[aria-label*='sign in' i], button[data-testid='login-btn']")
    private WebElement signInLink;

    @FindBy(css = "a[href*='my-account'], a[aria-label*='account' i], .account-icon")
    private WebElement myAccountLink;

    @FindBy(css = "button[aria-label*='menu' i], .hamburger-btn, .nav-toggle, button.menu-btn")
    private WebElement hamburgerMenuButton;

    @FindBy(css = "nav[class*='mobile'], .mobile-menu, .drawer-menu, aside[role='navigation']")
    private WebElement mobileMenuDrawer;

    @FindBy(css = "input[placeholder*='search' i], .site-search-input, [aria-label*='search' i]")
    private WebElement siteSearchInput;

    @FindBy(css = "nav ul li a, header nav a")
    private List<WebElement> allNavLinks;

    private static final By HEADER_LOC = By.cssSelector("header, [role='banner']");
    private static final By MOBILE_MENU_LOC =
            By.cssSelector("nav[class*='mobile'], .mobile-menu, .drawer-menu, aside[role='navigation']");

    // ─── Actions ──────────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(HEADER_LOC, 10);
    }

    public void clickLogo() {
        click(wmLogoLink);
        logger.info("Clicked WM logo – navigating to home");
    }

    public void goToResidentialServices() {
        click(residentialNavLink);
        logger.info("Navigated to Residential Services via nav link");
    }

    public void goToCommercialServices() {
        click(commercialNavLink);
        logger.info("Navigated to Commercial Services via nav link");
    }

    public void goToDumpsterRental() {
        click(dumpsterRentalNavLink);
        logger.info("Navigated to Dumpster Rental via nav link");
    }

    public void goToRecycling() {
        click(recyclingNavLink);
        logger.info("Navigated to Recycling via nav link");
    }

    public void clickSignIn() {
        click(signInLink);
        logger.info("Clicked Sign In link");
    }

    public void clickMyAccount() {
        click(myAccountLink);
        logger.info("Clicked My Account link");
    }

    public void openMobileMenu() {
        click(hamburgerMenuButton);
        WaitUtils.waitForVisibility(mobileMenuDrawer);
        logger.info("Opened mobile hamburger menu");
    }

    public boolean isMobileMenuOpen() {
        return WaitUtils.isElementVisible(MOBILE_MENU_LOC, 5);
    }

    public boolean isSignInLinkVisible() {
        return isDisplayed(signInLink);
    }

    public boolean isMyAccountLinkVisible() {
        return isDisplayed(myAccountLink);
    }

    public boolean isLogoVisible() {
        return isDisplayed(wmLogoLink);
    }

    public boolean isHamburgerMenuVisible() {
        return isDisplayed(hamburgerMenuButton);
    }

    public int getNavLinkCount() {
        return allNavLinks.size();
    }

    public void searchFromNav(String keyword) {
        type(siteSearchInput, keyword);
        siteSearchInput.sendKeys(org.openqa.selenium.Keys.ENTER);
        logger.info("Searched '{}' via header search box", keyword);
    }
}
