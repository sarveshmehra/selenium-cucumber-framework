package stepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.testng.Assert;
import pageObjects.SelectLoginMethod;
import runner.TestContext;

public class SelectLoginMethodSteps {

    TestContext testContext;
    SelectLoginMethod selectLoginMethod;

    public SelectLoginMethodSteps(TestContext context){
        testContext = context;
        selectLoginMethod = testContext.getPageObjectManager().getSelectLoginMethod();
    }

    @Then("^the user is asked to select login method$")
    public void the_user_is_asked_to_select_login_method() {
        Assert.assertEquals(selectLoginMethod.selectLoginMethodTextIsDisplayed(),true);
    }

    @And("^the user clicks the button company ID$")
    public void the_user_clicks_the_button_with_company_id() {
        selectLoginMethod.clickOncompanyId();
    }
}
