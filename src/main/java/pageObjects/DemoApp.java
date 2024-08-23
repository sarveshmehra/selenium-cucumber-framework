package pageObjects;

import configs.AppUrls;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class DemoApp {

    public DemoApp(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.CSS, using = "#login")
    private WebElement link_login;

    @FindBy(how = How.CSS, using = "#login-2FA")
    private WebElement link_2FALogin;

    @FindBy(how = How.CSS, using = "#logout")
    private WebElement link_logout;

    @FindBy(how = How.CSS, using = "#ROLE_1FA")
    private WebElement txtBox_1fa;

    @FindBy(how = How.CSS, using = "#ROLE_2FA")
    private WebElement txtBox_2fa;

    public void navigateToDemoApp(WebDriver driver){
        driver.get(AppUrls.app_DEMO_APP_URL);
    }

    public void clickLoginLink(){
        link_login.click();
    }

    public void click2faLoginLink(){
        link_2FALogin.click();
    }

    public void clickLogout(){
        link_logout.click();
    }

    public String get1faStatus(){
        return txtBox_1fa.getAttribute("value");
    }

    public String get2faStatus(){
        return txtBox_2fa.getAttribute("value");
    }
}
