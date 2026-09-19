@web @issue-type @guest-flow
Feature: Guest user can select an issue type after opening Repair/Replace Container

  As a guest user who has entered a valid residential address and selected 'Home'
  I want to navigate to the Repair/Replace Container section
  So that I can select an issue type such as 'Missing' without being required to log in

  Background:
    Given the guest user is on the WM home page
    And the user enters the residential address "1234 Elm Street, Houston, TX 77001"
    And the user selects "Home" from the service type options
    And the user clicks the "Get Started" button

  @smoke
  Scenario: Service dashboard loads after entering valid address and selecting Home service type
    Then the service dashboard should be displayed
    And the "Repair/Replace Container" link should be visible on the service dashboard

  @smoke
  Scenario: Guest user can open Repair/Replace Container page
    Then the service dashboard should be displayed
    When the user clicks the "Repair/Replace Container" link
    Then the Repair/Replace Container page should be displayed
    And the issue type list should be displayed

  @regression
  Scenario: Issue type list contains Missing option
    Then the service dashboard should be displayed
    When the user clicks the "Repair/Replace Container" link
    Then the Repair/Replace Container page should be displayed
    And the issue type list should contain "Missing"

  @smoke
  Scenario: TC-002 Guest user can select Missing as the issue type
    Then the service dashboard should be displayed
    When the user clicks the "Repair/Replace Container" link
    Then the Repair/Replace Container page should be displayed
    And the issue type list should contain "Missing"
    When the user selects "Missing" from the issue type list
    Then the "Missing" issue type should be selected
    And the next step action should be available

  @regression
  Scenario: Guest user can navigate Repair/Replace Container flow without logging in
    Then the service dashboard should be displayed
    When the user clicks the "Repair/Replace Container" link
    Then the Repair/Replace Container page should be displayed
    And the login prompt should not be required to view issue types

  @regression
  Scenario: At least one issue type option is displayed on the Repair/Replace Container page
    Then the service dashboard should be displayed
    When the user clicks the "Repair/Replace Container" link
    Then the Repair/Replace Container page should be displayed
    And at least 1 issue type option should be displayed
