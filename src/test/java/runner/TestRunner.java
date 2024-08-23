package runner;

import com.company.test.totp.svc.ApiException;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import dataProviders.ContextBrowser;
import org.testng.annotations.*;
import testData.ManageTestUsers;
import testData.Utility;

import javax.sewebApp.auth.login.LoginException;
import java.io.IOException;

@CucumberOptions(
        features = "src/test/resources/functionalTests",
        glue = {"stepDefinitions"},
        tags = {"@All"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    private static final Thread thread = Thread.currentThread();
    private static boolean setupDone = false;
    private static boolean tearDownDone = false;

    @BeforeClass
    public void setUp() throws ApiException, IOException, com.company.test.totp.orc.ApiException {
        synchronized (thread) {
            if(!setupDone) {
                setupDone = true;
                Utility.createOrCleanScreenshotDirectory();
                try {
                    ManageTestUsers.createTestUsers();
                } catch (LoginException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @AfterClass
    public void tearDown() {
        synchronized (thread) {
            if( !tearDownDone ){
                Utility.printReportLocation();
                try {
                    ManageTestUsers.deleteTestUsers();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tearDownDone = true;
            }
        }
    }

    @BeforeTest
    @Parameters("testngBrowser")
    public void setCurrentBrowser(String testngBrowser) {
        ContextBrowser contextBrowser = ContextBrowser.getInstance();
        contextBrowser.setBrowser(testngBrowser);
    }

    @DataProvider
    public Object[] feature(){
        return new Object[0];
    }
}