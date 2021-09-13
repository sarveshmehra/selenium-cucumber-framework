package managers;

import org.openqa.selenium.WebDriver;
import pageObjects.*;

public class PageObjectManager {

    private final WebDriver driver;
    private LoginApp loginApp;
    private LoginPage loginPage;
    private SelectLoginMethod selectLoginMethod;
    private SelectStrongAuthenticationMethod selectStrongAuthenticationMethod;
    private ForgotPassword forgotPassword;
    private DemoApp demoApp;

    public PageObjectManager(WebDriver driver){
        this.driver = driver;
    }

    public LoginApp getLoginApp(){
        return (loginApp == null) ? loginApp = new LoginApp(driver) : loginApp;
    }

    public LoginPage getLoginPage(){
        return (loginPage == null) ? loginPage = new LoginPage(driver) : loginPage;
    }

    public SelectLoginMethod getSelectLoginMethod(){
        return (selectLoginMethod == null) ? selectLoginMethod = new SelectLoginMethod(driver) : selectLoginMethod;
    }

    public SelectStrongAuthenticationMethod getSelectStrongAuthenticationMethod(){
        return (selectStrongAuthenticationMethod == null) ? selectStrongAuthenticationMethod = new SelectStrongAuthenticationMethod(driver) : selectStrongAuthenticationMethod;
    }

    public ForgotPassword getForgotPassword(){
        return (forgotPassword == null) ? forgotPassword = new ForgotPassword(driver) : forgotPassword;
    }

    public DemoApp getDemoApp(){
        return (demoApp == null) ? demoApp = new DemoApp(driver) : demoApp;
    }

}
