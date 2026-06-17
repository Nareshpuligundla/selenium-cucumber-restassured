package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Represents the WM.com My Account / Dashboard page (post-login).
 * Covers: account overview, service management, billing, notifications, sign-out.
 */
public class MyAccountPage extends BasePage {

    private static final String ACCOUNT_PATH = "/my-account";

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(css = "h1.account-title, [data-testid='account-heading'], .dashboard-title")
    private WebElement accountHeading;

    @FindBy(css = ".account-name, [data-testid='welcome-msg'], .user-greeting")
    private WebElement welcomeMessage;

    // ─ Service management tab/link ────────────────────────────────────────────
    @FindBy(css = "a[href*='services'], nav a[data-section='services'], .account-nav-services")
    private WebElement servicesNavLink;

    // ─ Billing / payments tab ─────────────────────────────────────────────────
    @FindBy(css = "a[href*='billing'], nav a[data-section='billing'], .account-nav-billing")
    private WebElement billingNavLink;

    // ─ Profile / settings tab ─────────────────────────────────────────────────
    @FindBy(css = "a[href*='profile'], a[href*='settings'], .account-nav-profile")
    private WebElement profileNavLink;

    // ─ Notifications tab ─────────────────────────────────────────────────────
    @FindBy(css = "a[href*='notifications'], .account-nav-notifications")
    private WebElement notificationsNavLink;

    // ─ Account menu items list ────────────────────────────────────────────────
    @FindBy(css = ".account-menu li a, .account-sidebar a, aside nav a")
    private List<WebElement> accountMenuItems;

    // ─ Service cards ─────────────────────────────────────────────────────────
    @FindBy(css = ".service-card, .service-summary, [data-testid='service-item']")
    private List<WebElement> serviceCards;

    // ─ Balance/amount due ────────────────────────────────────────────────────
    @FindBy(css = ".balance-due, .amount-due, [data-testid='balance'], .current-balance")
    private WebElement balanceDue;

    // ─ Pay now button ─────────────────────────────────────────────────────────
    @FindBy(css = "button.pay-now, a[href*='pay'], [data-testid='pay-btn']")
    private WebElement payNowButton;

    // ─ Sign out ───────────────────────────────────────────────────────────────
    @FindBy(css = "a[href*='logout'], button.sign-out, [data-testid='sign-out']")
    private WebElement signOutLink;

    // ─ Next pickup ────────────────────────────────────────────────────────────
    @FindBy(css = ".next-pickup, .pickup-date, [data-testid='next-pickup']")
    private WebElement nextPickupDate;

    private static final By PAGE_READY_LOC =
            By.cssSelector("h1, .account-title, .dashboard-title, .account-menu");

    // ─── Actions ──────────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(PAGE_READY_LOC, 15);
    }

    public void open() {
        navigateTo(com.wm.automation.utils.ConfigReader.getInstance().getBaseUrl() + ACCOUNT_PATH);
        logger.info("Opened My Account page");
    }

    public String getWelcomeMessage() {
        try {
            return getText(welcomeMessage);
        } catch (Exception e) {
            return "";
        }
    }

    public String getAccountHeading() {
        try {
            return getText(accountHeading);
        } catch (Exception e) {
            return "";
        }
    }

    public void goToServices() {
        click(servicesNavLink);
        logger.info("Navigated to Services section");
    }

    public void goToBilling() {
        click(billingNavLink);
        logger.info("Navigated to Billing section");
    }

    public void goToProfile() {
        click(profileNavLink);
        logger.info("Navigated to Profile section");
    }

    public void goToNotifications() {
        click(notificationsNavLink);
        logger.info("Navigated to Notifications section");
    }

    public int getServiceCount() {
        return serviceCards.size();
    }

    public boolean hasActiveServices() {
        return !serviceCards.isEmpty();
    }

    public String getBalanceDue() {
        try {
            return getText(balanceDue);
        } catch (Exception e) {
            return "0.00";
        }
    }

    public void clickPayNow() {
        click(payNowButton);
        logger.info("Clicked Pay Now button");
    }

    public boolean isPayNowButtonVisible() {
        return isDisplayed(payNowButton);
    }

    public String getNextPickupDate() {
        try {
            return getText(nextPickupDate);
        } catch (Exception e) {
            return "";
        }
    }

    public void signOut() {
        scrollToElement(signOutLink);
        click(signOutLink);
        logger.info("Clicked Sign Out");
    }

    public boolean isSignOutLinkVisible() {
        return isDisplayed(signOutLink);
    }

    public int getAccountMenuItemCount() {
        return accountMenuItems.size();
    }
}
