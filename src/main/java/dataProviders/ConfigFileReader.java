package dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

        private Properties properties;
        private final String propertyFilePath= "src/Configuration.properties";

        public ConfigFileReader(){
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(propertyFilePath));
                properties = new Properties();
                try {
                    properties.load(reader);
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
            }
        }

        public long getImplicitlyWait() {
            String implicitlyWait = properties.getProperty("implicitlyWait");
            if(implicitlyWait != null) {
                try{
                    return Long.parseLong(implicitlyWait);
                }catch(NumberFormatException e) {
                    throw new RuntimeException("Not able to parse value : " + implicitlyWait + " in to Long");
                }
            }
            return 30;
        }


    public String getFirefoxDriverPath(){
        String ffDriverPath = properties.getProperty("firefoxDriverPath");
        if(ffDriverPath!= null) return ffDriverPath;
        else throw new RuntimeException("firefoxDriverPath not specified in the Configuration.properties file.");
    }

    public String getChromeDriverPath(){
        String chromeDriverPath = properties.getProperty("chromeDriverPath");
        if(chromeDriverPath!= null) return chromeDriverPath;
        else throw new RuntimeException("chromeDriverPath not specified in the Configuration.properties file.");
    }


    public String getIExplorerDriverPath() {
        String ieDriverPath = properties.getProperty("iExplorerDriverPath");
        if(ieDriverPath!= null) return ieDriverPath;
        else throw new RuntimeException("ieDriverPath not specified in the Configuration.properties file.");
    }

        public Boolean getBrowserWindowSize() {
            String windowSize = properties.getProperty("windowMaximize");
            if(windowSize != null) return Boolean.valueOf(windowSize);
            return true;
        }

}
