@web
Feature: WM.com Contact Us Page

  As a WM customer or visitor
  I want to be able to contact WM support
  So that I can get help with my service or account

  @smoke
  Scenario: Contact Us page loads with the contact form
    Given the user is on the Contact Us page
    Then the contact form should be displayed

  @smoke
  Scenario: Support phone number is visible on Contact Us page
    Given the user is on the Contact Us page
    Then the support phone number should be visible

  @regression
  Scenario: Submitting the contact form without required fields shows validation errors
    Given the user is on the Contact Us page
    When the user submits the contact form without filling required fields
    Then contact form validation errors should be displayed

  @regression
  Scenario: User can successfully submit the contact form with valid details
    Given the user is on the Contact Us page
    When the user fills in the contact form with valid details
    Then a form submission confirmation should be displayed

  @regression
  Scenario: Contact Us page is accessible via site navigation
    Given the user navigates to the WM home page
    Then the WM home page should be displayed
