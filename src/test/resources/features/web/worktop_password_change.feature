@web @regression @worktop @password
Feature: Worktop Change Password

  As a registered Worktop user
  I want to change my password to one that meets the password policy
  So that my account stays secure and policy violations are rejected

  Background:
    Given the Worktop user is on the Worktop login page

  @smoke
  Scenario: Change password to a value that meets the password policy
    When the Worktop user logs in with identifier "qa.user1@example.com" and password "OldPassw0rd!23"
    Then the Worktop workspace home page should be displayed
    When the Worktop user opens the Change Password form from the user menu
    Then the Change Password form should be displayed
    When the Worktop user enters "OldPassw0rd!23" in the "Current password" field
    And the Worktop user enters "Str0ng!Passphrase#2024" in the "New password" field
    And the Worktop user enters "Str0ng!Passphrase#2024" in the "Confirm new password" field
    And the Worktop user clicks the Save button
    Then a password change success confirmation should be displayed
    And the success confirmation should not contain "Str0ng!Passphrase#2024"
    When the Worktop user logs out
    And the Worktop user logs in with identifier "qa.user1@example.com" and password "Str0ng!Passphrase#2024"
    Then the Worktop workspace home page should be displayed

  # ASSUMPTION: policy and error wording are proposed, pending business approval
  @negative @boundary
  Scenario Outline: Reject new passwords that violate policy rules
    When the Worktop user logs in with identifier "qa.user1@example.com" and password "OldPassw0rd!23"
    And the Worktop user opens the Change Password form from the user menu
    And the Worktop user enters "OldPassw0rd!23" in the "Current password" field
    And the Worktop user enters the "<newPasswordKey>" test password in the "New password" field
    And the Worktop user enters the "<confirmPasswordKey>" test password in the "Confirm new password" field
    And the Worktop user clicks the Save button
    Then the "<field>" field error should read "<error>"
    And the "Current password" field should keep its value "OldPassw0rd!23"

    Examples:
      | newPasswordKey | confirmPasswordKey | field                | error                                          |
      | tooShort7      | tooShort7          | New password         | Password must be at least 12 characters.       |
      | tooShort11     | tooShort11         | New password         | Password must be at least 12 characters.       |
      | tooLong129     | tooLong129         | New password         | Password must not exceed 128 characters.       |
      | noUppercase    | noUppercase        | New password         | Password must contain at least one uppercase letter. |
      | compliant2024  | compliant2025      | Confirm new password | Passwords do not match.                        |
      | previous       | previous           | New password         | You cannot reuse any of your last 5 passwords. |

  @negative
  Scenario: Original password still works after rejected password changes
    When the Worktop user logs in with identifier "qa.user1@example.com" and password "OldPassw0rd!23"
    And the Worktop user logs out
    And the Worktop user logs in with identifier "qa.user1@example.com" and password "OldPassw0rd!23"
    Then the Worktop workspace home page should be displayed
