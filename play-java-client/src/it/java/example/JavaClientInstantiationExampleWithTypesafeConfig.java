package example;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sphere.sdk.client.JavaClient;
import io.sphere.sdk.client.JavaClientImpl;

public class JavaClientInstantiationExampleWithTypesafeConfig {
    public void instantiate() throws Exception {
        final Config config = ConfigFactory.load();
        final JavaClient client = new JavaClientImpl(config);
    }
}
