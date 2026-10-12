@web
Feature: Guest Checkout Flow

  As an unregistered shopper
  I want to complete a purchase as a guest
  So that I can buy products without creating an account

  @guest-checkout @happy-path @smoke
  Scenario: TC-001 Successful guest checkout with valid email, shipping address, and card details
    Given the user navigates to the checkout cart page
    Then the cart page should be displayed with a Proceed to Checkout button
    When the user clicks the Proceed to Checkout button
    Then the checkout options page should display Guest Checkout and Sign In options
    When the user clicks the Guest Checkout button
    Then the guest checkout form should be displayed
    When the user enters "guestuser@testmail.com" in the Email Address field
    And the user enters "123 Main St" in the Street Address field
    And the user enters "Springfield" in the City field
    And the user enters "IL" in the State field
    And the user enters "62701" in the Postal Code field
    And the user selects "United States" from the Country dropdown
    And the user enters "4111111111111111" in the Card Number field
    And the user enters "12/26" in the Expiry Date field
    And the user enters "123" in the CVV field
    When the user clicks the Place Order button
    Then the order confirmation page should be displayed
    And the confirmation heading "Order Confirmed!" should be visible
    And the confirmation page should display the guest email "guestuser@testmail.com"
    And the confirmation page should display a unique order ID

  @guest-checkout @address-verification @high-value-order @regression
  Scenario: TC-002 Guest checkout with order above $500 triggers address verification
    Given the user navigates to the checkout cart page
    Then the cart page should be displayed with a Proceed to Checkout button
    When the user clicks the Proceed to Checkout button
    Then the checkout options page should display Guest Checkout and Sign In options
    When the user clicks the Guest Checkout button
    Then the guest checkout form should be displayed
    When the user enters "highvalue@testmail.com" in the Email Address field
    And the user enters "456 Oak Avenue" in the Street Address field
    And the user enters "Austin" in the City field
    And the user enters "TX" in the State field
    And the user enters "78701" in the Postal Code field
    And the user selects "United States" from the Country dropdown
    And the user enters "4111111111111111" in the Card Number field
    And the user enters "11/27" in the Expiry Date field
    And the user enters "456" in the CVV field
    When the user clicks the Place Order button
    Then the address verification prompt should be displayed for high-value orders
    And the verification prompt should show the entered address
    And the Confirm Address and Edit Address buttons should be visible
    When the user clicks the Confirm Address button
    Then the order confirmation page should be displayed
    And the confirmation heading "Order Confirmed!" should be visible
    And the confirmation page should display a unique order ID

  @guest-checkout @gift-card @subscription @restriction @regression
  Scenario: TC-003 Guest checkout blocked when attempting to apply a gift card to a subscription product
    Given the user navigates to the checkout cart page with a subscription product
    Then the cart page should display "Monthly Box Subscription" at "$29.99"
    And the cart page should be displayed with a Proceed to Checkout button
    When the user clicks the Proceed to Checkout button
    Then the checkout options page should display Guest Checkout and Sign In options
    When the user clicks the Guest Checkout button
    Then the guest checkout form should include a Gift Card field
    When the user enters "giftcardtest@testmail.com" in the Email Address field
    And the user enters "789 Birch Ave" in the Street Address field
    And the user enters "Portland" in the City field
    And the user enters "OR" in the State field
    And the user enters "97201" in the Postal Code field
    And the user selects "United States" from the Country dropdown
    And the user enters "GIFTCARD-TEST-2024" in the Gift Card field
    Then the gift card error message "Gift cards cannot be used for subscription products" should be displayed
    And the gift card field should be cleared
    And the order total should remain "$29.99"
    And the Place Order button should be visible and enabled
