package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SelectStrongAuthenticationMethod {

    public SelectStrongAuthenticationMethod(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.CSS, using = "#hero-hdr-welcome")
    private WebElement div_selectStrongAutheticationMethod;

    @FindBy(how = How.CSS, using = "#sms-otp")
    private WebElement btn_selectTextMessageCode;

    @FindBy(how = How.CSS, using = "#sms")
    private WebElement btn_selectTextMessageLink;

    @FindBy(how = How.CSS, using = "#totp")
    private WebElement btn_selectSewebAppToken;

    @FindBy(how = How.CSS, using = "#encap")
    private WebElement btn_selectSecureApp;

    @FindBy(how = How.XPATH, using = "//input[@id='otp']")
    private WebElement txtbox_SMSCode;

    @FindBy(how = How.CSS, using = "#otp")
    private WebElement txtbox_totpCode;

    @FindBy(how = How.CSS, using = "#login-bis-id-btn")
    private WebElement btn_authenticate;

    @FindBy(how = How.XPATH, using = "//div[@id='error-invalid-otp']")
    private WebElement div_InvalidCodeErrMsg;

    public boolean selectStrongAuthenticationMethodMessageIsDisplayed(){
        return div_selectStrongAutheticationMethod.isDisplayed();
    }

    public void clickOnSMSWithCode(){
        btn_selectTextMessageCode.click();
    }

    public void clickOnSMSWithLink(){
        btn_selectTextMessageLink.click();
    }

    public void clickOnSecureApp(){
        btn_selectSecureApp.click();
    }

    public void clickOnSewebAppToken(){
        btn_selectSewebAppToken.click();
    }

    public void enterSmsCode(String code){
        txtbox_SMSCode.sendKeys(code);
    }

    public void clickOnAuthenticate(){
        btn_authenticate.click();
    }

    public void enterTotpCode(String totpCode){
        txtbox_totpCode.sendKeys(totpCode);
    }

    public boolean invalidCodeMessageIsDisplayed(){
        return div_InvalidCodeErrMsg.isDisplayed();
    }
}
