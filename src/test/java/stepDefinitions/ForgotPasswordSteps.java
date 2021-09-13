package stepDefinitions;

import com.bisnode.common.login.mail.MailReader;
import com.bisnode.mail.smtp.test.common.SimpleMessage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import pageObjects.ForgotPassword;
import runner.TestContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordSteps {

    TestContext testContext;
    ForgotPassword forgotPassword;
    private String emailId;

    public ForgotPasswordSteps(TestContext context){
        testContext = context;
        forgotPassword = testContext.getPageObjectManager().getForgotPassword();
    }

    @Then("^the user is asked to enter email address$")
    public void the_user_is_asked_to_enter_email_address() {
        Assert.assertEquals(forgotPassword.resetPasswordButtonIsDisplayed(), true);
    }

    @When("^the user enters email$")
    public void the_user_enters_email() {
        emailId = testContext.getWebDriverManager().browserSpecificUser();
        forgotPassword.enterPrimaryEmail(emailId);
    }

    @And("^the user clicks send email button$")
    public void the_user_clicks_send_email_button(){
        forgotPassword.clickSendEmail();
    }

    @Then("^the user is shown message that email has been sent$")
    public void the_user_is_shown_message_that_email_has_been_sent() throws InterruptedException {
        Thread.sleep(500);
        Assert.assertEquals(forgotPassword.emailSentMessageIsDisplayed(), true);
    }

    @And("^the user receives forgot password email$")
    public void the_user_receives_forgot_password_email() throws InterruptedException {
        SimpleMessage message = waitForNewMailMessageTo(emailId);
        assert message != null;
        assert linkInMail(message) != null;
    }

    @When("^the user clicks on the link in email")
    public void the_user_clicks_on_the_link_in_email() throws InterruptedException {
        String link = linkInMail(waitForNewMailMessageTo(emailId));
        testContext.getWebDriverManager().getDriver().get(link);
    }

    @And("^the user enters new password \"([^\"]*)\"$")
    public void the_user_enters_new_password(String pswd){
        forgotPassword.enterNewPassword(pswd);
    }

    @And("^the user clicks change password button$")
    public void the_user_clicks_change_password_button(){
        forgotPassword.clickChangePassword();
    }

    @Then("^the user is shown message that password has been changed$")
    public void the_user_is_shown_message_that_password_has_been_changed() {
        Assert.assertEquals(forgotPassword.passwordChangedMessageIsDisplayed(), false);
    }

    @And("^the user clicks on log in$")
    public void the_user_clicks_on_log_in(){
        forgotPassword.clickOnLoginLink();
    }

    @Then("^the user is shown message that new password does not comply with password policy$")
    public void the_user_is_shown_message_that_new_password_does_not_comply_with_password_policy() {
        Assert.assertEquals(forgotPassword.errorMessageIsDisplayed(), true);
    }

    private SimpleMessage waitForNewMailMessageTo(String email) throws InterruptedException {
        Thread.sleep(2000);
        return MailReader.getLastMessageSentTo(email);
    }

    private String linkInMail(SimpleMessage message) {
        String messageText = message.getBody();
        return urlIn(messageText);
    }

    private String urlIn(String text) {
        Pattern webLinkPattern = Pattern.compile(".*(http\\S+).*", Pattern.DOTALL);
        Matcher matcher = webLinkPattern.matcher(text);
        if (!matcher.matches()) {
            return null;
        }
        return matcher.group(1);
    }
}
