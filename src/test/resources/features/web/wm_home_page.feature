@web
Feature: WM.com Home Page Validation

  As a site visitor
  I want the WM.com home page to load correctly
  So that I can access waste management services

  Background:
    Given the user navigates to the WM home page

  @smoke
  Scenario: WM.com home page loads successfully
    Then the WM home page should be displayed

  @smoke
  Scenario: Page title contains WM brand name
    Then the WM home page should be displayed
    And the page title should contain "WM"

  @regression
  Scenario: Address input field is displayed on home page
    Then the WM home page should be displayed
    And the address input field should be visible
