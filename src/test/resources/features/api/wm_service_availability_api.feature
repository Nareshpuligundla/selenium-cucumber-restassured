@api
Feature: WM Service Availability API Validation

  As an API consumer
  I want to validate the service availability API endpoints
  So that I can confirm the API behaves correctly for valid and invalid inputs

  Background:
    Given the API base URL is configured

  @smoke
  Scenario: API returns success for valid GET request
    When a GET request is made to the posts endpoint
    Then the response status code should be 200
    And the response should contain a list of posts

  @smoke
  Scenario: API handles invalid endpoint gracefully
    When a GET request is made to an invalid endpoint
    Then the response status code should be 404

  @regression
  Scenario: API response for single post is valid
    When a GET request is made to the posts endpoint with id 1
    Then the response status code should be 200
    And the API response title field should not be empty

  @regression
  Scenario: API response schema is valid for single post
    When a GET request is made to the posts endpoint with id 1
    Then the response status code should be 200
    And the response should match the expected schema

  @regression
  Scenario: API supports POST request with valid payload
    When a POST request is made to the posts endpoint with valid payload
    Then the response status code should be 201
    And the response should contain the created post data
