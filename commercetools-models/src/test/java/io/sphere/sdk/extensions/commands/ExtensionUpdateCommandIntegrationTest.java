package io.sphere.sdk.extensions.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.extensions.*;
import io.sphere.sdk.extensions.commands.updateactions.SetDestination;
import io.sphere.sdk.extensions.commands.updateactions.SetKey;
import io.sphere.sdk.extensions.commands.updateactions.SetTriggers;
import io.sphere.sdk.extensions.queries.ExtensionQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.assumeHasAzureFunctionUrl;
import static io.sphere.sdk.subscriptions.SubscriptionFixtures.azureFunctionUrl;
import static io.sphere.sdk.test.SphereTestUtils.asList;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;


public class ExtensionUpdateCommandIntegrationTest extends IntegrationTest {


    private static String URL;

    @BeforeClass
    public static void deleteAllExtensions() {
        assumeHasAzureFunctionUrl();
        URL = azureFunctionUrl();
        PagedQueryResult<Extension> results = client().executeBlocking(ExtensionQuery.of());
        results.getResults()
                .stream()
                .map(ExtensionDeleteCommand::of)
                .map(client()::executeBlocking);
    }

    @Test
    public void setKey() {

        withExtension(client(), extension -> {
            final String newKey = randomKey();
            Extension updatedExtension = client().executeBlocking(ExtensionUpdateCommand.of(extension, SetKey.of(newKey)));
            assertThat(updatedExtension.getKey()).isEqualTo(newKey);
            return updatedExtension;
        });
    }


    @Test
    public void changeTriggers(){

        withExtension(client(), extension -> {
            final List<Trigger> triggers = asList(TriggerBuilder.of(Cart.referenceTypeId(), asList(TriggerType.CREATE)).build());
            Extension updatedExtension = client().executeBlocking(ExtensionUpdateCommand.of(extension, SetTriggers.of(triggers)));
            assertThat(updatedExtension.getTriggers()).containsAll(triggers);
            return updatedExtension;
        });

    }


    @Test
    public void changeDestination(){

        withExtension(client(), extension -> {
            Destination destination = HttpDestinationBuilder.of(URL, AzureFunctionsAuthenticationBuilder.of(randomKey()).build()).build();
            Extension updatedExtension = client().executeBlocking(ExtensionUpdateCommand.of(extension, SetDestination.of(destination)));
            assertThat(updatedExtension.getDestination()).isEqualTo(destination);
            return updatedExtension;
        });

    }


    public void withExtension(final BlockingSphereClient client, final ExtensionDraft extensionDraft, UnaryOperator<Extension> mapper) {

        Extension extension = client.executeBlocking(ExtensionCreateCommand.of(extensionDraft));
        Extension result = mapper.apply(extension);
        client.executeBlocking(ExtensionDeleteCommand.of(result));
    }

    public void withExtension(final BlockingSphereClient client, UnaryOperator<Extension> mapper) {

        List<Trigger> triggers = asList(TriggerBuilder.of(Cart.referenceTypeId(), asList(TriggerType.CREATE, TriggerType.UPDATE)).build());
        Destination destination = HttpDestinationBuilder.of(URL, AzureFunctionsAuthenticationBuilder.of(randomKey()).build()).build();
        withExtension(client, ExtensionDraftBuilder.of(randomKey(), destination, triggers).build(), mapper);

    }


}
