package testData;

import com.company.api.scim.User;
import com.company.common.login.rest.companyIdApi;
import com.company.common.login.rest.TotpOrcApi;
import com.company.common.login.rest.TotpSvcApi;
import com.company.login.simplescimclient.testing.UserBuilder;
import com.company.test.totp.svc.ApiException;
import com.company.test.totp.svc.model.CustomerCreate;
import com.company.test.totp.svc.model.Device;
import javax.sewebApp.auth.login.LoginException;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import static com.company.test.totp.svc.model.DeviceCreate.StateEnum.VACANT;

public class ManageTestUsers {

    private static final TotpSvcApi totpSvcApi = new TotpSvcApi();
    private static final TotpOrcApi totpOrcApi = new TotpOrcApi();
    private static String customerId = "382a4d25-e6ab-47b2-8795-0276754049a8";

    public static void createTestUsers() throws LoginException, ApiException, IOException, com.company.test.totp.orc.ApiException {
        createTestUser("webApp-auto-test@company.com");
        createTestUser("webApp-auto-test-brutforce@company.com");
        createBasicUser("webApp-auto-test-no-totp@company.com");
        createBasicUserWithoutService("webApp-auto-test-noservice@company.com");
    }

    public static void deleteTestUsers() throws ApiException, com.company.test.totp.orc.ApiException, LoginException, IOException {
        deleteTestUser("webApp-auto-test@company.com");
        deleteTestUser("webApp-auto-test-brutforce@company.com");
        deleteTestUser("webApp-auto-test-no-totp@company.com");
        deleteTestUser("webApp-auto-test-noservice@company.com");
        deleteTestUser("webApp-auto-test-password-change-chrome@company.com");
        deleteTestUser("webApp-auto-test-password-change-firefox@company.com");
    }

    private static void createTestUser(String userName) throws LoginException, ApiException, IOException, com.company.test.totp.orc.ApiException {

        String userId = UUID.randomUUID().toString();
        String deviceId = UUID.randomUUID().toString();
        deleteTestUser(userName);
        User user = UserBuilder.user()
                .withUserName(userName)
                .withPassword("Secret123")
                .withPrimaryPhoneNumber("+672351111")
                .withUserType("WEB")
                .withExternalId("demo_id", userName.replace("@company.com", ""))
                .thatIsActive()
                .withId(userId)
                .build();

        new companyIdApi().validateOrCreate(user);
        User createdUser = new companyIdApi().getUser(userName);
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

    private static void createBasicUser(String userName) throws ApiException, com.company.test.totp.orc.ApiException, LoginException, IOException {
        String userId = UUID.randomUUID().toString();
        deleteTestUser(userName);
        User user = UserBuilder.user()
                .withUserName(userName)
                .withPassword("Secret123")
                .withPrimaryPhoneNumber("+672351111")
                .withUserType("WEB")
                .withExternalId("demo_id", userName.replace("@company.com", ""))
                .thatIsActive()
                .build();

        user.setId(userId);
        new companyIdApi().validateOrCreate(user);
        User createdUser = new companyIdApi().getUser(userName);
        assert createdUser != null;
    }

    public static void createBasicUserWithoutService(String userName) throws ApiException, com.company.test.totp.orc.ApiException, LoginException, IOException {
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
        new companyIdApi().validateOrCreate(user);
        User createdUser = new companyIdApi().getUser(userName);
        assert createdUser != null;
    }

    private static void deleteTestUser(String userName) throws com.company.test.totp.orc.ApiException, IOException, ApiException, LoginException {
        User existingUser = new companyIdApi().getUser(userName);
        if (existingUser != null) {
            if (existingUser.getDevices() != null) {
                for (com.company.api.scim.Device device : existingUser.getDevices()) {
                    totpOrcApi.disconnectUserAndDevice(device.getDeviceId(), existingUser.getId());
                    totpSvcApi.deleteDevice(device.getDeviceId());
                }
            }
            new companyIdApi().delete(existingUser);
            existingUser = new companyIdApi().getUser(userName);
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