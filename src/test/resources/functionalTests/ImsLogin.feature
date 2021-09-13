Feature: Ims Login Action
  Description: This feature will test different methods of login

  @All @1fa @LoginApp
  Scenario: Login with bisnodeId
    Given User is on login-app home page
    When the user clicks bisnodeId button
    And the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    Then the user is shown username and acr
    And the user clicks logout

  @All @1fa @LoginApp
  Scenario: A logged in user has its GUID as JWT subject
    Given User is on login-app home page
    When the user clicks bisnodeId button
    And the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    Then the user's guid is displayed as subject
    Then the user's idtoken has the required claims
    Then the user's accesstoken has the required claims
    And the user clicks logout

  @All @1fa @LoginApp
  Scenario: Login to loa1 with bisnodeId
    Given User is on login-app home page
    When the user clicks loa1 button
    Then the user is asked to select login method
    And the user clicks the button Bisnode ID
    When the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    Then the user is shown username and acr
    And the user clicks logout

  @All @2fa @LoginApp
  Scenario: Successful navigation to Bank id
    Given User is on login-app home page
    When the user clicks Common Bank ID button
    Then the user is redirected to bank id login page

  @All @2fa @Sms @LoginApp
  Scenario: User can login with bisnodeId to get 2FA via sms
    Given User is on login-app home page
    And the user clicks loa2 button
    And the user clicks the button Bisnode ID
    Then the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    And the user is asked to select a strong authentication method
    When the user clicks on button select text message with code
    Then the user receives sms with code on phone number "+672351111"
    When the user enters code in text box
    And the user clicks authenticate button
    Then the user is shown username and acr
    And the user clicks logout

  @All @2fa @Totp @LoginApp
  Scenario: User can login with bisnodeId to get 2FA via security token
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
    And the user clicks logout

  @All @2fa @Sms @LoginApp
  Scenario: User can login with bisnodeId to get 2FA via sms link
    Given User is on login-app home page
    And the user clicks loa2 button
    And the user clicks the button Bisnode ID
    Then the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    And the user is asked to select a strong authentication method
    When the user clicks on button select text message with link
    Then the user receives sms with code on phone number "+672351111"
    When the user click link in sms
    Then the user is shown username and acr
    And the user clicks logout

  @All @2fa @Sms @LoginApp
  Scenario: User preference for strong authentication method is set once a method is already selected
    Given User is on login-app home page
    And the user clicks loa2 button
    And the user clicks the button Bisnode ID
    Then the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    And the user is asked to select a strong authentication method
    When the user clicks on button select text message with code
    Then the user receives sms with code on phone number "+672351111"
    When the user enters code in text box
    And the user clicks authenticate button
    Then the user is shown username and acr
    And the user clicks logout
    When the user clicks loa2 button
    And the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    Then User is on strong authentication with text messages page

  @All @1fa @LoginApp
  Scenario: User tries to login with wrong username and password
    Given User is on login-app home page
    When the user clicks bisnodeId button
    And the user enters username "curity-auto-test-incorrect@bisnode.com" and password "Secret123" and clicks login button
    Then the user is shown invalid credentials error message

  @All @2fa @Sms @LoginApp
  Scenario: User enters wrong sms code to get 2FA
    Given User is on login-app home page
    And the user clicks loa2 button
    And the user clicks the button Bisnode ID
    Then the user enters username "curity-auto-test@bisnode.com" and password "Secret123" and clicks login button
    And the user is asked to select a strong authentication method
    When the user clicks on button select text message with code
    Then the user enters wrong code in text box
    And the user clicks authenticate button
    Then the user is shown incorrect sms code error message

  @All @1fa @DemoApp
  Scenario: User tries to login without any service connected (DemoApp)
    Given User is on demo-app home page
    When the user clicks login button
    And the user enters username "curity-auto-test-noservice@bisnode.com" and password "Secret123" and clicks login button
    Then the user shown unlinked page

  @All @1fa @LoginApp
  Scenario: User tries to login with wrong username and password 11 times (brutforce block)
    Given User is on login-app home page
    When the user clicks bisnodeId button
    And the user enters username "curity-auto-test-brutforce@bisnode.com" and password "Secret1234" and clicks login button
    Then the user is shown invalid credentials error message
    When the user enters username "curity-auto-test-brutforce@bisnode.com" and password "Secret1234" and clicks login button
    Then the user is shown invalid credentials error message
    When the user enters username "curity-auto-test-brutforce@bisnode.com" and password "Secret1234" and clicks login button
    Then the user is shown invalid credentials error message
    When the user enters username "curity-auto-test-brutforce@bisnode.com" and password "Secret1234" and clicks login button
    Then the user is shown invalid credentials error message
    When the user enters username "curity-auto-test-brutforce@bisnode.com" and password "Secret1234" and clicks login button
    Then the user is shown invalid credentials error message
    When the user enters username "curity-auto-test-brutforce@bisnode.com" and password "Secret1234" and clicks login button
    Then the user is shown invalid credentials error message
    When the user enters username "curity-auto-test-brutforce@bisnode.com" and password "Secret1234" and clicks login button
    Then the user is shown invalid credentials error message
    When the user enters username "curity-auto-test-brutforce@bisnode.com" and password "Secret1234" and clicks login button
    Then the user is shown invalid credentials error message
    When the user enters username "curity-auto-test-brutforce@bisnode.com" and password "Secret1234" and clicks login button
    Then the user is shown invalid credentials error message
    When the user enters username "curity-auto-test-brutforce@bisnode.com" and password "Secret1234" and clicks login button
    Then the user is shown invalid credentials error message
    When the user enters username "curity-auto-test-brutforce@bisnode.com" and password "Secret1234" and clicks login button
    Then the user is shown too many attempts error message