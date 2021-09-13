Feature: Forgot Password
  Description: This feature will test changing of password

  @All @Password @LoginApp @WIP
  Scenario: User is able to change password
    Given A user exists to support forgot password
    And User is on login-app home page
    When the user clicks bisnodeId button
    And the user clicks on forgot password link
    Then the user is asked to enter email address
    When the user enters email
    And the user clicks send email button
    Then the user is shown message that email has been sent
    And the user receives forgot password email
    When the user clicks on the link in email
    And the user enters new password "NewPassword!"
    And the user clicks change password button
    Then the user is shown message that password has been changed
    And the user clicks on log in
    When the user enters username and new password "NewPassword!" and clicks login button
    Then the user is logged in
    And the user clicks logout

  @All @Password @LoginApp
  Scenario: User is able to change password having special characters
    Given A user exists to support forgot password
    And User is on login-app home page
    When the user clicks bisnodeId button
    And the user clicks on forgot password link
    Then the user is asked to enter email address
    When the user enters email
    And the user clicks send email button
    Then the user is shown message that email has been sent
    And the user receives forgot password email
    When the user clicks on the link in email
    And the user enters new password "/Password\*'"
    And the user clicks change password button
    Then the user is shown message that password has been changed
    And the user clicks on log in
    When the user enters username and new password "/Password\*'" and clicks login button
    Then the user is logged in
    And the user clicks logout

  @All @Password @LoginApp
  Scenario: User cannot change password that does not comply with password policy
    Given User is on login-app home page
    When the user clicks bisnodeId button
    And the user clicks on forgot password link
    Then the user is asked to enter email address
    When the user enters email
    And the user clicks send email button
    Then the user is shown message that email has been sent
    And the user receives forgot password email
    When the user clicks on the link in email
    And the user enters new password "Password"
    And the user clicks change password button
    Then the user is shown message that new password does not comply with password policy