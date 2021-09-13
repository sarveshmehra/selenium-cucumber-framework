package testData;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class Utility {

    public static  void createOrCleanScreenshotDirectory(){
        try {
            File screenshotDir = new File("build/test-output/screenshots/");
            if (screenshotDir.exists())
                FileUtils.cleanDirectory(screenshotDir);
            else FileUtils.forceMkdir(screenshotDir);
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void printReportLocation(){
        System.out.println("\n Report: build/test-output/ExtentReport.html");
    }
}
