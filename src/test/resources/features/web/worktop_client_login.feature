@web @login
Feature: Worktop Client Selection and Login Errors

  As a Worktop user
  I want clear client guidance and non-disclosing login errors
  So that I can sign in to the right client without leaking account information

  @regression @smoke
  Scenario: Switch client from the wrong-client message on the login page
    Given the user is on the Worktop login page
    When the user selects client "Client A" on the Worktop login page
    And the user enters identifier "qa.clientb@example.com" on the Worktop login page
    And the user enters Worktop password "ClientBPassw0rd!42"
    And the user clicks the Worktop Sign In button
    Then a wrong-client message with a "Switch to Client B" action should be displayed
    And no generic invalid login error should be displayed
    When the user clicks the switch client action
    Then the selected Worktop client should be "Client B"
    And the Worktop identifier field should show "qa.clientb@example.com"
    And the Worktop password field should be empty
    When the user enters Worktop password "ClientBPassw0rd!42"
    And the user clicks the Worktop Sign In button
    Then the user should land on the Worktop workspace home page

  @regression @security
  Scenario Outline: Login with invalid credentials shows generic non-disclosing error
    Given the user is on the Worktop login page
    When the user enters identifier "<identifier>" on the Worktop login page
    And the user enters Worktop password "<password>"
    And the user clicks the Worktop Sign In button
    Then the Worktop login error "Invalid username or password" should be displayed
    And the user should remain on the Worktop login page
    And the Worktop login error should not mention client or account status

    Examples:
      | identifier                | password         |
      | qa.user1@example.com      | WrongPassw0rd!1  |
      | unknown.user@example.com  | AnyPassw0rd!1    |

  @regression @security
  Scenario: Wrong-client login negatives do not disclose client association
    Given the user is on the Worktop login page
    When the user selects client "Client A" on the Worktop login page
    And the user enters identifier "qa.noclient@example.com" on the Worktop login page
    And the user enters Worktop password "NoClientPassw0rd!1"
    And the user clicks the Worktop Sign In button
    Then the Worktop login error "Invalid username or password" should be displayed
    And no switch client action should be offered
    When the user enters identifier "qa.multi@example.com" on the Worktop login page
    And the user enters Worktop password "MultiPassw0rd!1"
    And the user clicks the Worktop Sign In button
    Then the Worktop login error "You cannot sign in to this client with this identifier. Select your client and try again." should be displayed
    And a "Change client" action should be offered
    And the Worktop login page should not display "Client B" or "Client C"
    When the user clicks the switch client action
    Then the Worktop identifier field should show "qa.multi@example.com"
    And the Worktop password field should be empty
    When the user selects client "Client A" on the Worktop login page
    And the user enters identifier "qa.clientb@example.com" on the Worktop login page
    And the user enters Worktop password "WrongPassw0rd!1"
    And the user clicks the Worktop Sign In button
    Then the Worktop login error "Invalid username or password" should be displayed
    And the Worktop login page should not display "Client B"
    And no authenticated Worktop session should exist
