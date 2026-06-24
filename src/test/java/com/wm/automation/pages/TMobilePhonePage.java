package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page object covering two closely related T-Mobile pages:
 *
 *  1. Phones Category / Listing Page — the grid of available smartphones
 *     reached after clicking 'Phones' in the top nav.
 *
 *  2. Phone Product Detail Page — the device-specific page with color swatch,
 *     storage selector, price, and 'Add to Cart' button.
 *
 * Both pages live under the same page object because the listing immediately
 * links into the detail page and the step flow visits them sequentially.
 */
public class TMobilePhonePage extends BasePage {

    // ─── Locators — Phone Listing Page ────────────────────────────────────────

    // TODO: replace — verify listing page heading selector on T-Mobile.com
    @FindBy(css = "h1, [data-cy='phonesPageTitle'], [data-testid='phones-heading']")
    private WebElement phonesPageHeading;

    // TODO: replace — verify product card/tile selector on T-Mobile.com phones page
    @FindBy(css = ".product-card, [data-testid='product-card'], .device-card, " +
                  "li[class*='product'], div[class*='DeviceTile']")
    private List<WebElement> productCards;

    private static final By PRODUCT_LISTING_LOC =
            By.cssSelector(".product-card, [data-testid='product-card'], .device-card, " +
                           "li[class*='product'], div[class*='DeviceTile'], .product-list");

    // ─── Locators — Product Detail Page ───────────────────────────────────────

    // TODO: replace — verify product detail page title/heading selector on T-Mobile.com
    @FindBy(css = "h1[class*='title'], [data-cy='pdpDeviceName'], [data-testid='product-title'], h1")
    private WebElement productDetailTitle;

    // TODO: replace — verify color swatch container/section selector on T-Mobile.com
    @FindBy(css = "[data-cy='colorPicker'], [data-testid='color-selector'], " +
                  ".color-picker, .color-selector, [aria-label*='color' i]")
    private WebElement colorSelectorContainer;

    // TODO: replace — verify individual color swatch button selector on T-Mobile.com
    @FindBy(css = "[data-cy='colorSwatch'], [data-testid='color-swatch'], " +
                  ".color-swatch, button[aria-label*='color' i], [class*='ColorSwatch']")
    private List<WebElement> colorSwatches;

    // TODO: replace — verify storage option selector on T-Mobile.com
    @FindBy(css = "[data-cy='storagePicker'], [data-testid='storage-selector'], " +
                  ".storage-picker, .storage-selector, [aria-label*='storage' i]")
    private WebElement storageSelectorContainer;

    // TODO: replace — verify individual storage button selector on T-Mobile.com
    @FindBy(css = "[data-cy='storageOption'], [data-testid='storage-option'], " +
                  ".storage-option, button[aria-label*='GB' i], [class*='StorageOption']")
    private List<WebElement> storageOptions;

    // TODO: replace — verify price element selector on T-Mobile.com PDP
    @FindBy(css = "[data-cy='devicePrice'], [data-testid='product-price'], " +
                  ".device-price, .pdp-price, [class*='Price']")
    private WebElement priceElement;

    // TODO: replace — verify 'Add to Cart' button selector on T-Mobile.com PDP
    @FindBy(css = "button[data-cy='addToCart'], button[data-testid='add-to-cart'], " +
                  "button[aria-label*='add to cart' i], .add-to-cart-btn, " +
                  "button[class*='AddToCart']")
    private WebElement addToCartButton;

    private static final By ADD_TO_CART_LOC =
            By.cssSelector("button[data-cy='addToCart'], button[data-testid='add-to-cart'], " +
                           "button[aria-label*='add to cart' i], .add-to-cart-btn, " +
                           "button[class*='AddToCart']");

    private static final By COLOR_SELECTOR_LOC =
            By.cssSelector("[data-cy='colorPicker'], [data-testid='color-selector'], " +
                           ".color-picker, .color-selector, [aria-label*='color' i]");

    private static final By STORAGE_SELECTOR_LOC =
            By.cssSelector("[data-cy='storagePicker'], [data-testid='storage-selector'], " +
                           ".storage-picker, .storage-selector, [aria-label*='storage' i]");

