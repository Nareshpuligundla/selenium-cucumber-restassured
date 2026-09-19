@web @regression @guest-flow
Feature: Guest User Completes Full Repair/Replace Container Request Flow

  As a guest user (not logged in)
  I want to submit a Repair/Replace Container request for my residential address
  So that I can get my missing container replaced without needing an account

  Background:
    Given the guest user is on the WM home page

  @smoke @happy-path @container-service
  Scenario: TC-001 Guest user completes full Repair/Replace Container request flow successfully
    Then the WM home page should be displayed
    And the address input field should be visible
    When the guest user enters the address "123 Main St, Houston, TX 77001"
    And the guest user selects "Home" from the service type options
    And the guest user clicks the Get Started button
    Then the service selection page should be loaded for the guest
    When the guest user clicks the Repair/Replace Container link
    Then the issue type selection screen should be displayed
    When the guest user selects "Missing" as the issue type
    Then the container size selection step should be displayed
    When the guest user selects the "96-gallon" container size
    Then the contact information entry step should be displayed
    When the guest user enters contact name "Jane Doe"
    And the guest user enters phone number "555-123-4567"
    And the guest user enters email "jane.doe@test.com"
    And the guest user selects the credit card payment method
    And the guest user enters credit card number ending in "4242" with expiry "12/26" and CVV "123"
    And the guest user clicks the Review button
    Then the review summary screen should be displayed
    And the review summary should show address "123 Main St, Houston, TX 77001"
    And the review summary should show issue type "Missing"
    And the review summary should show container size "96-gallon"
    And the review summary should show contact name "Jane Doe"
    And the review summary should show email "jane.doe@test.com"
    And the review summary should show phone "555-123-4567"
    When the guest user clicks the Submit button
    Then a confirmation screen should be displayed with a success message
    And a reference number should be displayed on the confirmation screen
    And no login prompt should be visible at any point in the flow
