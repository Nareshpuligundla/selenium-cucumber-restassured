@e2e
Feature: WM Web and API End-to-End Validation

  As a QA engineer
  I want to validate that the API returns expected data
  AND the web UI is functional in the same test run
  So that I can confirm end-to-end service availability

  @smoke
  Scenario: Validate service availability via API then verify UI loads
    Given the API base URL is configured
    When a GET request is made to the posts endpoint with id 1
    Then the response status code should be 200
    And the API response title field should not be empty
    When the user navigates to the WM home page
    Then the WM home page should be displayed

  @regression
  Scenario: API data validation followed by home page address input check
    Given the API base URL is configured
    When a GET request is made to the posts endpoint
    Then the response status code should be 200
    And the response should contain a list of posts
    When the user navigates to the WM home page
    Then the WM home page should be displayed
    And the address input field should be visible
