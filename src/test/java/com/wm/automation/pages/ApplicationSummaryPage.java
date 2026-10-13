package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ApplicationSummaryPage extends BasePage {

    // TODO: replace - confirm CSS selector for the Application Summary h1 heading
    @FindBy(css = "h1.summary-title, [data-testid='summary-heading'], h1")
    private WebElement pageHeading;

    // TODO: replace - confirm CSS selector for the disclosure-accepted status indicator
    @FindBy(css = ".disclosure-accepted, [data-testid='disclosure-accepted'], " +
                  ".status-indicator.accepted, [aria-label*='Disclosure Accepted']")
    private WebElement disclosureAcceptedIndicator;

    private static final By PAGE_READY_LOC =
            By.cssSelector("h1, .summary-title, [data-testid='summary-heading'], .summary-container");

    private static final By DISCLOSURE_ACCEPTED_LOC =
            By.cssSelector(".disclosure-accepted, [data-testid='disclosure-accepted'], " +
                           ".status-indicator.accepted, [aria-label*='Disclosure Accepted']");

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(PAGE_READY_LOC, 15);
    }

    public String getPageHeading() {
        try {
            return getText(pageHeading);
        } catch (Exception e) {
            logger.warn("Could not read Application Summary page heading: {}", e.getMessage());
            return "";
        }
    }

    public boolean isDisclosureAcceptedIndicatorDisplayed() {
        return WaitUtils.isElementVisible(DISCLOSURE_ACCEPTED_LOC, 10);
    }
}
