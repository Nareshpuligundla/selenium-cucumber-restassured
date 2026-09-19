@web @regression
Feature: Guest user sees Repair/Replace Container link after entering valid residential address

  As a guest user (not logged in)
  I want to see the 'Repair/Replace Container' link after entering a valid residential address
  So that I can request a repair or replacement for my container without needing to log in

  Background:
    Given the guest user is on the WM home page

  @smoke @TC-002
  Scenario: Guest user sees Repair/Replace Container link after entering valid residential address
    Given the guest user is on the WM home page
    When the user enters the residential address "456 Oak Ave, Dallas, TX 75201"
    And the user selects "Home" as the service type
    And the user clicks the Get Started button
    Then the service options page should load for the entered address
    And the "Repair/Replace Container" link should be visible on the service options page
    And no login prompt should be displayed

  @regression @TC-002
  Scenario: Repair/Replace Container link is clickable for a guest user
    Given the guest user is on the WM home page
    When the user enters the residential address "456 Oak Ave, Dallas, TX 75201"
    And the user selects "Home" as the service type
    And the user clicks the Get Started button
    Then the service options page should load for the entered address
    And the "Repair/Replace Container" link should be visible and clickable on the service options page

  @regression @TC-002
  Scenario: Guest user is not prompted to log in at any step of the repair flow
    Given the guest user is on the WM home page
    When the user enters the residential address "456 Oak Ave, Dallas, TX 75201"
    And the user selects "Home" as the service type
    And the user clicks the Get Started button
    Then no login prompt should be displayed
    And the service options page should load for the entered address
    And the "Repair/Replace Container" link should be visible on the service options page
