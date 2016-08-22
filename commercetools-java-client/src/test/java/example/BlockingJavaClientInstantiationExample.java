package example;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientFactory;

import java.util.concurrent.TimeUnit;

public class BlockingJavaClientInstantiationExample {
    public void instantiate() {
        try (final BlockingSphereClient sphereClient = createSphereClient()) {
            //do blocking requests here
        }
    }

    private BlockingSphereClient createSphereClient() {
        final SphereClientFactory factory = SphereClientFactory.of();
        final SphereClient asyncSphereClient = factory.createClient(
                "jvm-sdk-dev-1", //replace with your project key
                "ELqF0rykXD2fyS8s-IhIPKfQ", //replace with your client id
                "222222222222222222222222222222226"); //replace with your client secret
        return BlockingSphereClient.of(asyncSphereClient, 20, TimeUnit.SECONDS);
    }
}