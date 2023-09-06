package dev.svero.playground.helloworld.utils;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyCloakClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyCloakClient.class);
    private static final String ACCESS_TOKEN_KEY = "access_token";

    private final HttpUtils httpUtils;
    private final String keyCloakBaseUrl;
    private final String keyCloakRealm;

    public KeyCloakClient(HttpUtils httpUtils, String keyCloakBaseUrl, String keyCloakRealm) {
        this.httpUtils = httpUtils;
        this.keyCloakBaseUrl = keyCloakBaseUrl;
        this.keyCloakRealm = keyCloakRealm;
    }

    public String getAccessToken(final String jsonWebToken) {
        String accessToken = null;

        String tokenRequestData = "grant_type=client_credentials" +
                "&client_assertion_type=urn:ietf:params:oauth:client-assertion-type:jwt-bearer" +
                "&client_assertion=" + jsonWebToken;
        LOGGER.debug("Token request data: {}", tokenRequestData);

        final String url = String.format("%s/realms/%s/protocol/openid-connect/token",
                keyCloakBaseUrl, keyCloakRealm);
        LOGGER.debug("Token request url: {}", url);

        String result = httpUtils.postRequest(url, tokenRequestData);
        LOGGER.debug("Raw result: {}", result);

        JSONObject jsonObject = new JSONObject(result);
        if (jsonObject.has(ACCESS_TOKEN_KEY)) {
            accessToken = jsonObject.getString(ACCESS_TOKEN_KEY);
        } else {
            LOGGER.error("No access token found in received data!");
        }

        return accessToken;
    }
}
