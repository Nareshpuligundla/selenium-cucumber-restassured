package com.wm.automation.stepdefinitions;

import com.wm.automation.base.ScenarioContext;
import com.wm.automation.pages.NavigationPage;
import com.wm.automation.pages.ServiceSelectionPage;
import com.wm.automation.reporting.ReportLogger;
import com.wm.automation.utils.AssertionUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavigationSteps {

    private static final Logger logger = LogManager.getLogger(NavigationSteps.class);
    private final ScenarioContext context;

    private NavigationPage navPage;
    private ServiceSelectionPage serviceSelectionPage;

    public NavigationSteps(ScenarioContext context) {
        this.context = context;
    }

    private NavigationPage nav() {
        if (navPage == null) navPage = new NavigationPage();
        return navPage;
    }

    private ServiceSelectionPage selectionPage() {
        if (serviceSelectionPage == null) serviceSelectionPage = new ServiceSelectionPage();
        return serviceSelectionPage;
    }

    // ─── Navigation bar assertions ────────────────────────────────────────────

    @Then("the WM logo should be visible in the header")
    public void theWmLogoShouldBeVisibleInHeader() {
        AssertionUtils.assertTrue(nav().isLogoVisible(),
                "WM logo should be visible in the site header");
        ReportLogger.pass("WM logo is visible in header");
    }

    @Then("the Sign In link should be visible in the header")
    public void theSignInLinkShouldBeVisible() {
        AssertionUtils.assertTrue(nav().isSignInLinkVisible(),
                "Sign In link should be visible in the header");
        ReportLogger.pass("Sign In link is visible");
    }

    @Then("the header navigation should contain at least {int} links")
    public void theHeaderNavigationShouldContainLinks(int minLinks) {
        int count = nav().getNavLinkCount();
        ReportLogger.info("Navigation link count: " + count);
        AssertionUtils.assertGreaterThan(count, minLinks - 1,
                "Header nav should have at least " + minLinks + " links");
    }

    // ─── Navigation actions ───────────────────────────────────────────────────

    @When("the user clicks on the WM logo")
    public void theUserClicksOnTheLogo() {
        nav().clickLogo();
        ReportLogger.info("Clicked WM logo");
    }

    @When("the user clicks on Residential Services in the navigation")
    public void theUserClicksResidentialServicesNav() {
        nav().goToResidentialServices();
        ReportLogger.info("Clicked Residential Services nav link");
    }

    @When("the user clicks on Commercial Services in the navigation")
    public void theUserClicksCommercialServicesNav() {
        nav().goToCommercialServices();
        ReportLogger.info("Clicked Commercial Services nav link");
    }

    @When("the user clicks on Dumpster Rental in the navigation")
    public void theUserClicksDumpsterRentalNav() {
        nav().goToDumpsterRental();
        ReportLogger.info("Clicked Dumpster Rental nav link");
    }

    @When("the user clicks Sign In from the navigation bar")
    public void theUserClicksSignInFromNav() {
        nav().clickSignIn();
        ReportLogger.info("Clicked Sign In from navigation bar");
    }

    @When("the user opens the mobile hamburger menu")
    public void theUserOpensMobileHamburgerMenu() {
        nav().openMobileMenu();
        ReportLogger.info("Opened mobile hamburger menu");
    }

    @Then("the mobile navigation menu should be open")
    public void theMobileMenuShouldBeOpen() {
        AssertionUtils.assertTrue(nav().isMobileMenuOpen(),
                "Mobile navigation menu should be open after clicking hamburger");
        ReportLogger.pass("Mobile menu is open");
    }

    // ─── Service selection page ───────────────────────────────────────────────

    @Then("the service selection page should be displayed")
    public void theServiceSelectionPageShouldBeDisplayed() {
        boolean loaded = selectionPage().isPageLoaded();
        ReportLogger.info("Service selection page loaded: " + loaded);
        AssertionUtils.assertTrue(loaded, "Service selection page should be displayed");
    }

    @Then("the residential service card should be visible")
    public void theResidentialServiceCardShouldBeVisible() {
        AssertionUtils.assertTrue(selectionPage().isResidentialCardVisible(),
                "Residential service card should be visible");
        ReportLogger.pass("Residential service card is visible");
    }

    @Then("the commercial service card should be visible")
    public void theCommercialServiceCardShouldBeVisible() {
        AssertionUtils.assertTrue(selectionPage().isCommercialCardVisible(),
                "Commercial service card should be visible");
        ReportLogger.pass("Commercial service card is visible");
    }

    @Then("at least {int} service cards should be displayed")
    public void atLeastNServiceCardsShouldBeDisplayed(int min) {
        int count = selectionPage().getServiceCardCount();
        ReportLogger.info("Service card count: " + count);
        AssertionUtils.assertGreaterThan(count, min - 1,
                "At least " + min + " service cards should be displayed");
    }

    @When("the user selects Residential service")
    public void theUserSelectsResidentialService() {
        selectionPage().selectResidential();
        ReportLogger.info("Selected Residential service card");
    }

    @When("the user selects Commercial service")
    public void theUserSelectsCommercialService() {
        selectionPage().selectCommercial();
        ReportLogger.info("Selected Commercial service card");
    }

    @When("the user selects Dumpster Rental")
    public void theUserSelectsDumpsterRental() {
        selectionPage().selectDumpsterRental();
        ReportLogger.info("Selected Dumpster Rental service card");
    }

    @Then("the confirmed address should be displayed on the selection page")
    public void theConfirmedAddressShouldBeDisplayed() {
        String address = selectionPage().getConfirmedAddress();
        AssertionUtils.assertNotEmpty(address,
                "Confirmed address should be displayed on service selection page");
        ReportLogger.pass("Confirmed address displayed: " + address);
    }

    @And("the change address link should be available")
    public void theChangeAddressLinkShouldBeAvailable() {
        AssertionUtils.assertTrue(selectionPage().isChangeAddressLinkVisible(),
                "Change Address link should be visible");
        ReportLogger.pass("Change Address link is visible");
    }
}
