package io.sphere.sdk.client;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.HashMap;
import java.util.Map;

public class JavaClientFactory implements ClientFactory<JavaClient> {
    private JavaClientFactory() {
    }

    @Override
    public JavaClient createClient(final String projectKey, final String clientId, final String clientSecret, final String authUrl, final String apiUrl) {
        final Config defaultValuesFromClasspath = ConfigFactory.load();
        final Map<String, Object> values = new HashMap<>();
        values.put("sphere.project", projectKey);
        values.put("sphere.clientId", clientId);
        values.put("sphere.clientSecret", clientSecret);
        values.put("sphere.auth", authUrl);
        values.put("sphere.core", apiUrl);
        final Config config = ConfigFactory.parseMap(values).withFallback(defaultValuesFromClasspath);
        final JavaClient client = new JavaClientImpl(config);
        return client;
    }

    public static JavaClientFactory of() {
        return new JavaClientFactory();
    }
}
