@web
Feature: WM.com Login Page

  As a registered WM customer
  I want a functional and secure login page
  So that I can sign in to manage my waste management account

  @smoke
  Scenario: Login page loads with the full sign-in form
    Given the user is on the WM login page
    Then the login form should be displayed

  @smoke
  Scenario: Forgot Password link is visible on the login page
    Given the user is on the WM login page
    Then the login form should be displayed
    And the Forgot Password link should be visible

  @smoke
  Scenario: Create Account link is visible on the login page
    Given the user is on the WM login page
    Then the login form should be displayed
    And the Create Account link should be visible

  @regression
  Scenario: Login with invalid credentials shows an error message
    Given the user is on the WM login page
    When the user enters email "invalid@notreal.com" and password "WrongPass999!"
    Then a login error message should be displayed

  @regression
  Scenario: Clicking Forgot Password navigates to the reset page
    Given the user is on the WM login page
    When the user clicks on Forgot Password
    Then the WM home page should be displayed

  @regression
  Scenario: Clicking Create Account navigates to registration
    Given the user is on the WM login page
    When the user clicks on Create Account
    Then the WM home page should be displayed

  @regression
  Scenario: Login page is accessible from the navigation header
    Given the user navigates to the WM home page
    Then the Sign In link should be visible in the header
    When the user clicks Sign In from the navigation bar
    Then the login form should be displayed
    And the Forgot Password link should be visible
