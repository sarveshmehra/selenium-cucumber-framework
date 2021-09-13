package com.bisnode.common.login.rest;

import com.bisnode.test.totp.svc.client.CustomercontrollerApi;
import configs.AppUrls;
import com.bisnode.test.totp.svc.ApiException;
import com.bisnode.test.totp.svc.ApiResponse;
import com.bisnode.test.totp.svc.client.DevicecontrollerApi;
import com.bisnode.test.totp.svc.model.*;
import javax.security.auth.login.LoginException;
import java.io.IOException;

public class TotpSvcApi extends TotpClientBase {

    private final DevicecontrollerApi api = new DevicecontrollerApi();
    private final CustomercontrollerApi customerApi = new CustomercontrollerApi();

    public TotpSvcApi() {
        super("common-login-integration-test", "c9iD7NTUh0CjHj7s0gPe4gcQhr6601Kl9hU4GT9MreDgKkVogBR2qUgAUgcFaAv6", "totp");
        api.getApiClient().setBasePath(AppUrls.TOTP_SVC_API_URL);
    }

    public void deleteDevice(String deviceId) throws IOException, LoginException, ApiException {
        try {
            ApiResponse<ResponseMessageDevice> response = api.deleteDeviceUsingDELETEWithHttpInfo(deviceId, getAuthorizationHeader());
        } catch (ApiException e){
            if(e.getCode() != 404)
                throw e;
        }
    }

    public void createDevice(String deviceId, String serialNumber, DeviceCreate.StateEnum state) throws IOException, LoginException, ApiException {
        DeviceCreate deviceCreate = new DeviceCreate();
        deviceCreate.setCustomerContractId("AutoTestContract");
        deviceCreate.setCustomerContractIdType("AutoTestType");
        deviceCreate.setDeviceId(deviceId);
        deviceCreate.setEncryptionMethod(DeviceCreate.EncryptionMethodEnum.NONE);
        Secret secret = new Secret();
        secret.setDecrypted("KckA2ZBdT3MVUWX+2ysr9tGkN0T3vTptvuG6UJ3iiJipmSBGp1A7WwzG4Ncw/C2z");
        secret.setEncrypted("KckA2ZBdT3MVUWX+2ysr9tGkN0T3vTptvuG6UJ3iiJipmSBGp1A7WwzG4Ncw/C2z");
        deviceCreate.setSecret(secret);
        deviceCreate.setState(state);
        deviceCreate.setTime(0l);
        deviceCreate.setTimeInterval(60l);
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setManufacturer("VERISEC");
        deviceInfo.setModel("Verisec:Generic:HMAC-SHA1");
        deviceInfo.setSerialNumber(serialNumber);
        deviceCreate.setDeviceInfo(deviceInfo);

        ApiResponse<ResponseMessageDevice> response = api.insertDeviceUsingPOSTWithHttpInfo(deviceCreate, getAuthorizationHeader());
    }

    public Device updateDeviceState(String deviceId, DeviceUpdate.StateEnum state) throws IOException, LoginException, ApiException {
        DeviceUpdate deviceUpdate = new DeviceUpdate();
        deviceUpdate.setState(state);

        ApiResponse<ResponseMessageDevice> response = api.updateDeviceUsingPUTWithHttpInfo(deviceId, deviceUpdate, getAuthorizationHeader());
        return response.getData().getData();
    }

    public void insertCustomer(CustomerCreate customerCreate) throws IOException, ApiException, LoginException {
        customerApi.insertCustomerUsingPOST(customerCreate, getAuthorizationHeader());
    }

    public ResponseMessageCustomer customerExists(String customerId) throws IOException, LoginException, ApiException {
        return customerApi.getCustomerUsingGET(customerId, getAuthorizationHeader());
    }
}