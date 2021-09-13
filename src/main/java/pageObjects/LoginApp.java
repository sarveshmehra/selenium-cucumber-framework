package pageObjects;

import configs.AppUrls;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class LoginApp {

    public LoginApp(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//a[@id='urn:se:curity:authentication:html-form:bisnode-id']")
    private WebElement btn_bisnodeId;

    @FindBy(how = How.CSS, using = "#loa1")
    private WebElement btn_loa1;

    @FindBy(how = How.CSS, using = "#loa2")
    private WebElement btn_loa2;

    @FindBy(how = How.CSS, using = "#loa2-mobile")
    private WebElement btn_loa2Mobile;

    @FindBy(how = How.CSS, using = "#common-login-bankid")
    private WebElement btn_commonBankId;

    @FindBy(how = How.CSS, using = "#logout")
    private WebElement btn_logout;

    @FindBy(how = How.XPATH, using = "//h3[@id='username']")
    private WebElement txt_username;

    @FindBy(how = How.CSS, using = "#acr")
    private WebElement txt_acr;

    @FindBy(how = How.XPATH, using = "//div[@id='not-logged-in']")
    private WebElement div_logout;

    @FindBy(how = How.CSS, using = "#subject")
    private WebElement txt_guid;

    @FindBy(how = How.XPATH, using = "//div[@id='id-token']/child::pre/child::code")
    private WebElement txt_idtoken;

    @FindBy(how = How.XPATH, using = "//div[@id='access-token']/child::pre[last()]/child::code")
    private WebElement txt_accesstoken;

    public void navigateToLoginApp(WebDriver driver){
       driver.get(AppUrls.IMS_LOGIN_APP_URL);
    }

    public void clickOnBisnodeId(){
        btn_bisnodeId.click();
    }

    public void clickOnLoa1(){
        btn_loa1.click();
    }

    public void clickOnLoa2(){
        btn_loa2.click();
    }

    public void clickOnLoa2Mobile(){
        btn_loa2Mobile.click();
    }

    public void clickOnCommonBankId(){
        btn_commonBankId.click();
    }

    public void clickOnLogout(){
        btn_logout.click();
    }

    public String getLoggedInUserName(){
        return txt_username.getText().trim();
    }

    public String getLoggedInAcr(){
        return txt_acr.getText().trim();
    }

    public boolean logoutTextIsDisplayed(){
        return div_logout.isDisplayed();
    }

    public String getGUIDText(){
        return txt_guid.getText().trim();
    }

    public Token getIdToken() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String idTokenStr = txt_idtoken.getAttribute("innerHTML");
        Token token = mapper.readValue(idTokenStr, Token.class);
        return token;
    }

    public Token getAccessToken() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String tokenStr = txt_accesstoken.getAttribute("innerHTML");
        Token token =  mapper.readValue(tokenStr, Token.class);
        return token;
    }
}
