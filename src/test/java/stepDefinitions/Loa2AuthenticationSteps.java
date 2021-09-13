package stepDefinitions;

import com.bisnode.api.scim.Device;
import com.bisnode.api.scim.User;
import com.bisnode.common.login.rest.BisnodeIdApi;
import com.bisnode.common.login.rest.TotpOrcApi;
import com.bisnode.common.login.rest.TotpSvcApi;
import com.bisnode.common.login.sms.SmsReader;
import com.bisnode.common.login.sms.client.VirtualSms;
import com.bisnode.common.login.totp.TotpCodeGenerator;
import com.bisnode.test.totp.svc.ApiException;
import com.bisnode.test.totp.svc.model.DeviceCreate;
import com.bisnode.test.totp.svc.model.DeviceUpdate;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import enums.Context;
import org.openqa.selenium.remote.http.HttpClient;
import org.openqa.selenium.remote.http.HttpMethod;
import org.openqa.selenium.remote.http.HttpRequest;
import runner.TestContext;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loa2AuthenticationSteps {

    TestContext testContext;
    VirtualSms message;
    static Set<String> usedTotpCodes = new LinkedHashSet<>();

    String totpDeviceId = "3499675a-a416-437e-873d-be1dee5a9380";
    public Loa2AuthenticationSteps(TestContext context){
        testContext = context;
    }

    @Then("^the user receives sms with code on phone number \"([^\"]*)\"$")
    public void the_user_receives_sms_with_code_on_phone_number(String phoneNumber) throws InterruptedException {
        message = waitForNewMessageTo(phoneNumber,10);
        testContext.scenarioContext.setContext(Context.SMSCODE, message.getContent());
    }

    @When("the user click link in sms$")
    public void the_user_click_link_in_sms() throws IOException {
       String link = linkInSms(message);
       HttpClient client = HttpClient.Factory.createDefault().createClient(new URL(link));
       HttpRequest request = new HttpRequest(HttpMethod.GET, link);
       client.execute(request);
    }

    @Then("the user generates security code")
    public void the_user_generates_security_code() throws LoginException, ApiException, IOException, com.bisnode.test.totp.orc.ApiException, InvalidKeyException, NoSuchAlgorithmException, InterruptedException {
        TotpSvcApi totpSvcApi = new TotpSvcApi();
        totpSvcApi.deleteDevice(totpDeviceId);
        totpSvcApi.createDevice(totpDeviceId, getRandomSecret(), DeviceCreate.StateEnum.ACTIVE);
        String totpCode = generateTotpCode(totpDeviceId).trim();
        while( usedTotpCodes.contains(totpCode) ){
            // Same code is not allowed to be used twice
            Thread.sleep(1000);
            totpCode = generateTotpCode(totpDeviceId).trim();
        }
        usedTotpCodes.add(totpCode);
        testContext.scenarioContext.setContext(Context.TOTPCODE, totpCode);
    }

    @And("the totp device is reported lost for username \"([^\"]*)\"")
    public void the_totp_device_is_reported_lost(String username) throws LoginException, ApiException, IOException{
        TotpSvcApi totpSvcApi = new TotpSvcApi();
        totpSvcApi.updateDeviceState(getFirstDeviceForUser(username).getDeviceId(), DeviceUpdate.StateEnum.LOST);
    }

    private Device getFirstDeviceForUser(String username) {
        BisnodeIdApi bisnodeIdApi = new BisnodeIdApi();
        User user = bisnodeIdApi.getUserByUserName(username);
        return user.getDevices().get(0);
    }

    private VirtualSms waitForNewMessageTo(String phoneNumber, int seconds) throws InterruptedException {
        message = null;
        for (int i = 0; i < seconds * 2; i++) {
            Thread.sleep(500);
            message = SmsReader.lastMessageSentTo(phoneNumber);
            if (message != null)
                return message;
        }
        assert message != null: "No message sent to " + phoneNumber + " within " + seconds + " second(s)";
        return message;
    }

    private String urlIn(String text) {
        Pattern webLinkPattern = Pattern.compile(".*(http\\S+).*", Pattern.DOTALL);
        Matcher matcher = webLinkPattern.matcher(text);
        if (!matcher.matches()) {
            return null;
        }
        return matcher.group(1);
    }

    private String linkInSms(VirtualSms message){
        String messageText = message.getContent();
        return urlIn(messageText);
    }

    private static String generateTotpCode(String deviceId) throws LoginException, com.bisnode.test.totp.orc.ApiException, IOException, InvalidKeyException, NoSuchAlgorithmException {
        TotpOrcApi totpOrcApi = new TotpOrcApi();
        String secret = totpOrcApi.getDeviceSecret(deviceId);
        TotpCodeGenerator codeGen = new TotpCodeGenerator();
        String totpcode = codeGen.generateKey(secret);
        return totpcode;
    }

    private static String getRandomSecret() {
        Map<String, Integer> secretMap = new HashMap<>();
        secretMap.putAll(RandomTotpSecret.secretKeyMap);
        String secretKey = getMinKey(secretMap);
        secretMap.put(secretKey, secretMap.get(secretKey) + 1);
        RandomTotpSecret.secretKeyMap.putAll(secretMap);
        return secretKey;
    }

    private static String getMinKey (Map<String, Integer> map) {
        String minKey = null;
        int minValue = Integer.MAX_VALUE;
        for (String key : map.keySet()) {
            int value = map.get(key);
            if (value < minValue) {
                minValue = value;
                minKey = key;
            }
        }
        return minKey;
    }
}
