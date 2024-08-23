package com.company.common.login.sms.client;

import com.company.api.client.AccessTokenRetriever;
import com.company.api.config.AuthorizationServerClientConfig;
import com.google.common.collect.ImmutableMap;
import configs.AppUrls;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.sewebApp.auth.login.LoginException;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SmsClientImpl implements SmsClient {

    private static final String READ_SMS = "{to}/latest";
    private static final String AUTHORIZATION = "Authorization";
    private static final String SCOPE_VALUE = "sms:read:virtual";
    private static final String BEARER = "Bearer ";
    private static final String TOKEN_ENDPOINT_URL = "https://" + AppUrls.environment() + "-login.company.com/as/token.oauth2";
    private static final String CLIENT_ID = "common-login-integration-test";
    private static final String CLIENT_SECRET = "c9iD7NTUh0CjHj7s0gPe4gcQhr6601Kl9hU4GT9MreDgKkVogBR2qUgAUgcFaAv6";
    private Long deletedTimestamp = null;

    final Set<String> scope = new HashSet<>(Collections.singletonList(SCOPE_VALUE));
    private final AccessTokenRetriever tokenClient;
    private final String baseUrl;
    private final RestTemplate template;

    public SmsClientImpl(String baseUrl) {
        this.tokenClient = new AccessTokenRetriever(new AuthorizationServerClientConfig(
                TOKEN_ENDPOINT_URL,
                CLIENT_ID,
                CLIENT_SECRET,
                scope
        ));
        this.baseUrl = baseUrl;
        this.template = new RestTemplate();
    }

    @Override
    public VirtualSms getSms(String number) {
        ResponseEntity<VirtualSms> result = template.exchange(getUri(number, getUriComponentsBuilder()), HttpMethod.GET,
                new HttpEntity<VirtualSms>(getHttpHeaders()), VirtualSms.class);
        return verifySms(result.getBody());
    }

    private VirtualSms verifySms(VirtualSms lastSms) {
        // TODO: Parse date string and compare
        //if (Objects.nonNull(deletedTimestamp) && Instant.ofEpochMilli(lastSms.getTimestamp()).isBefore(Instant.ofEpochMilli(deletedTimestamp))){
        //    return null;
        //}
        return lastSms;
    }

    @Override
    public void deleteAllMessages(String phoneNumber) {
        String deletedPhonenumber = phoneNumber;
        this.deletedTimestamp = Instant.now().toEpochMilli();
    }

    private UriComponentsBuilder getUriComponentsBuilder() {
        return UriComponentsBuilder.fromUriString(this.baseUrl + READ_SMS);
    }

    private URI getUri(String number, UriComponentsBuilder builder) {
        return builder.buildAndExpand(ImmutableMap.of("to", number)).toUri();
    }

    private HttpHeaders getHttpHeaders() {
        String accessToken = null;
        try {
            accessToken = tokenClient.getAccessToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LoginException e) {
            System.out.print(e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION, BEARER + accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
