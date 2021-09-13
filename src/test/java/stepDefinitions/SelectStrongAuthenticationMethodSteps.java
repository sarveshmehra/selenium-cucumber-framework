package stepDefinitions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import enums.Context;
import org.testng.Assert;
import pageObjects.SelectStrongAuthenticationMethod;
import runner.TestContext;
import java.util.Random;

public class SelectStrongAuthenticationMethodSteps {

    TestContext testContext;
    SelectStrongAuthenticationMethod selectStrongAuthenticationMethod;

    public SelectStrongAuthenticationMethodSteps(TestContext context){
        testContext = context;
        selectStrongAuthenticationMethod = testContext.getPageObjectManager().getSelectStrongAuthenticationMethod();
    }

    @Then("^the user is asked to select a strong authentication method$")
    public void the_user_is_asked_to_select_a_strong_authentication_method() {
        Assert.assertEquals(selectStrongAuthenticationMethod.selectStrongAuthenticationMethodMessageIsDisplayed(),true);
    }

    @When("^the user clicks on button select text message with code$")
    public void the_user_clicks_on_button_select_text_message_with_code() {
        selectStrongAuthenticationMethod.clickOnSMSWithCode();
    }

    @When("the user clicks on button select text message with link")
    public void the_user_clicks_on_button_select_text_message_with_link() {
        selectStrongAuthenticationMethod.clickOnSMSWithLink();
    }

    @When("the user clicks on button select security token")
    public void the_user_clicks_on_button_select_security_token() {
        selectStrongAuthenticationMethod.clickOnSecurityToken();
    }

    @When("the user enters code in text box")
    public void the_user_enters_code_in_text_box() {
       selectStrongAuthenticationMethod.enterSmsCode((String) testContext.scenarioContext.getContext(Context.SMSCODE));
    }

    @When("the user enters wrong code in text box")
    public void the_user_enters_wrong_code_in_text_box() {
        selectStrongAuthenticationMethod.enterSmsCode(getRandomTotpCode());
    }

    @When("the user enters security code in text box")
    public void the_user_enters_security_code_in_text_box() {
        selectStrongAuthenticationMethod.enterTotpCode((String) testContext.scenarioContext.getContext(Context.TOTPCODE));
    }

    @When("the user clicks authenticate button")
    public void the_user_clicks_authenticate_button() {
       selectStrongAuthenticationMethod.clickOnAuthenticate();
    }

    @Then("^User is on strong authentication with text messages page$")
    public void user_is_on_strong_authentication_with_text_messages_page() {
        Assert.assertEquals(selectStrongAuthenticationMethod.selectStrongAuthenticationMethodMessageIsDisplayed(),true);
    }

    @Then("^the user is shown incorrect sms code error message$")
    public void the_user_is_shown_incorrect_sms_code_error_message() {
        Assert.assertEquals(selectStrongAuthenticationMethod.invalidCodeMessageIsDisplayed(), true);
    }

    private static String getRandomTotpCode() {
        Random randomCode = new Random();
        int number = randomCode.nextInt(999999);
        return String.format("%06d", number);
    }
}
