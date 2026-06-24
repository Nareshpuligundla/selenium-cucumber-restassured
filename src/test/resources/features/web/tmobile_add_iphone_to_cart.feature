@web @smoke @cart @device-booking
Feature: Successfully add iPhone 17 Pro Max to cart on T-Mobile

  As a guest user on T-Mobile.com
  I want to add an iPhone 17 Pro Max in Blue color with 512GB storage to my cart
  So that I can proceed with purchasing the device

  Background:
    Given the user navigates to the T-Mobile home page

  @smoke @cart @device-booking
  Scenario: Successfully add iPhone 17 Pro Max (Blue, 512GB) to cart as a guest user
    Then the T-Mobile home page should be displayed with navigation links
    And the cart icon should show no item count badge
    When the user clicks on Phones in the T-Mobile navigation bar
    Then the phones category page should be displayed with available smartphones
    When the user selects "iPhone 17 Pro Max" from the product listing
    Then the iPhone 17 Pro Max product detail page should be displayed
    And the color selector and storage selector options should be visible
    When the user selects the "Blue" color swatch
    Then the "Blue" color swatch should be highlighted as selected
    When the user selects the "512GB" storage option
    Then the "512GB" storage option should be highlighted as selected
    And the price should reflect the 512GB variant pricing
    When the user verifies the selected configuration
    Then the product configuration should show "Blue" color and "512GB" storage
    And the Add to Cart button should be enabled and visible
    When the user clicks the Add to Cart button
    Then a cart confirmation should be displayed showing "iPhone 17 Pro Max"
    And the cart confirmation should show color "Blue" and storage "512GB"
    And the cart icon badge should display a count of "1"
