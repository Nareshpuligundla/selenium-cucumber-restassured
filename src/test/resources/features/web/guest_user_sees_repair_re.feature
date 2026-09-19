@web @smoke @address-flow
Feature: Guest User Sees Repair/Replace Container Link After Address Entry

  As a guest user visiting WM.com
  I want to enter a valid residential address and select the 'Home' service type
  So that I can see the 'Repair/Replace Container' link on the service dashboard

  Background:
    Given the guest user is on the WM home page

  @smoke @address-flow
  Scenario: Guest user sees Repair/Replace Container link after entering valid residential address
    Given the WM home page is loaded with the address entry field visible
    When the user enters the residential address "1234 Elm Street, Houston, TX 77001"
    And the user selects "Home" as the service type
    And the user clicks the "Get Started" button
    Then the service dashboard should be displayed
    And the "Repair/Replace Container" link should be visible on the service dashboard
    And the "Repair/Replace Container" link should be clickable

  @regression @address-flow
  Scenario: Service dashboard displays after valid address is entered and Home is selected
    Given the WM home page is loaded with the address entry field visible
    When the user enters the residential address "1234 Elm Street, Houston, TX 77001"
    And the user selects "Home" as the service type
    And the user clicks the "Get Started" button
    Then the service dashboard should be displayed

  @regression @address-flow
  Scenario: Multiple service links are visible on the service dashboard after address entry
    Given the WM home page is loaded with the address entry field visible
    When the user enters the residential address "1234 Elm Street, Houston, TX 77001"
    And the user selects "Home" as the service type
    And the user clicks the "Get Started" button
    Then the service dashboard should be displayed
    And the service dashboard should display at least 1 service link
