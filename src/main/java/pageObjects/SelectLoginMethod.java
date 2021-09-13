package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SelectLoginMethod {

    public SelectLoginMethod(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    @FindBy(how = How.CSS, using = "#hero-hdr-welcome")
    private WebElement div_selectLoginMethod;

    @FindBy(how = How.CSS, using = "#bisnode-id")
    private WebElement btn_bisnodeId;

    @FindBy(how = How.CSS, using = "#common-login-bankid")
    private WebElement btn_commonBankId;

    public boolean selectLoginMethodTextIsDisplayed(){
       return div_selectLoginMethod.isDisplayed();

    }

    public void clickOnBisnodeId(){
        btn_bisnodeId.click();
    }

    public void clickOnCommonBankId(){
        btn_commonBankId.click();
    }

}
