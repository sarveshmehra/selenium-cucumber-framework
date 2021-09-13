package stepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import enums.Context;
import org.testng.Assert;
import pageObjects.LoginPage;
import runner.TestContext;

public class LoginPageSteps {

    TestContext testContext;
    LoginPage loginPage;
    ForgotPasswordSteps forgotPasswordSteps;

    public LoginPageSteps(TestContext context){
        testContext = context;
        loginPage = testContext.getPageObjectManager().getLoginPage();
    }

    @When ("^the user enters username \"([^\"]*)\" and password \"([^\"]*)\" and clicks login button$")
    public void the_user_enters_username_and_password_and_clicks_login_button(String username, String password) {
        Assert.assertEquals(loginPage.loginPageHeaderIsDisplayed(), true);
        testContext.scenarioContext.setContext(Context.USERNAME, username);
        loginPage.clearEmail();
        loginPage.clearPassword();
        loginPage.enterEmail(username);
        loginPage.enterPassword(password);
        loginPage.clickOnLogin();
    }

    @When ("^the user enters username and new password \"([^\"]*)\" and clicks login button$")
    public void the_user_enters_username_and_new_password_and_clicks_login_button(String password) {
        Assert.assertEquals(loginPage.loginPageHeaderIsDisplayed(), true);
        loginPage.clearEmail();
        loginPage.clearPassword();
        loginPage.enterEmail(testContext.getWebDriverManager().browserSpecificUser());
        loginPage.enterPassword(password);
        loginPage.clickOnLogin();
    }

    @And("checks the checkbox to confirm registration of personal number")
    public void checks_the_checkbox_to_confirm_registration_of_personal_number() {
        loginPage.checkPersonalNumberCheckBox();
    }

    @And("clicks Continue with Bank ID button")
    public void clicks_Continue_with_Bank_ID_button() {
        loginPage.clickOnContinueWithBankId();
    }

    @Then("the user is redirected to bank id login page")
    public void the_user_is_redirected_to_bank_id_login_page() {
        Assert.assertEquals(loginPage.loginWithBankIdTextIsDisplayed(), true);
    }

    @Then("^the user is shown invalid credentials error message$")
    public void the_user_is_shown_invaid_credentials_error_message() {
        Assert.assertEquals(loginPage.invalidCredentialMessageIsDisplayed(),true);
    }

    @Then("^the user is shown too many attempts error message$")
    public void the_user_is_shown_too_many_attempts_error_message() {
        Assert.assertEquals(loginPage.tooManyAttemptsMessageIsDisplayed(),true);
    }

    @Then("^the user is shown incorrect totp code error message$")
    public void the_user_is_shown_incorrect_totp_code_error_message() throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertEquals(loginPage.invalidTotpCodeMessageIsDisplayed(),true);
    }

    @Then("^the user is shown totp device not registered error message$")
    public void the_user_is_shown_totp_device_not_registered_error_message() {
        Assert.assertEquals(loginPage.totpNotRegisteredMessageIsDisplayed(),true);
    }

    @And("^the user clicks on forgot password link$")
    public void the_user_clicks_on_forgot_password_link(){
        loginPage.clickOnForgotPasswordLink();
    }

    @Then("^the user shown unlinked page$")
    public void the_user_shown_unlinked_page() {
        Assert.assertEquals(loginPage.unlinkedPageIsDisplayed(), true);
    }
}
