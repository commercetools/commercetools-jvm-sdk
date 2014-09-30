package io.sphere.sdk.test;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sphere.sdk.client.JavaClientImpl;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.LocalizedString;
import org.junit.AfterClass;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public abstract class IntegrationTest {

    private static final Random random = new Random();

    protected static LocalizedString randomSlug() {
        return LocalizedString.of(Locale.ENGLISH, "random-slug-" + random.nextInt());
    }

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

    @AfterClass
    public static void stopClient() {
        client.close();
        client = null;
    }

    public static LocalizedString en(final String value) {
        return LocalizedString.of(Locale.ENGLISH, value);
    }
}
