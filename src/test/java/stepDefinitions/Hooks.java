package stepDefinitions;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import dataProviders.ScreenshotLocations;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import runner.TestContext;
import java.io.File;
import java.io.IOException;

import static configs.AppUrls.closeBrowserAfterTest;

public class Hooks {

    TestContext testContext;

    public Hooks(TestContext context) {
        testContext = context;
    }

    @After
    public void AfterSteps(Scenario scenario) {

        ScreenshotLocations locations = ScreenshotLocations.getInstance();
        String targetLocation = "";
        String ScreenshotFolder = "build/test-output/screenshots/";

        if (scenario.isFailed()) {

            File screenshotFile = ((TakesScreenshot) testContext.getWebDriverManager().getDriver()).getScreenshotAs(OutputType.FILE);
            targetLocation = ScreenshotFolder + scenario.getName().replaceAll(" ", "_")
                    + "_" + testContext.getWebDriverManager().readBrowser() + ".png";
            File targetFile = new File(targetLocation);
            String readLocation = targetLocation.replace("build/test-output/","");
            try {
                FileHandler.copy(screenshotFile, targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            locations.addScreenshotPath(scenario.getName(), readLocation);
        }
    }

    @After
    public void closeBrowser(){
        if (closeBrowserAfterTest().equalsIgnoreCase("true")) {
            testContext.getWebDriverManager().closeDriver();
        }
    }
}

