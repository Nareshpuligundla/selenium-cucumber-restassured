package com.wm.automation.stepdefinitions;

import com.wm.automation.base.ScenarioContext;
import com.wm.automation.constants.FrameworkConstants;
import com.wm.automation.pages.HomePage;
import com.wm.automation.reporting.ReportLogger;
import com.wm.automation.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonSteps {

    private static final Logger logger = LogManager.getLogger(CommonSteps.class);

    private final ScenarioContext context;

    public CommonSteps(ScenarioContext context) {
        this.context = context;
    }

    @Given("the API base URL is configured")
    public void theApiBaseUrlIsConfigured() {
        String apiBaseUrl = ConfigReader.getInstance().getApiBaseUrl();
        ReportLogger.info("API Base URL configured: " + apiBaseUrl);
        logger.info("API base URL: {}", apiBaseUrl);
        context.set("apiBaseUrl", apiBaseUrl);
    }

    @Given("the user navigates to the WM home page")
    public void theUserNavigatesToTheWmHomePage() {
        new HomePage().open();
        ReportLogger.info("Navigated to WM home page: " + ConfigReader.getInstance().getBaseUrl());
    }

    @Given("the guest user is on the WM home page")
    public void theGuestUserIsOnTheWmHomePage() {
        theUserNavigatesToTheWmHomePage();
    }
}
