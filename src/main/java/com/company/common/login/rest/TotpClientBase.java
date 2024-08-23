package com.company.common.login.rest;

import com.company.api.client.AccessTokenRetriever;
import com.company.api.config.AuthorizationServerClientConfig;
import configs.AppUrls;
import javax.sewebApp.auth.login.LoginException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public abstract class TotpClientBase {

    private static AccessTokenRetriever tokenRetriever;

    TotpClientBase(String clientId, String clientSecret, String clientScope) {

        String[] scopes = clientScope.split(" ");
        Set<String> scope = new HashSet<>();
        for (String scopeValues : scopes)
            scope.add(scopeValues);

        AuthorizationServerClientConfig authConfig = new AuthorizationServerClientConfig(
                AppUrls.OATH2_TOKEN_ENDPOINT_URL,
                clientId,
                clientSecret,
                scope);
        tokenRetriever = new AccessTokenRetriever(authConfig);
    }

    public static String getAuthorizationHeader() throws IOException, LoginException {
        return "Bearer " + tokenRetriever.getAccessToken();
    }
}
