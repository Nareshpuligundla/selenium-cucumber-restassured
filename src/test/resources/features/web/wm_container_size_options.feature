@web @regression
Feature: Container Size Options Displayed and Submission Successful

  As a guest user
  I want to be able to request a missing container replacement by selecting container size
  and completing the submission flow without logging in
  So that I get a confirmation with a reference number and email notification

  Background:
    Given the guest user is on the WM home page

  @smoke @AC-2 @AC-3 @AC-4 @guest-flow @happy-path
  Scenario: Container size options displayed and submission successful for a Missing container (replacement flow)
    When the user enters the residential service address "123 Elm Street, Dallas, TX 75201"
    And the user selects "Home" as the property type
    And the user clicks the Get Started button
    Then the service options screen should be loaded
    And the Repair Replace Container link should be visible

    When the user clicks the Repair Replace Container link
    Then the issue type selection screen should be displayed
    And the issue type options should include "Missing", "Damaged", and "Stolen"

    When the user selects "Missing" as the issue type
    Then the container size selection should be enabled

    And exactly 3 container size options should be displayed
    And the container size options should include "32-gallon", "64-gallon", and "96-gallon"

    When the user selects "64-gallon" as the container size
    Then the required information entry step should be displayed

    When the user enters "Jane Guest" in the Contact Name field
    And the user enters "214-555-0199" in the Phone Number field
    And the user enters "janeguest@test.com" in the Email Address field
    And the user confirms the service address "123 Elm Street, Dallas, TX 75201"
    And the user proceeds to the payment step

    Then the payment step should be displayed
    And the payment methods "Credit/Debit Card" and "Online Account Credit" should be shown

    When the user selects "Credit/Debit Card" as the payment method
    Then card entry fields should be displayed

    When the user enters card number "4111111111111111", expiry "12/27", and CVV "123"
    And the user proceeds to the review step

    Then the review screen should display issue type "Missing"
    And the review screen should display container size "64-gallon"
    And the review screen should display service address "123 Elm Street, Dallas, TX 75201"
    And the review screen should display payment method "Credit/Debit Card"

    When the user clicks the Submit button

    Then the confirmation screen should be displayed
    And a unique request reference number should be shown
    And the confirmation should display issue type "Missing"
    And the confirmation should display container size "64-gallon"
    And the confirmation should display service address "123 Elm Street, Dallas, TX 75201"
    And the confirmation should display payment method "Credit/Debit Card"
    And the confirmation message should mention "janeguest@test.com"
    And the confirmation message should mention "15 minutes"
    And no login prompt should be displayed

  @regression @AC-2
  Scenario: All three container size options are visible after selecting Missing issue type
    When the user enters the residential service address "123 Elm Street, Dallas, TX 75201"
    And the user selects "Home" as the property type
    And the user clicks the Get Started button
    Then the service options screen should be loaded
    When the user clicks the Repair Replace Container link
    And the user selects "Missing" as the issue type
    Then exactly 3 container size options should be displayed
    And the container size options should include "32-gallon", "64-gallon", and "96-gallon"

  @regression @AC-3
  Scenario: Selecting 64-gallon container size advances the flow
    When the user enters the residential service address "123 Elm Street, Dallas, TX 75201"
    And the user selects "Home" as the property type
    And the user clicks the Get Started button
    Then the service options screen should be loaded
    When the user clicks the Repair Replace Container link
    And the user selects "Missing" as the issue type
    And the user selects "64-gallon" as the container size
    Then the required information entry step should be displayed

  @regression @AC-4
  Scenario: Payment step shows Credit/Debit Card and Online Account Credit options
    When the user enters the residential service address "123 Elm Street, Dallas, TX 75201"
    And the user selects "Home" as the property type
    And the user clicks the Get Started button
    Then the service options screen should be loaded
    When the user clicks the Repair Replace Container link
    And the user selects "Missing" as the issue type
    And the user selects "64-gallon" as the container size
    Then the required information entry step should be displayed
    When the user enters "Jane Guest" in the Contact Name field
    And the user enters "214-555-0199" in the Phone Number field
    And the user enters "janeguest@test.com" in the Email Address field
    And the user confirms the service address "123 Elm Street, Dallas, TX 75201"
    And the user proceeds to the payment step
    Then the payment step should be displayed
    And the payment methods "Credit/Debit Card" and "Online Account Credit" should be shown
