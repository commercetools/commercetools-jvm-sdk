package io.sphere.sdk.extensions.commands;

import io.sphere.sdk.extensions.AuthorizationHeaderAuthenticationBuilder;
import io.sphere.sdk.extensions.AzureFunctionsAuthenticationBuilder;
import io.sphere.sdk.extensions.Destination;
import io.sphere.sdk.extensions.HttpDestinationBuilder;
import org.junit.BeforeClass;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.assumeHasAzureFunctionUrl;
import static io.sphere.sdk.subscriptions.SubscriptionFixtures.azureFunctionUrl;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;

public class HttpDestinationIntegrationTest extends AbstractExtensionIntegrationTest {

    @Override
    public Destination getDestination() {
        return HttpDestinationBuilder.of("https://postman-echo.com/post",
                AuthorizationHeaderAuthenticationBuilder.of(randomKey()).build()).build();
    }
}
