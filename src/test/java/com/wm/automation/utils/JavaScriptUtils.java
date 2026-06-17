package com.wm.automation.utils;

import com.wm.automation.base.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {

    private static final Logger logger = LogManager.getLogger(JavaScriptUtils.class);

    private JavaScriptUtils() {}

    private static JavascriptExecutor js() {
        return (JavascriptExecutor) DriverFactory.getDriver();
    }

    public static void clickElement(WebElement element) {
        js().executeScript("arguments[0].click();", element);
        logger.debug("JS click on element: {}", element);
    }

    public static void scrollToElement(WebElement element) {
        js().executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", element);
    }

    public static void scrollToTop() {
        js().executeScript("window.scrollTo(0, 0);");
    }

    public static void scrollToBottom() {
        js().executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static void scrollBy(int x, int y) {
        js().executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }

    public static void highlightElement(WebElement element) {
        js().executeScript(
                "arguments[0].style.border='3px solid red';", element);
    }

    public static void clearInputField(WebElement element) {
        js().executeScript("arguments[0].value='';", element);
    }

    public static void setInputValue(WebElement element, String value) {
        js().executeScript("arguments[0].value=arguments[1];", element, value);
    }

    public static String getInnerText(WebElement element) {
        return (String) js().executeScript("return arguments[0].innerText;", element);
    }

    public static Object executeScript(String script, Object... args) {
        return js().executeScript(script, args);
    }

    public static void waitForPageLoad() {
        long timeout = ConfigReader.getInstance().getPageLoadTimeout() * 1000;
        long elapsed = 0;
        long poll = 500;
        while (elapsed < timeout) {
            String state = (String) js().executeScript("return document.readyState;");
            if ("complete".equals(state)) return;
            try {
                Thread.sleep(poll);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                return;
            }
            elapsed += poll;
        }
        logger.warn("Page load state did not reach 'complete' within {}ms", timeout);
    }
}
