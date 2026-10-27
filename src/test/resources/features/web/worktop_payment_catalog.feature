@web @regression @worktop
Feature: Worktop Payment and Product Catalog

  As a Worktop customer
  I want to browse paginated products and pay by card
  So that I can complete orders with clear validation and failure messages

  Background:
    Given the Worktop user is on the Worktop login page

  # ASSUMPTION: inline error wording is proposed, pending business approval
  @payment @negative
  Scenario: Payment form shows exact per-field inline errors
    When the Worktop user logs in with identifier "customer1@example.com" and password "CustPassw0rd!55"
    And the Worktop customer opens the cart and clicks Checkout
    Then the payment form should be displayed with card number, expiry date, CVV and Pay button
    When the Worktop customer enters card number "4111 1111 1111 1112"
    And the Worktop customer enters expiry date "01/20"
    And the Worktop customer enters CVV "12"
    And the Worktop customer clicks Pay
    Then the user should remain on the payment page with Pay enabled
    And the card number field error should read "Enter a valid card number."
    And the expiry date field error should read "Your card has expired."
    And the CVV field error should read "CVV must be 3 digits."
    When the Worktop customer opens the pending order from Orders
    Then the order status should be "Unpaid"

  @pagination @smoke
  Scenario: Product listing shows first page of 20 products with working pagination controls
    When the Worktop user logs in with identifier "customer1@example.com" and password "CustPassw0rd!55"
    And the Worktop customer opens the product listing from the top navigation
    Then the product listing should show 20 product cards
    And the pagination should show "Page 1 of 3"
    And the Previous button should be disabled and the Next button enabled
    When the Worktop customer clicks the Next pagination button
    Then the product listing should show 20 product cards
    And the URL should contain "page=2"
    When the Worktop customer clicks pagination page 3
    Then the product listing should show 5 product cards
    And the Next button should be disabled
    When the Worktop customer navigates back in the browser
    Then the product listing should show 20 product cards
    And the URL should contain "page=2"

  # ASSUMPTION: reference format, 3-D Secure UI and receipt details are proposed
  @e2e @payment @pagination
  Scenario: End-to-end login, browse paginated products and pay by credit card
    When the Worktop user logs in with identifier "customer1@example.com" and password "CustPassw0rd!55"
    Then the Worktop workspace home page should be displayed
    When the Worktop customer opens the product listing from the top navigation
    And the Worktop customer clicks the Next pagination button
    Then the URL should contain "page=2"
    And the product listing should show 20 product cards
    When the Worktop customer adds the first product to the cart
    Then the cart count should be 1
    When the Worktop customer opens the cart and clicks Checkout
    Then the payment form should be displayed with card number, expiry date, CVV and Pay button
    When the Worktop customer enters card number "4111 1111 1111 1111"
    And the Worktop customer enters expiry date "12/30"
    And the Worktop customer enters CVV "123"
    And the Worktop customer clicks Pay
    Then the 3-D Secure modal "Verify your payment" should be displayed
    When the Worktop customer completes 3-D Secure with password "Challenge123"
    Then the payment confirmation reference should match "PAY-\d{8}-\d{6}"

  # ASSUMPTION: failure message wording is proposed, pending business approval
  @payment @negative @idempotency
  Scenario: Payment failures for declined card, insufficient funds, gateway timeout and duplicate submission
    When the Worktop user logs in with identifier "customer1@example.com" and password "CustPassw0rd!55"
    And the Worktop customer opens the cart and clicks Checkout
    And the Worktop customer enters expiry date "12/30"
    And the Worktop customer enters CVV "123"
    And the Worktop customer enters card number "4000 0000 0000 0002"
    And the Worktop customer clicks Pay
    Then the payment banner should read "Your card was declined. Please use a different payment method."
    When the Worktop customer enters card number "4000 0000 0000 9995"
    And the Worktop customer clicks Pay
    Then the payment banner should read "Your card has insufficient funds. Please use a different payment method."
    When the Worktop customer enters card number "4000 0000 0000 0044"
    And the Worktop customer clicks Pay
    Then the payment banner should read "We could not process your payment right now. You have not been charged. Please try again."
    And the user should remain on the payment page with Pay enabled
    When the Worktop customer opens the pending order from Orders
    Then the order status should be "Unpaid"
    When the Worktop customer opens the cart and clicks Checkout
    And the Worktop customer enters card number "4111 1111 1111 1111"
    And the Worktop customer enters expiry date "12/30"
    And the Worktop customer enters CVV "123"
    And the Worktop customer double-clicks Pay
    Then the Pay button should be disabled
    When the Worktop customer completes 3-D Secure with password "Challenge123"
    Then the payment confirmation reference should match "PAY-\d{8}-\d{6}"
