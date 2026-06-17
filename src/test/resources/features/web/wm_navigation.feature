@web
Feature: WM.com Site Navigation

  As a site visitor
  I want all primary navigation elements to be accessible and functional
  So that I can easily move between sections of WM.com

  Background:
    Given the user navigates to the WM home page

  @smoke
  Scenario: WM logo is visible in the header
    Then the WM home page should be displayed
    And the WM logo should be visible in the header

  @smoke
  Scenario: Sign In link is visible in the header
    Then the WM home page should be displayed
    And the Sign In link should be visible in the header

  @regression
  Scenario: Header contains multiple navigation links
    Then the WM home page should be displayed
    And the header navigation should contain at least 3 links

  @regression
  Scenario: Clicking WM logo from any page returns to home
    Then the WM home page should be displayed
    When the user clicks on the WM logo
    Then the WM home page should be displayed

  @regression
  Scenario: User can navigate to Residential Services via nav link
    Then the WM home page should be displayed
    When the user clicks on Residential Services in the navigation
    Then the WM home page should be displayed

  @regression
  Scenario: User can navigate to Dumpster Rental via nav link
    Then the WM home page should be displayed
    When the user clicks on Dumpster Rental in the navigation

  @smoke
  Scenario: Sign In link navigates to the login page
    Then the Sign In link should be visible in the header
    When the user clicks Sign In from the navigation bar
    Then the login form should be displayed
