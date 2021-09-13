Feature: Single Sign On
  Description: This feature will test 1FA and 2FA with LoginApp and DemoApp

  @All @1fa @SSO @LoginApp @DemoApp
  Scenario: A user logged in (1FA) via demo-app is also logged in login-app (1FA)
    Given User is on demo-app home page
    And the user clicks login button
    When the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    Then the user is shown text authenticated with one factor
    When User is on login-app home page
    And the user clicks bisnodeId button
    Then the user is shown username and acr

  @All @2fa @Sms @SSO @LoginApp @DemoApp
  Scenario: A user logged in (2FA) via demo-app is also logged in login-app (2FA) through SMS
    Given User is on demo-app home page
    And the user clicks 2FA login link
    When the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    Then the user is asked to select a strong authentication method
    And the user clicks on button select text message with code
    And the user receives sms with code on phone number "+672351111"
    When the user enters code in text box
    And the user clicks authenticate button
    Then the user is shown text authenticated with two factors
    When User is on login-app home page
    And the user clicks loa2 button
    Then the user is shown username and acr

  @All @2fa @Totp @SSO @LoginApp @DemoApp
  Scenario: A user logged in (2FA) via login-app is also logged in demo-app (2FA) through TOTP
    Given User is on login-app home page
    And the user clicks loa2 button
    And the user clicks the button Bisnode ID
    Then the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    And the user is asked to select a strong authentication method
    When the user clicks on button select security token
    Then the user generates security code
    When the user enters security code in text box
    And the user clicks authenticate button
    Then the user is shown username and acr
    When User is on demo-app home page
    And the user clicks 2FA login link
    Then the user is shown text authenticated with two factors

  @All @2fa @Sms @SSO @LoginApp @DemoApp
  Scenario: A user gets 1FA in login-app and 2FA in demo-app through SMS
    Given User is on login-app home page
    And the user clicks loa2 button
    And the user clicks the button Bisnode ID
    Then the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    And the user is asked to select a strong authentication method
    When User is on demo-app home page
    And the user clicks 2FA login link
    Then the user is asked to select a strong authentication method
    When the user clicks on button select text message with code
    And the user receives sms with code on phone number "+672351111"
    And the user enters code in text box
    And the user clicks authenticate button
    Then the user is shown text authenticated with two factors

  @All @2fa @Totp @SSO @LoginApp @DemoApp
  Scenario: A user gets 1FA in demo-app and 2FA in login-app through TOTP
    Given User is on demo-app home page
    And the user clicks 2FA login link
    When the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    Then the user is asked to select a strong authentication method
    When User is on login-app home page
    And the user clicks loa2 button
    Then the user is asked to select a strong authentication method
    When the user clicks on button select security token
    Then the user generates security code
    When the user enters security code in text box
    And the user clicks authenticate button
    Then the user is shown username and acr