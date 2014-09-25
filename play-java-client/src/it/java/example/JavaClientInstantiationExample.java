package example;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sphere.sdk.client.JavaClient;
import io.sphere.sdk.client.JavaClientImpl;

import java.util.HashMap;
import java.util.Map;

public class JavaClientInstantiationExample {
    public void instantiate() throws Exception {
        final Config defaultValuesFromClasspath = ConfigFactory.load();
        final Map<String, Object> values = new HashMap<>();
        values.put("sphere.project", "your project key");
        values.put("sphere.clientId", "your client id");
        values.put("sphere.clientSecret", "your client secret");
        final Config config = ConfigFactory.parseMap(values).withFallback(defaultValuesFromClasspath);
        final JavaClient client = new JavaClientImpl(config);
    }
}
