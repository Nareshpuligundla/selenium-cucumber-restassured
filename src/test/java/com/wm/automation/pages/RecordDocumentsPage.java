package com.wm.automation.pages;

import com.wm.automation.base.BasePage;
import com.wm.automation.utils.ElementUtils;
import com.wm.automation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Page object for Worktop Records and the record Documents section (upload).
 */
public class RecordDocumentsPage extends BasePage {

    // ─── Locators ─────────────────────────────────────────────────────────────

    // TODO: replace - confirm 'Records' main navigation link selector
    @FindBy(css = "nav a[href*='records'], a[data-testid='nav-records']")
    private WebElement recordsNavLink;

    // TODO: replace - confirm hidden file input used by the 'Upload' button
    @FindBy(css = "input[type='file']")
    private WebElement fileInput;

    // TODO: replace - confirm upload banner selector (above the document list)
    @FindBy(css = ".upload-banner, [data-testid='upload-banner'], .documents-section [role='alert']")
    private WebElement banner;

    private static final By DOCUMENTS_SECTION_LOC =
            By.cssSelector(".documents-section, [data-testid='documents-section']");

    private static final By BANNER_LOC =
            By.cssSelector(".upload-banner, [data-testid='upload-banner'], .documents-section [role='alert']");

    // TODO: replace - confirm document list row selector
    private static final By DOCUMENT_ROW_LOC =
            By.cssSelector(".document-row, [data-testid='document-row'], .documents-section tbody tr");

    // ─── Actions ──────────────────────────────────────────────────────────────

    @Override
    public boolean isPageLoaded() {
        return WaitUtils.isElementVisible(DOCUMENTS_SECTION_LOC, 15);
    }

    public void openRecord(String recordId) {
        click(recordsNavLink);
        // TODO: replace - confirm record link selector
        click(By.xpath("//a[contains(normalize-space(.),'" + recordId + "')]"));
        logger.info("Opened record {}", recordId);
    }

    /** Selects the given file paths through the file input (absolute paths, newline separated). */
    public void uploadFiles(String... filePaths) {
        fileInput.sendKeys(String.join("\n", filePaths));
        logger.info("Uploaded {} file(s)", filePaths.length);
    }

    public String getBannerText() {
        try {
            WaitUtils.isElementVisible(BANNER_LOC, 10);
            return getText(banner);
        } catch (Exception e) {
            logger.warn("Could not read upload banner: {}", e.getMessage());
            return "";
        }
    }

    public List<String> getDocumentNames() {
        List<String> names = new ArrayList<>();
        for (WebElement row : ElementUtils.findAll(DOCUMENT_ROW_LOC)) {
            String text = row.getText().trim();
            if (!text.isEmpty()) {
                names.add(text.split("\\R")[0].trim());
            }
        }
        return names;
    }

    /** Returns the full row text (name, size, uploader, timestamp) for the document, or "". */
    public String getDocumentDetails(String fileName) {
        for (WebElement row : ElementUtils.findAll(DOCUMENT_ROW_LOC)) {
            if (row.getText().contains(fileName)) {
                return row.getText();
            }
        }
        return "";
    }
}
