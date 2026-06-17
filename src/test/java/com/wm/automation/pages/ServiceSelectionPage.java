package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Represents the service-type selection page that appears after a valid address is
 * submitted on the WM.com home page.
 *
 * Users choose between:
 *   Residential Trash & Recycling  |  Commercial  |  Dumpster Rental
 */
public class ServiceSelectionPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(css = "h1, h2.page-title, [data-testid='selection-title']")
    private WebElement pageHeading;

    @FindBy(css = "[data-service='residential'], button.residential-card, " +
                  ".service-card[href*='residential'], a[href*='residential-service']")
    private WebElement residentialCard;

    @FindBy(css = "[data-service='commercial'], button.commercial-card, " +
                  ".service-card[href*='commercial'], a[href*='commercial-service']")
    private WebElement commercialCard;

    @FindBy(css = "[data-service='dumpster'], button.dumpster-card, " +
                  ".service-card[href*='dumpster'], a[href*='dumpster-rental']")
    private WebElement dumpsterCard;

    @FindBy(css = ".service-card, .service-option, [data-testid='service-card']")
    private List<WebElement> allServiceCards;

    @FindBy(css = ".confirmed-address, .address-badge, [data-testid='address-display']")
    private WebElement confirmedAddressDisplay;

    @FindBy(css = "a.change-address, button[aria-label*='change address' i], .edit-address-link")
    private WebElement changeAddressLink;

    private static final By PAGE_READY_LOC =
            By.cssSelector(".service-card, .service-option, [data-testid='service-card'], " +
                           "h1, h2");

    // ─── Actions ──────────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(PAGE_READY_LOC, 15);
    }

    public void selectResidential() {
        scrollToElement(residentialCard);
        click(residentialCard);
        logger.info("Selected Residential service");
    }

    public void selectCommercial() {
        scrollToElement(commercialCard);
        click(commercialCard);
        logger.info("Selected Commercial service");
    }

    public void selectDumpsterRental() {
        scrollToElement(dumpsterCard);
        click(dumpsterCard);
        logger.info("Selected Dumpster Rental service");
    }

    public boolean isResidentialCardVisible() {
        return isDisplayed(residentialCard);
    }

    public boolean isCommercialCardVisible() {
        return isDisplayed(commercialCard);
    }

    public boolean isDumpsterCardVisible() {
        return isDisplayed(dumpsterCard);
    }

    public int getServiceCardCount() {
        return allServiceCards.size();
    }

    public String getPageHeading() {
        try {
            return getText(pageHeading);
        } catch (Exception e) {
            logger.warn("Could not read page heading: {}", e.getMessage());
            return "";
        }
    }

    public String getConfirmedAddress() {
        try {
            return getText(confirmedAddressDisplay);
        } catch (Exception e) {
            return "";
        }
    }

    public void changeAddress() {
        click(changeAddressLink);
        logger.info("Clicked Change Address link");
    }

    public boolean isChangeAddressLinkVisible() {
        return isDisplayed(changeAddressLink);
    }
}
