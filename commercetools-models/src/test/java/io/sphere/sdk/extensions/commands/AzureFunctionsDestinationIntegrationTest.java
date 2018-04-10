package io.sphere.sdk.extensions.commands;

import io.sphere.sdk.extensions.AzureFunctionsAuthenticationBuilder;
import io.sphere.sdk.extensions.Destination;
import io.sphere.sdk.extensions.HttpDestinationBuilder;
import org.junit.BeforeClass;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.assumeHasAzureFunctionUrl;
import static io.sphere.sdk.subscriptions.SubscriptionFixtures.azureFunctionUrl;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;

public class AzureFunctionsDestinationIntegrationTest extends AbstractExtensionIntegrationTest{

    private static String AZURE_FUNCTION_URL;

    @BeforeClass
    public static void init() {
        assumeHasAzureFunctionUrl();
        AZURE_FUNCTION_URL = azureFunctionUrl();
    }

    @Override
    public Destination getDestination() {
        return HttpDestinationBuilder.of(AZURE_FUNCTION_URL, AzureFunctionsAuthenticationBuilder.of(randomKey()).build()).build();
    }
}
