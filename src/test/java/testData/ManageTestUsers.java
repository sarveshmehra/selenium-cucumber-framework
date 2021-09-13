package testData;

import com.bisnode.api.scim.User;
import com.bisnode.common.login.rest.BisnodeIdApi;
import com.bisnode.common.login.rest.TotpOrcApi;
import com.bisnode.common.login.rest.TotpSvcApi;
import com.bisnode.login.simplescimclient.testing.UserBuilder;
import com.bisnode.test.totp.svc.ApiException;
import com.bisnode.test.totp.svc.model.CustomerCreate;
import com.bisnode.test.totp.svc.model.Device;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import static com.bisnode.test.totp.svc.model.DeviceCreate.StateEnum.VACANT;

public class ManageTestUsers {

    private static final TotpSvcApi totpSvcApi = new TotpSvcApi();
    private static final TotpOrcApi totpOrcApi = new TotpOrcApi();
    private static String customerId = "382a4d25-e6ab-47b2-8795-0276754049a8";

    public static void createTestUsers() throws LoginException, ApiException, IOException, com.bisnode.test.totp.orc.ApiException {
        createTestUser("curity-auto-test@bisnode.com");
        createTestUser("curity-auto-test-brutforce@bisnode.com");
        createBasicUser("curity-auto-test-no-totp@bisnode.com");
        createBasicUserWithoutService("curity-auto-test-noservice@bisnode.com");
    }

    public static void deleteTestUsers() throws ApiException, com.bisnode.test.totp.orc.ApiException, LoginException, IOException {
        deleteTestUser("curity-auto-test@bisnode.com");
        deleteTestUser("curity-auto-test-brutforce@bisnode.com");
        deleteTestUser("curity-auto-test-no-totp@bisnode.com");
        deleteTestUser("curity-auto-test-noservice@bisnode.com");
        deleteTestUser("curity-auto-test-password-change-chrome@bisnode.com");
        deleteTestUser("curity-auto-test-password-change-firefox@bisnode.com");
    }

    private static void createTestUser(String userName) throws LoginException, ApiException, IOException, com.bisnode.test.totp.orc.ApiException {

        String userId = UUID.randomUUID().toString();
        String deviceId = UUID.randomUUID().toString();
        deleteTestUser(userName);
        User user = UserBuilder.user()
                .withUserName(userName)
                .withPassword("Secret123")
                .withPrimaryPhoneNumber("+672351111")
                .withUserType("WEB")
                .withExternalId("demo_id", userName.replace("@bisnode.com", ""))
                .thatIsActive()
                .withId(userId)
                .build();

        new BisnodeIdApi().validateOrCreate(user);
        User createdUser = new BisnodeIdApi().getUser(userName);
        assert createdUser != null;

        new Device();
        totpSvcApi.createDevice(deviceId, getRandomDeviceSerialNumber(), VACANT);

        try {
            if (totpSvcApi.customerExists(customerId) == null)
                createNewCustomer();
        } catch (ApiException e) {
            if( e.getCode() == 404 ){
                createNewCustomer();
            }
        }


        totpOrcApi.connectCustomerAndDevice(deviceId, customerId);
        totpOrcApi.connectUserAndDevice(deviceId, userId);
    }

    private static void createBasicUser(String userName) throws ApiException, com.bisnode.test.totp.orc.ApiException, LoginException, IOException {
        String userId = UUID.randomUUID().toString();
        deleteTestUser(userName);
        User user = UserBuilder.user()
                .withUserName(userName)
                .withPassword("Secret123")
                .withPrimaryPhoneNumber("+672351111")
                .withUserType("WEB")
                .withExternalId("demo_id", userName.replace("@bisnode.com", ""))
                .thatIsActive()
                .build();

        user.setId(userId);
        new BisnodeIdApi().validateOrCreate(user);
        User createdUser = new BisnodeIdApi().getUser(userName);
        assert createdUser != null;
    }

    public static void createBasicUserWithoutService(String userName) throws ApiException, com.bisnode.test.totp.orc.ApiException, LoginException, IOException {
        String userId = UUID.randomUUID().toString();
        deleteTestUser(userName);
        User user = UserBuilder.user()
                .withUserName(userName)
                .withPassword("Secret123")
                .withPrimaryPhoneNumber("+672351111")
                .withUserType("WEB")
                .thatIsActive()
                .build();

        user.setId(userId);
        new BisnodeIdApi().validateOrCreate(user);
        User createdUser = new BisnodeIdApi().getUser(userName);
        assert createdUser != null;
    }

    private static void deleteTestUser(String userName) throws com.bisnode.test.totp.orc.ApiException, IOException, ApiException, LoginException {
        User existingUser = new BisnodeIdApi().getUser(userName);
        if (existingUser != null) {
            if (existingUser.getDevices() != null) {
                for (com.bisnode.api.scim.Device device : existingUser.getDevices()) {
                    totpOrcApi.disconnectUserAndDevice(device.getDeviceId(), existingUser.getId());
                    totpSvcApi.deleteDevice(device.getDeviceId());
                }
            }
            new BisnodeIdApi().delete(existingUser);
            existingUser = new BisnodeIdApi().getUser(userName);
            assert existingUser == null;
        } else {
        }
    }

    private static void createNewCustomer() throws ApiException, IOException, LoginException {
        CustomerCreate customerCreate = new CustomerCreate();
        customerCreate.setContractId("AutoTestContract");
        customerCreate.setContractIdType("AutoTestType");
        customerCreate.setCustomerId("382a4d25-e6ab-47b2-8795-0276754049a8");
        customerCreate.setCreatedBy("AutoTest");
        customerCreate.setDisplay("Auto-test");
        totpSvcApi.insertCustomer(customerCreate);
    }

    private static String getRandomDeviceSerialNumber() {
        Random randomCode = new Random();
        int number = randomCode.nextInt(999999999);
        return String.format("%09d", number);
    }
}