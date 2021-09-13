package com.bisnode.common.login.rest;

import com.bisnode.api.client.AccessTokenRetriever;
import com.bisnode.api.config.AuthorizationServerClientConfig;
import com.bisnode.api.scim.User;
import configs.AppUrls;
import com.bisnode.login.simplescimclient.RestScimService;
import com.bisnode.login.simplescimclient.ScimService;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class RestScimBase implements ScimService {
    private final RestScimService service;

    private static AccessTokenRetriever tokenRetriever;


    RestScimBase(String baseUrl, String clientId, String clientSecret, String clientScope) {
        Set<String> scope = new HashSet<>();
        scope.add(clientScope);

        AuthorizationServerClientConfig authConfig = new AuthorizationServerClientConfig(
                AppUrls.OATH2_TOKEN_ENDPOINT_URL,
                clientId,
                clientSecret,
                scope);
        tokenRetriever = new AccessTokenRetriever(authConfig);

        this.service = new RestScimService(baseUrl, tokenRetriever);
    }

    public static String getAuthorizationHeader() throws IOException, LoginException {
        return "Bearer " + tokenRetriever.getAccessToken();
    }

    @Override
    public User getUser(String userName) {
        return service.getUserByUserName(userName);
    }

    @Override
    public List<User> filterBy(String userName) {
        return service.filterBy(userName);
    }

    @Override
    public List<User> filterBy(String userName, String password, boolean active) {
        return service.filterBy(userName, password, active);
    }

    public List<User> filterBy(String userName, String password, String active) {
        return service.filterBy(userName, password, active);
    }

    @Override
    public List<User> filterBy(String email, boolean primary) {
        return service.filterBy(email, primary);
    }

    @Override
    public User update(User user) {
        return service.update(user);
    }

    @Override
    public boolean isAvailable() {
        return service.isAvailable();
    }

    @Override
    public User create(User user) {
        return service.create(user);
    }

    @Override
    public boolean delete(User user) {
        return service.delete(user);
    }

    @Override
    public User getUserByUserName(String userName) {
        return service.getUserByUserName(userName);
    }

    public List<User> listAllUsers() {
        return service.allUsers();
    }
}
