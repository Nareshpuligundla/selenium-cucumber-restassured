@web @disclosure @proxy-applicant
Feature: Proxy Applicant Disclosure Page

  As a proxy applicant
  I want to view and accept the disclosure document
  So that I can proceed with the application submission

  Background:
    Given the proxy applicant navigates to the login page
    When the user enters email "proxy.user@test.com" in the email field
    And the user enters password "ProxyPass1!" in the password field
    And the user clicks the Sign In submit button
    Then the proxy applicant is redirected to the dashboard

  @smoke @view
  Scenario: TC-001 Proxy applicant views disclosure page content successfully
    When the proxy applicant clicks the in-progress application to resume it
    Then the disclosure page should be displayed at "/application/disclosure"
    And the page heading should read exactly "Disclosure"
    And the disclosure text panel should be visible and scrollable
    When the proxy applicant scrolls to the bottom of the disclosure panel
    Then the acceptance checkbox should become visible

  @smoke @accept @submit
  Scenario: TC-002 Proxy applicant accepts disclosure and proceeds to application submission
    When the proxy applicant clicks the in-progress application to resume it
    Then the disclosure page should be displayed at "/application/disclosure"
    And the disclosure text panel should be visible and scrollable
    When the proxy applicant scrolls to the bottom of the disclosure panel
    Then the acceptance checkbox should be enabled
    When the proxy applicant clicks the acceptance checkbox
    Then the acceptance checkbox should be checked
    And the Continue button should be enabled
    When the proxy applicant clicks the Continue button
    Then the application summary page should be displayed at "/application/summary"
    And the application summary page heading should read "Application Summary"
    And the disclosure accepted indicator should be displayed

  @regression @boundary-scroll @negative
  Scenario: TC-003 Accept control is disabled before proxy applicant scrolls to bottom of disclosure
    When the proxy applicant clicks the in-progress application to resume it
    Then the disclosure page should be displayed at "/application/disclosure"
    And the acceptance checkbox should be disabled
    And the Continue button should be disabled
    When the proxy applicant attempts to click the Continue button without scrolling
    Then no navigation should have occurred from "/application/disclosure"
    And the validation message "Please read the full disclosure before accepting" should be displayed
