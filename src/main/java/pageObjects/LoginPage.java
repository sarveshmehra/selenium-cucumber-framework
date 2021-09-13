package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.CSS, using = "#email-bis-id")
    private WebElement txt_loginWithBisonodeId;

    @FindBy(how = How.CSS, using = "#email-bis-id")
    private WebElement txtbox_email;

    @FindBy(how = How.CSS, using = "#pwd-bis-id")
    private WebElement txtbox_password;

    @FindBy(how = How.CSS, using = "#login-bis-id-btn")
    private WebElement btn_login;

    @FindBy(how = How.XPATH, using = "//div[@class='checkbox']//label[@for='confirmModalDialogCheckBox']")
    private WebElement chkbox_registredSSN;

    @FindBy(how = How.CSS, using = "#closeModalDialogButton")
    private WebElement btn_continueBankId;

    @FindBy(how = How.XPATH, using = "//a[@class='btn btn--default text-center']")
    private WebElement btn_changeMethod;

    @FindBy(how = How.CSS, using = "#mobilebankid")
    private WebElement txt_loginWithBankId;

    @FindBy(how = How.XPATH, using = "//div[@id='validation-error-incorrect-credentials']//div[@class='alert__text']")
    private WebElement txt_invalidCredentialsMsg;

    @FindBy(how = How.XPATH, using = "//div[@id='error-too-many-attempts']")
    private WebElement div_tooManyAttemptsMsg;

    @FindBy(how = How.XPATH, using = "//div[@id='error-invalid-otp']")
    private WebElement div_invalidTotpCodeMsg;

    @FindBy(how = How.XPATH, using = "//div[@id='error-no-device']")
    private WebElement div_noTotpRegisteredMsg;

    @FindBy(how = How.CSS, using = "#forgot-password-link")
    private WebElement link_forgotPassword;

    @FindBy(how = How.CSS, using = "#failure")
    private WebElement page_unlinked;

    public boolean loginPageHeaderIsDisplayed(){
        return txt_loginWithBisonodeId.isDisplayed();
    }

    public void enterEmail(String email){
        txtbox_email.sendKeys(email);
    }

    public void clearEmail(){
        txtbox_email.clear();
    }

    public void enterPassword(String password){
        txtbox_password.sendKeys(password);
    }

    public void clearPassword(){
        txtbox_password.clear();
    }

    public void clickOnLogin(){
        btn_login.click();
    }

    public void checkPersonalNumberCheckBox(){
        chkbox_registredSSN.click();
    }

    public void clickOnContinueWithBankId(){
        btn_continueBankId.click();
    }

    public void clickOnChangeAuthenticationMethod(){
        btn_changeMethod.click();
    }

    public boolean loginWithBankIdTextIsDisplayed(){
        return txt_loginWithBankId.isDisplayed();
    }

    public boolean invalidCredentialMessageIsDisplayed(){
        return txt_invalidCredentialsMsg.isDisplayed();
    }

    public boolean tooManyAttemptsMessageIsDisplayed(){
        return div_tooManyAttemptsMsg.isDisplayed();
    }

    public boolean invalidTotpCodeMessageIsDisplayed(){
        return div_invalidTotpCodeMsg.isDisplayed();
    }

    public boolean totpNotRegisteredMessageIsDisplayed(){
        return div_noTotpRegisteredMsg.isDisplayed();
    }

    public void clickOnForgotPasswordLink(){
        link_forgotPassword.click();
    }

    public boolean unlinkedPageIsDisplayed(){
        return page_unlinked.isDisplayed();
    }
}
