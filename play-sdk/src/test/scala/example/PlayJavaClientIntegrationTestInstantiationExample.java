package example;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.client.PlayJavaClientImpl;

import java.util.HashMap;
import java.util.Map;

public class PlayJavaClientIntegrationTestInstantiationExample {
    public void forIntegrationTest() throws Exception {
        final Config defaultValuesFromClasspath = ConfigFactory.load();
        final Map<String, Object> values = new HashMap<>();
        values.put("sphere.project", "your project key");
        values.put("sphere.clientId", "your client id");
        values.put("sphere.clientSecret", "your client secret");
        final Config config = ConfigFactory.parseMap(values).withFallback(defaultValuesFromClasspath);
        final PlayJavaClient client = new PlayJavaClientImpl(config);
    }
}
