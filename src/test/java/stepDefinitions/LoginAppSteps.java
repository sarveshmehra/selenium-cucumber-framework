package stepDefinitions;

import com.company.common.login.rest.companyIdApi;
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
    String userId = new companyIdApi().getUser("webApp-auto-test@company.com").getId();

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

    @When("the user clicks companyId button")
    public void the_user_clicks_companyId_button() {
        testContext.scenarioContext.setContext(Context.ACR,"urn:se:webApp:authentication:html-form:company-id");
        loginApp.clickOncompanyId();
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

    @Then("^the user's idtoken has the required claapp$")
     public void the_users_idtoken_has_the_required_claapp() throws IOException {
        Map<String, Object> claapp = loginApp.getIdToken().getClaapp();
        Assert.assertEquals(claapp.get("profile_id"), userId);
        Assert.assertEquals(claapp.get("aud"), "app-login-app");
        Assert.assertEquals(claapp.get("sub"), userId);
        Assert.assertEquals(claapp.get("demo_id"), "webApp-auto-test");
        Assert.assertEquals(claapp.get("preferred_username"), "webApp-auto-test@company.com");
        Assert.assertEquals(claapp.get("acr"), "urn:se:webApp:authentication:html-form:company-id");
     }

    @Then("^the user's accesstoken has the required claapp$")
    public void the_users_accesstoken_has_the_required_claapp() throws IOException {
         Map<String, Object> claapp = loginApp.getAccessToken().getClaapp();
        String scopeReceived = (String) claapp.get("scope");
        String []scopes = new String[] {"profile", "openid"};
        Assert.assertEquals(claapp.get("sub"), userId);
        Assert.assertEquals(claapp.get("acr"), "urn:se:webApp:authentication:html-form:company-id");
        Assert.assertEquals(claapp.get("profile_id"), userId);
        Assert.assertEquals(claapp.get("username"), "webApp-auto-test@company.com");
        Assert.assertEquals(claapp.get("demo_id"), "webApp-auto-test");
        Assert.assertTrue(scopeReceived.contains(scopes[0]));
        Assert.assertTrue(scopeReceived.contains(scopes[1]));
    }
}
