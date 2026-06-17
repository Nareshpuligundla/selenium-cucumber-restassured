package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page object for WM.com search functionality.
 */
public class SearchPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    @FindBy(css = "input[type='search'], input[aria-label*='search' i], input[placeholder*='search' i]")
    private WebElement searchInput;

    @FindBy(css = "button[aria-label*='search' i], .search-submit, button[type='submit']")
    private WebElement searchSubmitButton;

    @FindBy(css = ".search-results, .results-container, [data-testid='search-results']")
    private WebElement searchResultsContainer;

    @FindBy(css = ".search-result-item, .result-item, article.result")
    private List<WebElement> searchResultItems;

    @FindBy(css = ".no-results, .zero-results, [data-testid='no-results']")
    private WebElement noResultsMessage;

    private static final By RESULTS_LOC =
            By.cssSelector(".search-results, .results-container, [data-testid='search-results']");

    // ─── Page actions ─────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(
                By.cssSelector("input[type='search'], input[aria-label*='search' i]"), 10);
    }

    public void search(String keyword) {
        type(searchInput, keyword);
        searchInput.sendKeys(Keys.ENTER);
        logger.info("Searched for: {}", keyword);
    }

    public boolean hasResults() {
        boolean visible = WaitUtils.isElementVisible(RESULTS_LOC, 10);
        logger.info("Search results present: {}", visible);
        return visible;
    }

    public int getResultCount() {
        List<WebElement> items = ElementUtils.findAll(
                By.cssSelector(".search-result-item, .result-item, article.result"));
        return items.size();
    }

    public boolean isNoResultsMessageDisplayed() {
        return isDisplayed(noResultsMessage);
    }

    public String getFirstResultTitle() {
        if (!searchResultItems.isEmpty()) {
            return getText(searchResultItems.get(0));
        }
        return "";
    }
}
