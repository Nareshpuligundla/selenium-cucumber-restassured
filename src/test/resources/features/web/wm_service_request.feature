@web
Feature: WM.com Service Request Flow

  As a guest user
  I want to be able to start a service request by entering my address
  So that I can get waste management services at my location

  @smoke
  Scenario: Guest user enters valid residential address and sees service options
    Given the guest user is on the WM home page
    When the user enters a valid residential service address
    And the user starts the service request flow
    Then the service request options should be displayed

  @regression
  Scenario: Guest user enters a valid address and it is accepted
    Given the guest user is on the WM home page
    When the user enters a valid residential service address
    Then the address should be accepted

  @regression
  Scenario: Guest user enters an invalid address
    Given the guest user is on the WM home page
    When the user enters an invalid service address
    Then an error or no results message should be displayed
