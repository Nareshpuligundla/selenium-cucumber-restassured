@web @regression @login @authentication @error-handling
Feature: Login Fails with Invalid Credentials

  As a registered WM customer
  I want to see a clear error message when I enter invalid credentials
  So that I understand my login attempt failed and I remain on the login page

  Background:
    Given the user navigates to the WM home page

  @smoke
  Scenario: Login fails with invalid credentials and shows an error message
    Given the Sign In link is visible in the navigation bar
    When the user clicks the Sign In button from the nav bar
    Then the login form should be fully visible with email, password and sign-in button
    When the user enters email "wronguser@example.com" in the email field
    Then no inline validation error should appear on the email field
    When the user enters password "WrongPass99!" in the password field
    Then no inline validation error should appear on the password field
    When the user clicks the Sign In submit button
    Then the login error message "Invalid email or password. Please try again." should be displayed
    And the user should remain on the login page
    And the email field should still be visible on the login page
    And the password field should still be visible on the login page
    And the sign-in button should still be visible on the login page

  @regression
  Scenario Outline: Login fails with multiple invalid credential combinations
    Given the Sign In link is visible in the navigation bar
    When the user clicks the Sign In button from the nav bar
    Then the login form should be fully visible with email, password and sign-in button
    When the user enters email "<email>" in the email field
    And the user enters password "<password>" in the password field
    And the user clicks the Sign In submit button
    Then a login failure error message should be displayed
    And the user should remain on the login page

    Examples:
      | email                        | password       |
      | wronguser@example.com        | WrongPass99!   |
      | notregistered@nowhere.com    | AnotherPass1!  |
      | invalid.user@fake.domain.com | BadPassword99! |

  @regression
  Scenario: Login page remains intact after failed login attempt
    Given the user is on the WM login page
    When the user enters email "wronguser@example.com" and password "WrongPass99!"
    Then a login error message should be displayed
    And the email field should still be visible on the login page
    And the password field should still be visible on the login page
    And the sign-in button should still be visible on the login page
    And the user should remain on the login page
