package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DashboardPage extends BasePage {

    // TODO: replace - confirm CSS selector for the dashboard container / heading
    @FindBy(css = "h1.dashboard-title, [data-testid='dashboard-heading'], .dashboard-container h1")
    private WebElement dashboardHeading;

    // TODO: replace - confirm CSS selector for in-progress application cards/links
    @FindBy(css = ".application-card.in-progress, [data-testid='in-progress-application'], " +
                  "a[href*='/application/'], .application-list .in-progress")
    private List<WebElement> inProgressApplications;

    private static final By PAGE_READY_LOC =
            By.cssSelector("h1, .dashboard-container, .dashboard-title, [data-testid='dashboard-heading']");

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(PAGE_READY_LOC, 15);
    }

    public void clickInProgressApplication() {
        WaitUtils.waitForVisibility(inProgressApplications.get(0));
        click(inProgressApplications.get(0));
        logger.info("Clicked in-progress application");
    }
}
