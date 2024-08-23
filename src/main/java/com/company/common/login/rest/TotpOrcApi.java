package com.company.common.login.rest;

import com.company.test.totp.orc.client.DevicecustomerconnectioncontrollerApi;
import com.company.test.totp.orc.client.DeviceuserconnectioncontrollerApi;
import configs.AppUrls;
import com.company.test.totp.orc.ApiException;
import com.company.test.totp.orc.ApiResponse;
import com.company.test.totp.orc.client.KeycontrollerApi;
import com.company.test.totp.orc.model.Key;
import javax.sewebApp.auth.login.LoginException;
import java.io.IOException;

public class TotpOrcApi extends TotpClientBase {

    private final KeycontrollerApi api = new KeycontrollerApi();
    private final DeviceuserconnectioncontrollerApi userConnectApi = new DeviceuserconnectioncontrollerApi();
    private final DevicecustomerconnectioncontrollerApi customerConnectApi =  new DevicecustomerconnectioncontrollerApi();

    public TotpOrcApi() {
        super("common-login-integration-test", "c9iD7NTUh0CjHj7s0gPe4gcQhr6601Kl9hU4GT9MreDgKkVogBR2qUgAUgcFaAv6", "totp companyid");
        api.getApiClient().setBasePath(AppUrls.TOTP_ORC_API_URL);
    }

    public String getDeviceSecret(String deviceId) throws IOException, LoginException, ApiException {
        ApiResponse<Key> response = api.getKeyUsingGETWithHttpInfo(deviceId, getAuthorizationHeader());
        return response.getData().getKey();
    }

    public void connectUserAndDevice(String deviceId, String userId) throws IOException, LoginException, ApiException {
        userConnectApi.connectDeviceAndUserUsingPUT(deviceId, userId, null, getAuthorizationHeader());
    }

    public void connectCustomerAndDevice(String deviceId, String customerId) throws IOException, LoginException, ApiException {
        customerConnectApi.connectDeviceAndCustomerUsingPUT(deviceId, customerId, getAuthorizationHeader());
    }

    public void disconnectUserAndDevice(String deviceId, String userId) throws IOException, LoginException, ApiException {
        userConnectApi.disconnectDeviceAndUserUsingDELETE(deviceId, userId, getAuthorizationHeader());
    }
}