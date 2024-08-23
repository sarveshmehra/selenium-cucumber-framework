package runner;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import configs.AppUrls;
import dataProviders.ScreenshotLocations;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExtentReportListener implements IReporter {
    private ExtentReports extent;

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

            extent = new ExtentReports(outputDirectory + File.separator + "ExtentReport.html", true);
            extent.addSystemInfo("Environment", AppUrls.environment().toUpperCase());
            extent.addSystemInfo("Environment Type", AppUrls.environentType().toUpperCase());
            extent.addSystemInfo("Headless Browsers", AppUrls.headlessBrowserMode().toUpperCase());
            extent.addSystemInfo("Application URL", AppUrls.app_LOGIN_APP_URL + ", " + AppUrls.app_DEMO_APP_URL);

            for (ISuite suite : suites) {
                Map<String, ISuiteResult> result = suite.getResults();
                for (ISuiteResult r : result.values()) {
                    ITestContext context = r.getTestContext();
                    buildTestNodes(context.getPassedTests(), LogStatus.PASS, "", context.getName());
                    buildTestNodes(context.getFailedTests(), LogStatus.FAIL, "", context.getName());
                    buildTestNodes(context.getSkippedTests(), LogStatus.SKIP, "", context.getName());
                }
            }

            extent.flush();
            extent.close();
        }

    private void buildTestNodes(IResultMap tests, LogStatus status, String screenshotPath, String category) {
        ExtentTest test;
        String featureScenarioName = "";
        String scenarioName = "";

        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                scenarioName = result.getParameters()[0].toString().substring(1, result.getParameters()[0].toString().length() - 1);
                for (int i = 0; i < 2; i++) {
                    featureScenarioName = result.getParameters()[i].toString().substring(1, result.getParameters()[i].toString().length() - 1) + featureScenarioName;
                    if (i == 0)
                        featureScenarioName = " : " + featureScenarioName;
                }

                ScreenshotLocations screenshotLocations =  ScreenshotLocations.getInstance();
                if(screenshotLocations.getScreenshotPath().containsKey(scenarioName))
                    screenshotPath = screenshotLocations.getScreenshotPath().get(scenarioName);

                test = extent.startTest(featureScenarioName);
                test.setStartedTime(getTime(result.getStartMillis()));
                test.setEndedTime(getTime(result.getEndMillis()));
                test.assignCategory(category);

                if (result.getThrowable() != null) {
                    test.log(status, result.getThrowable());
                    test.log(status, test.addScreenCapture(screenshotPath));

                } else {
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
                }

                extent.endTest(test);
                featureScenarioName = "";
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}