package configs;

import java.util.HashMap;
import java.util.Map;

public class AppUrls {

    public static final String companyID_API_URL = getcompanyIdApiUrl();
    public static final String SMTP_TEST_SERVER_URL = getSmtpTestServerUrl();
    public static final String app_LOGIN_APP_URL = getappLoginAppUrl();
    public static final String REMOTE_WEB_DRIVER_URL = getRemoteWebDriverUrl();
    public static final String SMS_URL = getSmsUrl();
    public static final String TOTP_SVC_API_URL = getTotpSvcApiUrl();
    public static final String TOTP_ORC_API_URL = getTotpOrcApiUrl();
    public static final boolean IS_REMOTE_ENVIRONMENT = !environentType().equalsIgnoreCase("local");
    public static final boolean IS_HEADLESS_BROWSER = headlessBrowserMode().equalsIgnoreCase("true");
    public static final String OATH2_TOKEN_ENDPOINT_URL = getOathTokenEndpointUrl();

    private static final String SHJ_URL = getSHJUrl();
    public static final String app_DEMO_APP_URL = getappDemoAppUrl();
    private static final String LOGOUT_URL = getLogoutUrl();
    private static final String NO_CONTEXT_URL = getNoContextUrl();
    private static Map<String, String> urls = new HashMap<>();

    static {
        urls.put("shj start page", SHJ_URL);
        urls.put("app demo app", app_DEMO_APP_URL);
        urls.put("app login app", app_LOGIN_APP_URL);
        urls.put("logout", LOGOUT_URL);
        urls.put("app demo app logout", app_DEMO_APP_URL + "logout");
        urls.put("page with no context", NO_CONTEXT_URL);
    }

    public static String get(String app) {
        return urls.get(app.toLowerCase());
    }

    private static String getColorFromEnv(String env) {
        String colour;
        switch (env) {
            case "dev":
                colour = "blue";
                break;
            case "qa":
                colour = "blue";
                break;
            case "stage":
                colour = "blue";
                break;
            case "prod":
                colour = "orange";
                break;
            default:
                colour = "blue";
                break;
        }
        return colour;
    }

    private static String getappLoginAppUrl() {
        if (newTokenEndpoint().equalsIgnoreCase("true"))
            return property("app_login_app.url", "https://sfs." + environment() + ".aws." + getColorFromEnv(environment()) + ".company.net/poc-sp/");
        else
            return property("app_login_app.url", "https://sfs." + environment() + ".aws." + getColorFromEnv(environment()) + ".company.net/app-login-app/");
    }

    private static String getappDemoAppUrl() {
        return property("app_demo_app.url", "https://sfs." + environment() + ".aws." + getColorFromEnv(environment()) + ".company.net/app-demo-app/");
    }

    private static String getSmsUrl() {
        return property("sms.url", "https://sfs." + environment() + ".aws." + getColorFromEnv(environment()) + ".company.net/virtual-sms-adapter/v1/api/sms/receivers/");
    }

    private static String getSmtpTestServerUrl() {
            return property("smtp_test_server.url", "https://sfs." + environment() + ".aws." + getColorFromEnv(environment()) + ".company.net/smtp-test-server");
    }


    private static String getLogoutUrl() {
        return property("logout.url", "https://" + ("prod".equals(environment()) ? "" : environment() + "-") + "login.company.com/identityservice/authn/authenticate/logout");
    }

    private static String getSHJUrl() {
        switch (environment()) {
            case "dev":
                return property("shj.url", "https://utv-web-shj-se.infra.soliditet.com/shjwebb/");
            case "qa":
                return property("shj.url", "https://test1-web-shj-se.infra.soliditet.com/shjwebb/");
            case "stage":
                return property("shj.url", "https://stage-web-shj-se.infra.soliditet.com/shjwebb/");
            default:
                throw new IllegalArgumentException(environment() + " environment is not defined for SHJ");
        }
    }

    private static String getcompanyIdApiUrl() {
        return property("companyid-api.url", "https://sfs." + environment() + ".aws." + getColorFromEnv(environment()) + ".company.net/companyid/v2/");
    }

    private static String property(String propertyName, String defaultValue) {
        if (System.getProperty(propertyName) == null) {
            return defaultValue;
        }
        return System.getProperty(propertyName);
    }

    public static String environment() {
        return System.getProperty("environment", "dev");
    }

    private static String getRemoteWebDriverUrl() {
        return System.getProperty("remote.driver.url");
    }

    private static String getNoContextUrl() {
        return System.getProperty("nocontext.url", "https://" + environment() + "-login.company.com/identityservice/authn/authenticate/loa2");
    }

    private static String getTotpSvcApiUrl() {
        return property("totp.svc.api.url", "https://sfs." + environment() + ".aws." + getColorFromEnv(environment()) + ".company.net/app-totp-s/v1/");
    }

    private static String getTotpOrcApiUrl() {
        return property("totp.orc.api.url", "https://sfs." + environment() + ".aws." + getColorFromEnv(environment()) + ".company.net/app-totp-o/v1/");
    }

    private static String getOathTokenEndpointUrl() {
        return property("oath2.token.endpoint.url", "https://" + environment() + "-login.company.com/as/token.oauth2");
    }

    public static String environentType() {
        return System.getProperty("environmentType", "remote");
    }

    public static String closeBrowserAfterTest() {
        return System.getProperty("closeBrowserAfterTest", "true");
    }

    public static String headlessBrowserMode() {
        return System.getProperty("headlessBrowser", "false");
    }

    public static String newTokenEndpoint() {return System.getProperty("newTokenEndpoint", "false");}
}
