@web @regression
Feature: WM.com Repair/Replace Container – Issue Type Selection

  As a guest user who has entered a valid residential address
  I want to open the Repair/Replace Container flow
  So that I can see all available issue types for my container

  Background:
    Given the guest user is on the WM home page
    When the user enters the repair replace container address "123 Elm Street, Dallas, TX 75201"
    And the user selects "Home" as the property type
    And the user clicks the Get Started button
    Then the service options screen should be displayed

  @smoke @AC-2 @guest-flow
  Scenario: All three issue types are displayed when opening Repair/Replace Container flow
    When the user clicks the Repair Replace Container link
    Then the issue type selection screen should be displayed
    And exactly three issue type options should be shown
    And the issue type option "Missing" should be visible
    And the issue type option "Damaged" should be visible
    And the issue type option "Stolen" should be visible

  @regression @AC-2 @guest-flow
  Scenario: No additional issue types beyond Missing, Damaged, and Stolen are present
    When the user clicks the Repair Replace Container link
    Then the issue type selection screen should be displayed
    And exactly three issue type options should be shown
    And only the expected issue types are displayed

  @regression @AC-2 @guest-flow
  Scenario: Repair/Replace Container link is visible on the service options screen
    Then the Repair Replace Container link should be visible on the service options screen

  @regression @AC-2 @guest-flow
  Scenario: Issue type selection screen loads after clicking Repair/Replace Container
    When the user clicks the Repair Replace Container link
    Then the issue type selection screen should be displayed
