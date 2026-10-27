@web @e2e
Feature: Worktop Account Lockout and Document Upload

  As a Worktop user
  I want lockout recovery and clear upload validation
  So that I can regain access and only store valid documents

  @regression @lockout @security
  Scenario: Account lockout after 5 failed logins and recovery via password reset
    Given the user is on the Worktop login page
    When the user fails to sign in 4 times as "qa.lock@example.com" with password "BadPassw0rd!0"
    Then the Worktop login error "Invalid username or password" should be displayed
    When the user enters identifier "qa.lock@example.com" on the Worktop login page
    And the user enters Worktop password "BadPassw0rd!0"
    And the user clicks the Worktop Sign In button
    Then the Worktop login error "Your account has been locked for 30 minutes due to too many failed sign-in attempts. Use \"Forgot password?\" to reset your password." should be displayed
    When the user enters identifier "qa.lock@example.com" on the Worktop login page
    And the user enters Worktop password "LockPassw0rd!11"
    And the user clicks the Worktop Sign In button
    Then the Worktop login error "Your account has been locked for 30 minutes due to too many failed sign-in attempts. Use \"Forgot password?\" to reset your password." should be displayed
    And the user should remain on the Worktop login page
    When the user clicks the Forgot password link
    And the user requests a password reset for "qa.lock@example.com"
    Then the reset confirmation "If an account exists for this email, a reset link has been sent." should be displayed
    When the user opens the password reset link from the email
    And the user sets the reset password to "N3w!LongPassphrase#77"
    Then the reset success message "Your password has been reset. Please sign in." should be displayed
    When the user enters identifier "qa.lock@example.com" on the Worktop login page
    And the user enters Worktop password "N3w!LongPassphrase#77"
    And the user clicks the Worktop Sign In button
    Then the user should land on the Worktop workspace home page

  @regression @upload @validation
  Scenario: Document upload rejections then partial and successful upload to a record
    Given the user is logged in to Worktop as "qa.user1@example.com" with password "OldPassw0rd!23"
    When the user opens record "REC-1001"
    And the user uploads the file "invalid.exe"
    Then the upload banner should show "invalid.exe: file type not supported. Allowed types: PDF, DOCX, XLSX, PNG, JPG."
    When the user uploads the file "large.pdf"
    Then the upload banner should show "large.pdf: file exceeds the 25 MB limit."
    When the user uploads the file "empty.pdf"
    Then the upload banner should show "empty.pdf: file is empty."
    When the user uploads the file "eicar-test.pdf"
    Then the upload banner should show "eicar-test.pdf: file failed the security scan and was not uploaded."
    When the user uploads 11 files named "doc01.pdf" to "doc11.pdf"
    Then the upload banner should show "You can upload a maximum of 10 files at a time."
    When the user uploads the files "valid.pdf" and "invalid.exe"
    Then the upload banner should show "1 of 2 files uploaded. 1 file was rejected."
    And the upload banner should show "invalid.exe: file type not supported. Allowed types: PDF, DOCX, XLSX, PNG, JPG."
    And the document list should contain exactly 1 document "valid.pdf" uploaded by "qa.user1@example.com"
