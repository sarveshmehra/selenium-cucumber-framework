package stepDefinitions;

import com.bisnode.common.login.rest.BisnodeIdApi;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import enums.Context;
import org.testng.Assert;
import pageObjects.LoginApp;
import runner.TestContext;

import java.io.IOException;
import java.util.Map;

public class LoginAppSteps {

    TestContext testContext;
    LoginApp loginApp;
    String userId = new BisnodeIdApi().getUser("curity-auto-test@bisnode.com").getId();

    public LoginAppSteps(TestContext context){
        testContext = context;
        loginApp = testContext.getPageObjectManager().getLoginApp();
    }

    @Given("no cookies are set")
    public void no_cookes_are_set() {
        testContext.getWebDriverManager().getDriver().manage().deleteAllCookies();
    }

    @Given("^User is on login-app home page$")
    public void user_is_on_login_app_home_page() {
        loginApp.navigateToLoginApp(testContext.getWebDriverManager().getDriver());
    }

    @When("the user clicks bisnodeId button")
    public void the_user_clicks_bisnodeId_button() {
        testContext.scenarioContext.setContext(Context.ACR,"urn:se:curity:authentication:html-form:bisnode-id");
        loginApp.clickOnBisnodeId();
    }

    @When("^the user clicks loa1 button$")
    public void the_user_clicks_loa1_button() {
        testContext.scenarioContext.setContext(Context.ACR,"loa1");
        loginApp.clickOnLoa1();
    }

    @And("^the user clicks loa2 button$")
    public void the_user_clicks_loa2_button() {
        testContext.scenarioContext.setContext(Context.ACR,"loa2");
        loginApp.clickOnLoa2();
    }

    @When("the user clicks Common Bank ID button")
    public void the_user_clicks_Common_Bank_ID_button() {
        loginApp.clickOnCommonBankId();
    }

    @Then("^the user is shown username and acr$")
    public void the_user_is_shown_username_and_acr() throws InterruptedException {
        Thread.sleep(1000);
        String username = (String)testContext.scenarioContext.getContext(Context.USERNAME);
        String acr = (String)testContext.scenarioContext.getContext(Context.ACR);
        Assert.assertEquals(loginApp.getLoggedInUserName(), username);
        Assert.assertEquals(loginApp.getLoggedInAcr(), acr);
    }

    @Then("^the user is logged in$")
    public void the_user_is_logged_in(){
        Assert.assertEquals(loginApp.getLoggedInUserName(), testContext.getWebDriverManager().browserSpecificUser());
    }

    @And("^the user clicks logout$")
    public void the_user_clicks_logout(){
        loginApp.clickOnLogout();
        Assert.assertEquals(loginApp.logoutTextIsDisplayed(),true);
    }

    @Then("^the user's guid is displayed as subject$")
    public void the_users_guid_is_displayed_as_subject(){
        Assert.assertEquals(loginApp.getGUIDText(), userId);
     }

    @Then("^the user's idtoken has the required claims$")
     public void the_users_idtoken_has_the_required_claims() throws IOException {
        Map<String, Object> claims = loginApp.getIdToken().getClaims();
        Assert.assertEquals(claims.get("profile_id"), userId);
        Assert.assertEquals(claims.get("aud"), "ims-login-app");
        Assert.assertEquals(claims.get("sub"), userId);
        Assert.assertEquals(claims.get("demo_id"), "curity-auto-test");
        Assert.assertEquals(claims.get("preferred_username"), "curity-auto-test@bisnode.com");
        Assert.assertEquals(claims.get("acr"), "urn:se:curity:authentication:html-form:bisnode-id");
     }

    @Then("^the user's accesstoken has the required claims$")
    public void the_users_accesstoken_has_the_required_claims() throws IOException {
         Map<String, Object> claims = loginApp.getAccessToken().getClaims();
        String scopeReceived = (String) claims.get("scope");
        String []scopes = new String[] {"profile", "openid"};
        Assert.assertEquals(claims.get("sub"), userId);
        Assert.assertEquals(claims.get("acr"), "urn:se:curity:authentication:html-form:bisnode-id");
        Assert.assertEquals(claims.get("profile_id"), userId);
        Assert.assertEquals(claims.get("username"), "curity-auto-test@bisnode.com");
        Assert.assertEquals(claims.get("demo_id"), "curity-auto-test");
        Assert.assertTrue(scopeReceived.contains(scopes[0]));
        Assert.assertTrue(scopeReceived.contains(scopes[1]));
    }
}
