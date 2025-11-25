package org.example.edufyimprovedsearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class ClientCredentialsTokenProvider {
    private final RestClient restClient = RestClient.create();
    private final String tokenUri;
    private final String clientId;
    private final String clientSecret;

    private volatile String cachedToken;
    private volatile long expiresAtEpochSeconds;

    public ClientCredentialsTokenProvider(
            @Value("${keycloak.token-uri}") String tokenUri,
            @Value("${keycloak.client-id}") String clientId,
            @Value("${keycloak.client-secret}") String clientSecret
    ) {
        this.tokenUri = tokenUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public synchronized String getAccessToken() {
        long now = System.currentTimeMillis() / 1000;
        if (cachedToken != null && now < (expiresAtEpochSeconds - 30)) {
            return cachedToken;
        }

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");
        form.add("client_id", clientId);
        form.add("client_secret", clientSecret);

        Map<String, Object> resp = restClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .retrieve()
                .body(Map.class);

        cachedToken = (String) resp.get("access_token");
        Number expiresIn = (Number) resp.getOrDefault("expires_in", 300);
        expiresAtEpochSeconds = now + expiresIn.longValue();

        return cachedToken;
    }
}
