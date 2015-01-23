package example;

import io.sphere.sdk.client.JavaClient;
import io.sphere.sdk.client.JavaClientFactory;

public class JavaClientInstantiationExample {
    public void instantiate() {
        final JavaClient client = JavaClientFactory.of().createClient("project key", "client id", "client secret");
    }
}