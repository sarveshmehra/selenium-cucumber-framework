package stepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import pageObjects.DemoApp;
import runner.TestContext;

public class DemoAppSteps {

    TestContext testContext;
    DemoApp demoApp;

    public DemoAppSteps(TestContext context){
        testContext = context;
        demoApp = testContext.getPageObjectManager().getDemoApp();
    }

    @Given("^User is on demo-app home page$")
    public void user_is_on_login_app_home_page() {
        demoApp.navigateToDemoApp(testContext.getWebDriverManager().getDriver());
    }

    @When("^the user clicks login button$")
    public void the_user_clicks_login_button() {
        demoApp.clickLoginLink();
    }

    @And("^the user clicks 2FA login link$")
    public void the_user_clicks_2FA_login_link() {
        demoApp.click2faLoginLink();
    }

    @Then("^the user is shown text authenticated with one factor$")
    public void the_user_is_shown_text_authenticated_with_one_factor() {
        Assert.assertEquals(demoApp.get1faStatus(), "true");
        Assert.assertEquals(demoApp.get2faStatus(), "false");
    }

    @Then("^the user is shown text authenticated with two factors$")
    public void the_user_is_shown_text_authenticated_with_two_factor() {
        Assert.assertEquals(demoApp.get1faStatus(), "true");
        Assert.assertEquals(demoApp.get2faStatus(), "true");
    }
}
