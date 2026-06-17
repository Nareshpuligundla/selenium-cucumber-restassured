package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page object for the WM.com service-request flow.
 * Covers the results/options page that appears after a valid address is submitted.
 */
public class ServiceRequestPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(css = ".service-options, .service-type-list, [data-testid='service-options']")
    private WebElement serviceOptionsContainer;

    @FindBy(css = ".error-message, .alert-danger, [data-testid='error-msg'], .no-results")
    private WebElement errorMessage;

    @FindBy(css = ".address-confirmation, .confirmed-address, [data-testid='address-confirm']")
    private WebElement addressConfirmation;

    @FindBy(css = "button.residential, [data-service='residential'], .service-type-residential")
    private WebElement residentialServiceOption;

    @FindBy(css = "button.commercial, [data-service='commercial'], .service-type-commercial")
    private WebElement commercialServiceOption;

    private static final By SERVICE_OPTIONS_LOC =
            By.cssSelector(".service-options, .service-type-list, [data-testid='service-options'], " +
                           ".cart-options, .pickup-options, h2, h3");

    private static final By ERROR_OR_NO_RESULTS_LOC =
            By.cssSelector(".error-message, .alert-danger, .no-results, " +
                           "[data-testid='error-msg'], .address-not-found");

    // ─── Page actions ─────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        try {
            WaitUtils.waitForPresence(SERVICE_OPTIONS_LOC);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areServiceOptionsDisplayed() {
        boolean visible = WaitUtils.isElementVisible(SERVICE_OPTIONS_LOC, 15);
        logger.info("Service options displayed: {}", visible);
        return visible;
    }

    public boolean isErrorOrNoResultsDisplayed() {
        boolean visible = WaitUtils.isElementVisible(ERROR_OR_NO_RESULTS_LOC, 10);
        logger.info("Error/no-results message displayed: {}", visible);
        return visible;
    }

    public boolean isAddressAccepted() {
        // Either service options appear OR address confirmation text is shown
        return areServiceOptionsDisplayed() || isDisplayed(addressConfirmation);
    }

    public void selectResidentialService() {
        click(residentialServiceOption);
        logger.info("Selected residential service option");
    }

    public void selectCommercialService() {
        click(commercialServiceOption);
        logger.info("Selected commercial service option");
    }

    public String getErrorMessageText() {
        try {
            return getText(errorMessage);
        } catch (Exception e) {
            logger.warn("Could not read error message text: {}", e.getMessage());
            return "";
        }
    }
}
