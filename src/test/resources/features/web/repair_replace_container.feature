@web @regression
Feature: Repair/Replace Container Service Option

  As a guest user on WM.com
  I want to see the 'Repair/Replace Container' link after entering a valid residential address
  So that I can request a container repair or replacement without logging in

  Background:
    Given the guest user is on the WM home page

  @smoke @AC-1 @guest-flow
  Scenario: Repair/Replace Container link visible for valid residential address (TC-001)
    When the user enters the residential address "123 Elm Street, Dallas, TX 75201"
    And the user selects "Home" as the property type
    And the user clicks the Get Started button
    Then the service options screen should be displayed
    And the "Repair/Replace Container" link should be visible on the service options screen
    And the "Repair/Replace Container" link should be clickable
    And no login prompt should be displayed

  @regression
  Scenario: Repair/Replace Container link is displayed after entering default valid address
    When the user enters a valid residential service address
    And the user starts the service request flow
    Then the service options screen should be displayed
    And the "Repair/Replace Container" link should be visible on the service options screen

  @regression
  Scenario: Page transitions to service options after entering a valid residential address and selecting Home
    When the user enters the residential address "123 Elm Street, Dallas, TX 75201"
    And the user selects "Home" as the property type
    And the user clicks the Get Started button
    Then the service options screen should be displayed

  @regression
  Scenario: Guest user can access Repair/Replace Container without being prompted to log in
    When the user enters the residential address "123 Elm Street, Dallas, TX 75201"
    And the user selects "Home" as the property type
    And the user clicks the Get Started button
    Then the service options screen should be displayed
    And no login prompt should be displayed
    And the "Repair/Replace Container" link should be visible on the service options screen
