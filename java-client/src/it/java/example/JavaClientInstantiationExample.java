package example;

import io.sphere.sdk.client.JavaClient;
import io.sphere.sdk.client.JavaClientFactory;

public class JavaClientInstantiationExample {
    public void instantiate() {
        final JavaClientFactory factory = JavaClientFactory.of();
        final JavaClient client = factory.createClient("your project key", "your client id", "your client secret");
    }
}