@web
Feature: WM Guest Checkout

  As a guest shopper
  I want to complete a purchase without creating an account
  So that I can buy products quickly and conveniently

  # ─── TC-001: Successful guest checkout below 500 USD ─────────────────────────

  @smoke @happy-path @guest-checkout
  Scenario: Successful guest checkout with all valid fields - order below 500 USD
    Given the guest user opens the cart page at "https://shop.example.com/cart"
    Then the cart subtotal should be "299.99" USD and the Proceed to Checkout button should be visible
    When the guest user clicks the Proceed to Checkout button
    Then the guest checkout page should be loaded at "/checkout"
    And the Email, Shipping Address and Payment sections should be visible
    When the guest user enters "guestuser@testmail.com" in the Email address field
    Then the Email address field should display "guestuser@testmail.com" with no validation error
    When the guest user enters "guestuser@testmail.com" in the Confirm email address field
    Then both email fields should match with no error
    When the guest user enters "123 Main St" in the Address line 1 field
    And the guest user enters "Apt 4B" in the Address line 2 field
    And the guest user enters "Austin" in the City field
    And the guest user enters "TX" in the State Province field
    And the guest user enters "78701" in the Postal code field
    And the guest user selects "United States" from the Country dropdown
    And the guest user enters "4111111111111111" in the Card number field
    And the guest user enters "12/26" in the Expiry field
    And the guest user enters "123" in the CVV field
    And the guest user enters "Jane Doe" in the Cardholder name field
    When the guest user clicks the Place Order button
    Then the order confirmation page should be displayed at "/checkout/confirmation"
    And the order reference number should be displayed on the confirmation page
    And the confirmation page should show an itemised order summary with subtotal, tax, shipping cost, grand total and estimated delivery date
    And the confirmation page should display the text "Your order has been placed"
    And no AVS hold message should be displayed on the confirmation page

  # ─── TC-003: AVS partial match - order above 500 USD ─────────────────────────

  @AVS @hold @guest-checkout
  Scenario: Guest checkout with order above 500 USD - AVS partial match places order in pending-verification hold
    Given the guest user opens the cart page at "https://shop.example.com/cart"
    Then the cart subtotal should be "750.00" USD and the Proceed to Checkout button should be visible
    When the guest user clicks the Proceed to Checkout button
    Then the guest checkout page should be loaded at "/checkout"
    And the Email, Shipping Address and Payment sections should be visible
    When the guest user enters "avs.partial@testmail.com" in the Email address field
    Then the Email address field should display "avs.partial@testmail.com" with no validation error
    When the guest user enters "avs.partial@testmail.com" in the Confirm email address field
    Then both email fields should match with no error
    When the guest user enters "789 Oak Blvd" in the Address line 1 field
    And the guest user enters "Houston" in the City field
    And the guest user enters "TX" in the State Province field
    And the guest user enters "77001" in the Postal code field
    And the guest user selects "United States" from the Country dropdown
    And the guest user enters "4111111111111111" in the Card number field
    And the guest user enters "06/27" in the Expiry field
    And the guest user enters "456" in the CVV field
    And the guest user enters "Alice Brown" in the Cardholder name field
    When the guest user clicks the Place Order button
    Then the pending verification message should be displayed on the checkout page
    And an order reference number should be assigned and displayed on the hold page
    And the text "Your order has been placed" should NOT be displayed
    When the admin user navigates to "https://admin.example.com/orders/pending-verification"
    Then the pending verification admin page should be loaded
    And the order reference number should be present in the pending verification list with status "NOTIFIED"

  # ─── TC-004: Gift card disabled for subscription SKU ─────────────────────────

  @gift-card @subscription @restriction
  Scenario: Gift card input is disabled when cart contains a subscription SKU
    Given the guest user opens the cart page at "https://shop.example.com/cart"
    Then the subscription item "SUB-MONTHLY-001" should be visible in the cart
    And the Proceed to Checkout button should be visible
    When the guest user clicks the Proceed to Checkout button
    Then the guest checkout page should be loaded at "/checkout"
    And the gift card input field should be disabled
    And an explanatory message should be displayed stating gift cards cannot be applied to subscription orders
    When the guest user attempts to click the disabled gift card input field
    Then the gift card field should not accept focus or input
    When the guest user attempts to type "GC-TEST-1234-5678" into the disabled gift card input field
    Then no characters should be entered in the gift card field
    And the gift card input field should remain disabled
    And the explanatory message about gift card restriction should still be visible
