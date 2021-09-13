# Curity UI Integration Tests #
Test suite for Curity UI

## Frameworks ##
- Cucumber
- Selenium Webdriver/Hub
- Testng
- ExtentReport


## Add tests ##
- functionalTests Directory - Write test cases in Gherkin format in feature files.
- pageObject Package - Create class to uniquely identify page objects and methods to be operated on objects.
- stepDefinition Package - Write methods to implement test steps specified in feature file.

 
## Run tests ##
Execute testng.xml to run tests.
Default configuration is read from QA environment.

### Using the script to execute the tests against selenium hub locally: ###

     ./runtests.sh test dev non-bamboo false chrome.xml

## testng.xml ##
testng.xml is input to TestRunner class. You can configure how tests will run.

Example: To run whole test suite in other browser, add: <br>
`<test name="Firefox">
     <parameter name="testngBrowser" value="firefox" />
         <classes>
             <class name="runner.TestRunner" />
         </classes>
     </test>`
     <br>
- Increase number of threads on suite level with parameter: thread-count
- Generate ExtentReport on suite level: ExtentReportListener

## Environment Variables ##

- -Denvironment - Runs tests in specified environment. Possible values: dev, qa, stage, prod. Default: qa.
- -DenvironmentType - Runs test on local machine or remote environment. Possible values: local, remote. Default: remote.
- -DheadlessBrowser - Runs tests in headless browser mode, if set to true. Possible values: true, false. Default: false.
- -DcloseBrowserAfterTest - if set to true, it closes current browser after each test. Possible values: true, false. Default: true.
- -DnewTokenEndpoint - if set to true, tests will use new ims-login-app with Curity token endpoint. Possible values: true, false. Default: false.

## Report ##
After execution HTML report is generated and placed at:
build/test-output/ExtentReport.html