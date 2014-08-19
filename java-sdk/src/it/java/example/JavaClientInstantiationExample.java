package example;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sphere.sdk.client.JavaClient;
import io.sphere.sdk.client.JavaClientImpl;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JavaClientInstantiationExample {
    public void instantiate() throws Exception {
        Config defaultValuesFromClasspath = ConfigFactory.load();
        Map<String, Object> values = new HashMap<>();
        values.put("sphere.project", "your project key");
        values.put("sphere.clientId", "your client id");
        values.put("sphere.clientSecret", "your client secret");
        Config config = ConfigFactory.parseMap(values).withFallback(defaultValuesFromClasspath);
        JavaClient client = new JavaClientImpl(config);
    }
}
