Feature: 2FA with TOTP
  Description: These set of tests will go through 2FA with TOTP

  @All @2fa @Totp @LoginApp
  Scenario: User enters wrong totp code first and then correct code
    Given User is on login-app home page
    And the user clicks loa2 button
    And the user clicks the button company ID
    Then the user enters username "webApp-auto-test@company.com" and password "Secret123" and clicks login button
    And the user is asked to select a strong authentication method
    When the user clicks on button select sewebApp token
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message
    And the user generates sewebApp code
    When the user enters sewebApp code in text box
    And the user clicks authenticate button
    Then the user is shown username and acr
    And the user clicks logout

  @All @2fa @Totp @LoginApp
  Scenario: User tries 2fA with TOTP device without having one
    Given User is on login-app home page
    And the user clicks loa2 button
    And the user clicks the button company ID
    Then the user enters username "webApp-auto-test-no-totp@company.com" and password "Secret123" and clicks login button
    And the user is asked to select a strong authentication method
    When the user clicks on button select sewebApp token
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown totp device not registered error message

  @All @2fa @Totp @LoginApp
  Scenario: User tries 2FA with lost Totp device
    Given User is on login-app home page
    And the user clicks loa2 button
    And the user clicks the button company ID
    Then the user enters username "webApp-auto-test@company.com" and password "Secret123" and clicks login button
    And the user is asked to select a strong authentication method
    When the user clicks on button select sewebApp token
    And the user generates sewebApp code
    And the totp device is reported lost for username "webApp-auto-test@company.com"
    When the user enters sewebApp code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message

  @All @2fa @Totp @LoginApp
  Scenario: User enters wrong totp code 10 times (brutforce)
    Given User is on login-app home page
    And the user clicks loa2 button
    And the user clicks the button company ID
    Then the user enters username "webApp-auto-test-brutforce@company.com" and password "Secret123" and clicks login button
    And the user is asked to select a strong authentication method
    When the user clicks on button select sewebApp token
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message
    And the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect totp code error message