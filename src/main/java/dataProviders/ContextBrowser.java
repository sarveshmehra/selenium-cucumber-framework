package dataProviders;

public class ContextBrowser {

    private String browser;
    private ContextBrowser() {}

    public static synchronized ContextBrowser getInstance(){
        return instance.get();
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    @SuppressWarnings("unchecked")
    private static final ThreadLocal<ContextBrowser> instance = ThreadLocal.withInitial(() -> new ContextBrowser());
}
