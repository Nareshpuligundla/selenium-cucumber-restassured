@web
Feature: WM.com Service Type Selection

  As a guest user who has entered a valid service address
  I want to see the available service types for my location
  So that I can choose the right waste management service

  @smoke
  Scenario: Service selection page displays after valid address entry
    Given the guest user is on the WM home page
    When the user enters a valid residential service address
    And the user starts the service request flow
    Then the service selection page should be displayed

  @regression
  Scenario: Residential service card is visible on selection page
    Given the guest user is on the WM home page
    When the user enters a valid residential service address
    And the user starts the service request flow
    Then the service selection page should be displayed
    And the residential service card should be visible

  @regression
  Scenario: Commercial service card is visible on selection page
    Given the guest user is on the WM home page
    When the user enters a valid residential service address
    And the user starts the service request flow
    Then the service selection page should be displayed
    And the commercial service card should be visible

  @regression
  Scenario: At least two service cards are shown on the selection page
    Given the guest user is on the WM home page
    When the user enters a valid residential service address
    And the user starts the service request flow
    Then the service selection page should be displayed
    And at least 2 service cards should be displayed

  @regression
  Scenario: Confirmed address is displayed on service selection page
    Given the guest user is on the WM home page
    When the user enters a valid residential service address
    And the user starts the service request flow
    Then the service selection page should be displayed
    And the confirmed address should be displayed on the selection page

  @regression
  Scenario: User can select Residential service
    Given the guest user is on the WM home page
    When the user enters a valid residential service address
    And the user starts the service request flow
    Then the service selection page should be displayed
    When the user selects Residential service
    Then the WM home page should be displayed