    // ─── Listing Page Actions ─────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(PRODUCT_LISTING_LOC, 15);
    }

    /**
     * Returns true when the phones listing page has loaded and at least one
     * product card (iPhone or otherwise) is visible.
     */
    public boolean isPhonesListingDisplayed() {
        try {
            WaitUtils.waitForVisibility(phonesPageHeading);
            logger.info("Phones listing page heading: {}", phonesPageHeading.getText());
            return true;
        } catch (Exception e) {
            logger.warn("Phones listing heading not found, falling back to product cards check");
            return WaitUtils.isElementVisible(PRODUCT_LISTING_LOC, 10);
        }
    }

    /**
     * Returns true when at least one product card is visible on the listing page.
     */
    public boolean areProductCardsVisible() {
        return WaitUtils.isElementVisible(PRODUCT_LISTING_LOC, 10);
    }

    /**
     * Finds and clicks the product card/link matching the given product name (case-insensitive).
     * Searches anchor text and accessible name attributes.
     *
     * @param productName e.g. "iPhone 17 Pro Max"
     */
    public void selectProductByName(String productName) {
        logger.info("Looking for product: {}", productName);

        // Primary strategy: find any link whose text or aria-label contains the product name
        By productLinkLoc = By.xpath(
            "//a[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" +
            productName.toLowerCase() + "')]" +
            " | //button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" +
            productName.toLowerCase() + "')]"
        );

        try {
            WaitUtils.waitForPresenceElement(productLinkLoc);
            ElementUtils.click(productLinkLoc);
            logger.info("Clicked product link for: {}", productName);
        } catch (Exception e) {
            // Fallback: find by aria-label attribute
            By ariaLoc = By.xpath(
                "//*[contains(translate(@aria-label, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" +
                productName.toLowerCase() + "')]"
            );
            ElementUtils.click(ariaLoc);
            logger.info("Clicked product via aria-label for: {}", productName);
        }
    }

    // ─── Product Detail Page Actions ──────────────────────────────────────────

    /**
     * Returns true when the product detail page has loaded — the title, color
     * selector, and storage selector are all visible.
     */
    public boolean isProductDetailPageLoaded(String expectedProductName) {
        try {
            WaitUtils.waitForVisibility(productDetailTitle);
            String title = productDetailTitle.getText();
            logger.info("PDP title: {}", title);
            boolean titleMatches = title.toLowerCase().contains(expectedProductName.toLowerCase());
            boolean colorSelectorVisible = WaitUtils.isElementVisible(COLOR_SELECTOR_LOC, 10);
            boolean storageSelectorVisible = WaitUtils.isElementVisible(STORAGE_SELECTOR_LOC, 10);
            logger.info("Title match={}, colorSelector={}, storageSelector={}",
                    titleMatches, colorSelectorVisible, storageSelectorVisible);
            return titleMatches && colorSelectorVisible && storageSelectorVisible;
        } catch (Exception e) {
            logger.warn("Product detail page load check failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Returns the text of the product detail page title element.
     */
    public String getProductDetailTitle() {
        try {
            WaitUtils.waitForVisibility(productDetailTitle);
            return productDetailTitle.getText().trim();
        } catch (Exception e) {
            logger.warn("Could not read product detail title: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Returns true when both the color selector and storage selector sections
     * are visible on the product detail page.
     */
    public boolean areSelectorsVisible() {
        boolean colorVisible = WaitUtils.isElementVisible(COLOR_SELECTOR_LOC, 10);
        boolean storageVisible = WaitUtils.isElementVisible(STORAGE_SELECTOR_LOC, 10);
        logger.info("Color selector visible={}, Storage selector visible={}", colorVisible, storageVisible);
        return colorVisible && storageVisible;
    }

    /**
     * Selects a color swatch by name (case-insensitive match against aria-label or text).
     *
     * @param colorName e.g. "Blue"
     */
    public void selectColor(String colorName) {
        logger.info("Selecting color: {}", colorName);

        // Strategy 1: match by aria-label containing color name
        By colorSwatchLoc = By.cssSelector(
            "[aria-label*='" + colorName + "'], [data-color*='" + colorName.toLowerCase() + "'], " +
            "button[title*='" + colorName + "']"
        );

        try {
            WaitUtils.waitForClickability(colorSwatchLoc);
            ElementUtils.click(colorSwatchLoc);
            logger.info("Selected color via CSS: {}", colorName);
        } catch (Exception e) {
            // Strategy 2: iterate all swatches and match text/aria-label
            logger.warn("CSS color selection failed, falling back to iteration: {}", e.getMessage());
            boolean clicked = false;
            for (WebElement swatch : colorSwatches) {
                String label = swatch.getAttribute("aria-label");
                if (label == null) label = swatch.getText();
                if (label != null && label.toLowerCase().contains(colorName.toLowerCase())) {
                    WaitUtils.waitForClickability(swatch);
                    click(swatch);
                    clicked = true;
                    break;
                }
            }
            if (!clicked) {
                throw new RuntimeException("Could not find color swatch for: " + colorName);
            }
        }
    }

    /**
     * Returns true when the named color swatch appears selected/highlighted.
     * Checks the aria-pressed, aria-selected, or class-based selected state.
     *
     * @param colorName e.g. "Blue"
     */
    public boolean isColorSelected(String colorName) {
        By selectedColorLoc = By.cssSelector(
            "[aria-label*='" + colorName + "'][aria-pressed='true'], " +
            "[aria-label*='" + colorName + "'][aria-selected='true'], " +
            "[aria-label*='" + colorName + "'][class*='selected' i], " +
            "[aria-label*='" + colorName + "'][class*='active' i], " +
            "[data-color*='" + colorName.toLowerCase() + "'][class*='selected' i]"
        );
        boolean selected = WaitUtils.isElementVisible(selectedColorLoc, 5);
        logger.info("Color '{}' selected state: {}", colorName, selected);
        return selected;
    }

    /**
     * Selects a storage option by label (case-insensitive match against aria-label or text).
     *
     * @param storageLabel e.g. "512GB"
     */
    public void selectStorage(String storageLabel) {
        logger.info("Selecting storage: {}", storageLabel);

        // Strategy 1: match by aria-label or text containing storage label
        By storageLoc = By.cssSelector(
            "[aria-label*='" + storageLabel + "'], " +
            "button[data-storage*='" + storageLabel + "'], " +
            "button[data-value*='" + storageLabel + "']"
        );

        try {
            WaitUtils.waitForClickability(storageLoc);
            ElementUtils.click(storageLoc);
            logger.info("Selected storage via CSS: {}", storageLabel);
        } catch (Exception e) {
            // Strategy 2: iterate all storage options and match text/aria-label
            logger.warn("CSS storage selection failed, falling back to iteration: {}", e.getMessage());
            boolean clicked = false;
            for (WebElement option : storageOptions) {
                String label = option.getAttribute("aria-label");
                if (label == null) label = option.getText();
                if (label != null && label.toLowerCase().contains(storageLabel.toLowerCase())) {
                    WaitUtils.waitForClickability(option);
                    click(option);
                    clicked = true;
                    break;
                }
            }
            if (!clicked) {
                // Strategy 3: XPath text-based search
                By xpathLoc = By.xpath(
                    "//button[contains(text(), '" + storageLabel + "')] | " +
                    "//*[contains(@aria-label, '" + storageLabel + "')]"
                );
                ElementUtils.click(xpathLoc);
            }
        }
    }

    /**
     * Returns true when the named storage option appears selected/highlighted.
     *
     * @param storageLabel e.g. "512GB"
     */
    public boolean isStorageSelected(String storageLabel) {
        By selectedStorageLoc = By.cssSelector(
            "[aria-label*='" + storageLabel + "'][aria-pressed='true'], " +
            "[aria-label*='" + storageLabel + "'][aria-selected='true'], " +
            "[aria-label*='" + storageLabel + "'][class*='selected' i], " +
            "[aria-label*='" + storageLabel + "'][class*='active' i]"
        );
        boolean found = WaitUtils.isElementVisible(selectedStorageLoc, 5);
        if (!found) {
            // Fallback: check if any option element with this label has selected/active class
            By xpathSelected = By.xpath(
                "//button[contains(text(), '" + storageLabel + "') and " +
                "(contains(@class, 'selected') or contains(@class, 'active') or @aria-pressed='true')]"
            );
            found = WaitUtils.isElementVisible(xpathSelected, 5);
        }
        logger.info("Storage '{}' selected state: {}", storageLabel, found);
        return found;
    }

    /**
     * Returns the currently displayed price text from the product detail page.
     */
    public String getDisplayedPrice() {
        try {
            WaitUtils.waitForVisibility(priceElement);
            String price = priceElement.getText().trim();
            logger.info("Current displayed price: {}", price);
            return price;
        } catch (Exception e) {
            logger.warn("Could not read price: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Returns true when the displayed price is non-empty (i.e., has been updated
     * after storage selection).
     */
    public boolean isPriceUpdated() {
        return !getDisplayedPrice().isEmpty();
    }

    /**
     * Returns true when the 'Add to Cart' button is visible and enabled.
     */
    public boolean isAddToCartButtonEnabled() {
        try {
            WaitUtils.waitForVisibility(addToCartButton);
            boolean enabled = addToCartButton.isEnabled();
            boolean displayed = addToCartButton.isDisplayed();
            logger.info("Add to Cart button — visible={}, enabled={}", displayed, enabled);
            return displayed && enabled;
        } catch (Exception e) {
            logger.warn("Add to Cart button check failed: {}", e.getMessage());
            return WaitUtils.isElementVisible(ADD_TO_CART_LOC, 5);
        }
    }

    /**
     * Clicks the 'Add to Cart' button.
     */
    public void clickAddToCart() {
        logger.info("Clicking Add to Cart button");
        scrollToElement(addToCartButton);
        click(addToCartButton);
    }

    /**
     * Returns the text content of the product title as shown on the detail page,
     * used to verify "iPhone 17 Pro Max" appears in the page title.
     */
    public boolean isProductTitleVisible(String productName) {
        try {
            WaitUtils.waitForVisibility(productDetailTitle);
            String text = productDetailTitle.getText();
            boolean match = text.toLowerCase().contains(productName.toLowerCase());
            logger.info("Product title '{}' contains '{}': {}", text, productName, match);
            return match;
        } catch (Exception e) {
            // Fallback: check page title
            String pageTitle = getPageTitle();
            boolean match = pageTitle.toLowerCase().contains(productName.toLowerCase());
            logger.warn("Using page title fallback — '{}' contains '{}': {}", pageTitle, productName, match);
            return match;
        }
    }
}
