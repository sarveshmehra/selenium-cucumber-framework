package pageObjects;

import java.util.Map;

public class Token{

    private Map<String, String> header;
    private Map<String, Object> claapp;
    private String signature;

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Map<String, Object> getClaapp() {
        return claapp;
    }

    public void setClaapp(Map<String, Object> claapp) {
        this.claapp = claapp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}