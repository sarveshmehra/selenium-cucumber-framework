package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ForgotPassword {

    public ForgotPassword(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.CSS, using = "#primaryEmail")
    private WebElement txtBox_email;

    @FindBy(how = How.CSS, using = "#reset-pass-btn")
    private WebElement btn_resetPswd;

    @FindBy(how = How.XPATH, using = "//div[@class='alert__icon alert__icon--success']")
    private WebElement div_emailSentMessage;

    @FindBy(how = How.CSS, using = "#password1")
    private WebElement txtBox_newPassword;

    @FindBy(how = How.CSS, using = "#set-password")
    private WebElement btn_setPassword;

    @FindBy(how = How.XPATH, using = "//a[@class='alert__link']")
    private WebElement link_login;

    @FindBy(how = How.XPATH, using = "//div[@id='js-error-message']")
    private WebElement div_invalidPasswordMessage;

    public void enterPrimaryEmail(String email){
        txtBox_email.clear();
        txtBox_email.sendKeys(email);
    }

    public void clickSendEmail(){
        btn_resetPswd.click();
    }

    public boolean resetPasswordButtonIsDisplayed(){
        return btn_resetPswd.isDisplayed();
    }

    public boolean emailSentMessageIsDisplayed(){
        return div_emailSentMessage.isDisplayed();
    }

    public void enterNewPassword(String password){
        txtBox_newPassword.sendKeys(password);
    }

    public void clickChangePassword(){
        btn_setPassword.click();
    }

    public boolean passwordChangedMessageIsDisplayed(){
        return btn_setPassword.isEnabled();
    }

    public void clickOnLoginLink(){
        link_login.click();
    }

    public boolean errorMessageIsDisplayed(){
        return div_invalidPasswordMessage.isDisplayed();
    }
}
