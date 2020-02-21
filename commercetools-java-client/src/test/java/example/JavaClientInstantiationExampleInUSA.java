package example;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientFactory;

public class JavaClientInstantiationExampleInUSA {
    public void instantiate() {
        final SphereClientFactory factory = SphereClientFactory.of();
        final SphereClient client = factory.createClient(
                "jvm-sdk-dev-1", //replace with your project key
                "ELqF0rykXD2fyS8s-IhIPKfQ", //replace with your client id
                "222222222222222222222222222222226",
                "https://auth.us-central1.gcp.commercetools.com/",
                "https://api.us-central1.gcp.commercetools.com"
        );
    }
}
