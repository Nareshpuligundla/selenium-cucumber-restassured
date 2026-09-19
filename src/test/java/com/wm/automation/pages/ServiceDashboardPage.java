package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page object for the WM.com Service Dashboard page.
 *
 * This page appears after a guest user submits a valid residential address and
 * selects a service type (e.g. "Home") on the WM home page. It displays a list
 * of available service action links such as:
 *   - Start / Stop / Transfer Service
 *   - Repair/Replace Container
 *   - Extra Pickup
 *   - Missed Pickup
 *   - Schedule Bulk Item Pickup
 *
 * NOTE: WM.com is an A/B-tested site; update selectors here if the DOM changes
 * without touching any step-definition class.
 */
public class ServiceDashboardPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    /**
     * Top-level container that wraps all service action tiles / links on the dashboard.
     * Broad selector with multiple fallbacks to handle WM's A/B variants.
     */
    private static final By DASHBOARD_CONTAINER_LOC = By.cssSelector(
            ".service-dashboard, .dashboard-container, [data-testid='service-dashboard'], " +
            ".service-links, .action-links, .quick-links, " +
            ".service-tiles, [class*='dashboard'], [class*='service-list'], " +
            "main section, main .container, main"
    );

    /**
     * Generic locator for all service links / tiles on the dashboard.
     */
    private static final By ALL_SERVICE_LINKS_LOC = By.cssSelector(
            ".service-link, .service-tile, .dashboard-link, " +
            "[data-testid*='service-link'], [data-testid*='service-tile'], " +
            ".action-card a, .quick-link, " +
            "// TODO: replace — WM DOM service-link class name may differ post-deploy\n" +
            "a[href*='service'], a[href*='repair'], a[href*='container'], a[href*='pickup']"
    );

    /**
     * Specific locator for the "Repair/Replace Container" link.
     * Multiple CSS selectors cover variations in WM's DOM structure.
     */
    private static final By REPAIR_REPLACE_CONTAINER_LINK_LOC = By.cssSelector(
            // Text-based data attributes (most specific / stable)
            "[data-testid='repair-replace-container'], " +
            "[data-testid='repair-container'], " +
            "[data-action='repair-replace'], " +
            // Href-based fallbacks
            "a[href*='repair'], " +
            "a[href*='replace-container'], " +
            "a[href*='container-repair'], " +
            // Class-based fallbacks
            ".repair-replace-link, " +
            ".repair-container-link, " +
            "[class*='repair'], " +
            // TODO: replace — verify exact href fragment for Repair/Replace Container on live WM.com
            "a[href*='RepairReplaceContainer'], " +
            "a[href*='repair-replace-container']"
    );

    /**
     * XPath fallback to locate the link by its visible text.
     * Used when CSS-based selectors do not match due to DOM changes.
     */
    private static final By REPAIR_REPLACE_CONTAINER_LINK_XPATH = By.xpath(
            "//a[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
            "'abcdefghijklmnopqrstuvwxyz'), 'repair') and " +
            "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
            "'abcdefghijklmnopqrstuvwxyz'), 'container')] | " +
            "//button[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
            "'abcdefghijklmnopqrstuvwxyz'), 'repair') and " +
            "contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
            "'abcdefghijklmnopqrstuvwxuz'), 'container')] | " +
            "//*[contains(translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
            "'abcdefghijklmnopqrstuvwxyz'), 'repair/replace container')]"
    );

    // ─── FindBy elements ──────────────────────────────────────────────────────

    @FindBy(css = ".service-dashboard, .dashboard-container, [data-testid='service-dashboard'], " +
                  ".service-links, .action-links, .quick-links, main")
    private WebElement dashboardContainer;

    @FindBy(css = "[data-testid='repair-replace-container'], [data-testid='repair-container'], " +
                  "a[href*='repair'], a[href*='replace-container'], .repair-replace-link, " +
                  "[class*='repair'], a[href*='RepairReplaceContainer'], " +
                  "a[href*='repair-replace-container']")
    private WebElement repairReplaceContainerLink;

    @FindBy(css = ".service-link, .service-tile, .dashboard-link, a[href*='service'], " +
                  "a[href*='repair'], a[href*='container'], a[href*='pickup'], .action-card a")
    private List<WebElement> allServiceLinks;

    // ─── Page actions ─────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        try {
            WaitUtils.waitForPresence(DASHBOARD_CONTAINER_LOC);
            boolean loaded = isDisplayed(dashboardContainer);
            logger.info("Service dashboard loaded: {}", loaded);
            return loaded;
        } catch (Exception e) {
            logger.warn("Service dashboard load check failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Returns {@code true} when the dashboard container is visible after address submission.
     * Uses a generous timeout because WM.com performs address lookup calls before rendering.
     */
    public boolean isDashboardDisplayed() {
        boolean visible = WaitUtils.isElementVisible(DASHBOARD_CONTAINER_LOC, 20);
        logger.info("Service dashboard visible: {}", visible);
        return visible;
    }

    /**
     * Returns {@code true} when the "Repair/Replace Container" link is visible.
     * First tries the CSS selector, then falls back to the XPath text-based locator.
     */
    public boolean isRepairReplaceContainerLinkVisible() {
        // Primary: CSS selectors
        boolean cssVisible = WaitUtils.isElementVisible(REPAIR_REPLACE_CONTAINER_LINK_LOC, 15);
        if (cssVisible) {
            logger.info("'Repair/Replace Container' link found via CSS selector");
            return true;
        }

        // Fallback: XPath text-based search
        boolean xpathVisible = WaitUtils.isElementVisible(REPAIR_REPLACE_CONTAINER_LINK_XPATH, 10);
        logger.info("'Repair/Replace Container' link found via XPath fallback: {}", xpathVisible);
        return xpathVisible;
    }

    /**
     * Returns {@code true} when the "Repair/Replace Container" link is both visible
     * and enabled (i.e. clickable) in the browser.
     */
    public boolean isRepairReplaceContainerLinkClickable() {
        try {
            // Try CSS first
            if (WaitUtils.isElementVisible(REPAIR_REPLACE_CONTAINER_LINK_LOC, 10)) {
                WaitUtils.waitForClickability(REPAIR_REPLACE_CONTAINER_LINK_LOC);
                logger.info("'Repair/Replace Container' link is clickable (CSS)");
                return true;
            }
            // XPath fallback
            if (WaitUtils.isElementVisible(REPAIR_REPLACE_CONTAINER_LINK_XPATH, 10)) {
                WaitUtils.waitForClickability(REPAIR_REPLACE_CONTAINER_LINK_XPATH);
                logger.info("'Repair/Replace Container' link is clickable (XPath)");
                return true;
            }
            logger.warn("'Repair/Replace Container' link not found for clickability check");
            return false;
        } catch (Exception e) {
            logger.warn("Clickability check for 'Repair/Replace Container' failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Clicks the "Repair/Replace Container" link.
     * Tries CSS locator first; if not found, falls back to XPath.
     */
    public void clickRepairReplaceContainerLink() {
        if (WaitUtils.isElementVisible(REPAIR_REPLACE_CONTAINER_LINK_LOC, 10)) {
            click(REPAIR_REPLACE_CONTAINER_LINK_LOC);
            logger.info("Clicked 'Repair/Replace Container' link via CSS");
        } else if (WaitUtils.isElementVisible(REPAIR_REPLACE_CONTAINER_LINK_XPATH, 10)) {
            click(REPAIR_REPLACE_CONTAINER_LINK_XPATH);
            logger.info("Clicked 'Repair/Replace Container' link via XPath fallback");
        } else {
            logger.error("Could not find 'Repair/Replace Container' link to click");
            throw new RuntimeException("'Repair/Replace Container' link not found on service dashboard");
        }
    }

    /**
     * Returns the number of service links currently displayed on the dashboard.
     */
    public int getServiceLinkCount() {
        int count = allServiceLinks.size();
        logger.info("Service link count on dashboard: {}", count);
        return count;
    }

    /**
     * Returns {@code true} if any service links are present on the dashboard.
     */
    public boolean hasServiceLinks() {
        return getServiceLinkCount() > 0;
    }
}
