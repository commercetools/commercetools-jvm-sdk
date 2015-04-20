package example;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientFactory;

public class JavaClientInstantiationExample {
    public void instantiate() {
        final SphereClientFactory factory = SphereClientFactory.of();
        final SphereClient client = factory.createClient(
                "jvm-sdk-dev-1", //replace with your project key
                "ELqF0rykXD2fyS8s-IhIPKfQ", //replace with your client id
                "222222222222222222222222222222226"); //replace with your client secret
    }
}