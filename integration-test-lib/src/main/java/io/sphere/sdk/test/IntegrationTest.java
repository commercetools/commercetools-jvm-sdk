package io.sphere.sdk.test;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sphere.sdk.client.JavaClientImpl;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.http.ClientRequest;
import org.junit.AfterClass;

import java.util.HashMap;
import java.util.Map;

public abstract class IntegrationTest {

    private static TestClient client;

    protected static TestClient client() {
        if (client == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("sphere.core", System.getenv("JVM_SDK_IT_SERVICE_URL"));
            map.put("sphere.auth", System.getenv("JVM_SDK_IT_AUTH_URL"));
            map.put("sphere.project", System.getenv("JVM_SDK_IT_PROJECT_KEY"));
            map.put("sphere.clientId", System.getenv("JVM_SDK_IT_CLIENT_ID"));
            map.put("sphere.clientSecret", System.getenv("JVM_SDK_IT_CLIENT_SECRET"));
            final Config config = ConfigFactory.parseMap(map).withFallback(ConfigFactory.load());
            client = new TestClient(new JavaClientImpl(config));
        }
        return client;
    }

    protected static <T> T execute(final ClientRequest<T> clientRequest) {
        return client().execute(clientRequest);
    }

    @AfterClass
    public static void stopClient() {
        client.close();
        client = null;
    }
}
