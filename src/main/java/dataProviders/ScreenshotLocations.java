package dataProviders;

import java.util.HashMap;

public class ScreenshotLocations{

    private final HashMap<String, String> screenshotPath = new HashMap<>();
    private static ScreenshotLocations screenshotLocations;
    private ScreenshotLocations(){}

    public static synchronized ScreenshotLocations getInstance(){
        if(screenshotLocations == null)
            screenshotLocations = new ScreenshotLocations();
        return screenshotLocations;
    }

    public HashMap<String, String> getScreenshotPath() {
        return screenshotPath;
    }

    public void addScreenshotPath(String scenarioName, String path){
        screenshotPath.put(scenarioName, path);
    }

}
