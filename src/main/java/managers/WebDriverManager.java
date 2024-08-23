package managers;


import dataProviders.ContextBrowser;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static configs.AppUrls.*;


public class WebDriverManager {

    private WebDriver driver;
    private static final String FIREFOX_DRIVER_PROPERTY = "webdriver.gecko.driver";
    private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";

    public WebDriver getDriver() {
        getDriver(readBrowser());
        return driver;
    }

    public String readBrowser() {
        ContextBrowser contextBrowser = ContextBrowser.getInstance();
        return contextBrowser.getBrowser();
    }

    private WebDriver getDriver(String testngBrowser) {
        if (this.driver == null) {
            createDriver(testngBrowser);
        }
        return driver;
    }

    private void createDriver(String testngBrowser) {
        try {
            if ("firefox".equalsIgnoreCase(testngBrowser)) {
                this.driver = createFirefoxDriver();
            } else if ("chrome".equalsIgnoreCase(testngBrowser)) {
                this.driver = createChromeDriver();
            } else {
                throw new RuntimeException("Invalid browser option specified");
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }

        if (FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize()) {
            this.driver.manage().window().maximize();
        }

        driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(), TimeUnit.SECONDS);
    }

    private WebDriver createChromeDriver() throws MalformedURLException {
        WebDriver webDriver;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(IS_HEADLESS_BROWSER);
        chromeOptions.addArguments("--lang=en");
        chromeOptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        chromeOptions.addArguments("--no-sandbox"); // Bypass OS sewebApp model
        if (IS_REMOTE_ENVIRONMENT) {
            webDriver = new RemoteWebDriver(new URL(REMOTE_WEB_DRIVER_URL), chromeOptions);
        } else {
            System.setProperty(CHROME_DRIVER_PROPERTY, FileReaderManager.getInstance().getConfigReader().getChromeDriverPath());
            webDriver = new ChromeDriver(chromeOptions);
        }
        return webDriver;
    }

    private WebDriver createFirefoxDriver() throws MalformedURLException {
        WebDriver webdriver;
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        System.setProperty(FIREFOX_DRIVER_PROPERTY, FileReaderManager.getInstance().getConfigReader().getFirefoxDriverPath());
        firefoxOptions.setHeadless(IS_HEADLESS_BROWSER);
        firefoxOptions.addArguments(firefoxOptions.getVersion());
        firefoxOptions.addArguments("--lang=en");
        if (IS_REMOTE_ENVIRONMENT) {
            webdriver = new RemoteWebDriver(new URL(REMOTE_WEB_DRIVER_URL), firefoxOptions);
        } else {
            System.setProperty(CHROME_DRIVER_PROPERTY, FileReaderManager.getInstance().getConfigReader().getChromeDriverPath());
            webdriver = new FirefoxDriver(firefoxOptions);
        }
        return webdriver;
    }

    public String browserSpecificUser(){
        return "webApp-auto-test-password-change-" + readBrowser().toLowerCase() + "@company.com";
    }

    public void closeDriver() {
        // driver.close();
        driver.quit();
    }

}
