package io.sphere.sdk.extensions.commands;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.extensions.*;
import io.sphere.sdk.extensions.commands.updateactions.ChangeDestination;
import io.sphere.sdk.extensions.commands.updateactions.ChangeTriggers;
import io.sphere.sdk.extensions.commands.updateactions.SetKey;
import io.sphere.sdk.extensions.commands.updateactions.SetTimeoutInMs;
import io.sphere.sdk.extensions.queries.ExtensionQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.test.SphereTestUtils.asList;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;


public abstract class AbstractExtensionIntegrationTest extends IntegrationTest {


    public abstract Destination getDestination();

    @BeforeClass
    public static void deleteAllExtensions() {
        PagedQueryResult<Extension> results = client().executeBlocking(ExtensionQuery.of());
        results.getResults()
                .stream()
                .map(ExtensionDeleteCommand::of)
                .forEach(client()::executeBlocking);
    }

    @Test
    public void testExtensionForCart(){
        ExtensionResourceType extensionResourceType = ExtensionResourceType.CART;
        final List<Trigger> triggers = asList(TriggerBuilder.of(extensionResourceType, asList(TriggerType.CREATE, TriggerType.UPDATE)).build());
        final Destination destination = getDestination();
        withExtensionDraft(client(), ExtensionDraftBuilder.of(randomKey(), destination, triggers, 1000L).build(), extension -> {
            assertThat(extension.getTriggers()).hasSize(1);
            assertThat(extension.getTriggers().get(0).getResourceTypeId()).isEqualByComparingTo(extensionResourceType);
            assertThat(extension.getTimeoutInMs()).isEqualTo(1000L);
            return extension;
        });
    }

    @Test
    public void testExtensionForCustomer(){
        ExtensionResourceType extensionResourceType = ExtensionResourceType.CUSTOMER;
        final List<Trigger> triggers = asList(TriggerBuilder.of(extensionResourceType, asList(TriggerType.CREATE, TriggerType.UPDATE)).build());
        final Destination destination =getDestination();
        withExtensionDraft(client(), ExtensionDraftBuilder.of(randomKey(), destination, triggers).build(), extension -> {
            assertThat(extension.getTriggers()).hasSize(1);
            assertThat(extension.getTriggers().get(0).getResourceTypeId()).isEqualByComparingTo(extensionResourceType);
            return extension;
        });
    }

    @Test
    public void testExtensionForPayment(){
        ExtensionResourceType extensionResourceType = ExtensionResourceType.PAYMENT;
        final List<Trigger> triggers = asList(TriggerBuilder.of(extensionResourceType, asList(TriggerType.CREATE, TriggerType.UPDATE)).build());
        final Destination destination =getDestination();
        withExtensionDraft(client(), ExtensionDraftBuilder.of(randomKey(), destination, triggers).build(), extension -> {
            assertThat(extension.getTriggers()).hasSize(1);
            assertThat(extension.getTriggers().get(0).getResourceTypeId()).isEqualByComparingTo(extensionResourceType);
            return extension;
        });
    }

    @Test
    public void testExtensionForOrder(){
        ExtensionResourceType extensionResourceType = ExtensionResourceType.ORDER;
        final List<Trigger> triggers = asList(TriggerBuilder.of(extensionResourceType, asList(TriggerType.CREATE, TriggerType.UPDATE)).build());
        final Destination destination =getDestination();
        withExtensionDraft(client(), ExtensionDraftBuilder.of(randomKey(), destination, triggers).build(), extension -> {
            assertThat(extension.getTriggers()).hasSize(1);
            assertThat(extension.getTriggers().get(0).getResourceTypeId()).isEqualByComparingTo(extensionResourceType);
            return extension;
        });
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
    public void setTimeout() {
        withExtension(client(), extension -> {
            final long newTimeout = 500;
            final Extension updatedExtension = client().executeBlocking(ExtensionUpdateCommand.of(extension, SetTimeoutInMs.of(newTimeout)));
            assertThat(updatedExtension.getTimeoutInMs()).isEqualTo(newTimeout);
            return updatedExtension;
        });
    }

    @Test
    public void changeTriggers(){

        withExtension(client(), extension -> {
            final List<Trigger> triggers = asList(TriggerBuilder.of(ExtensionResourceType.CART, asList(TriggerType.CREATE)).build());
            Extension updatedExtension = client().executeBlocking(ExtensionUpdateCommand.of(extension, ChangeTriggers.of(triggers)));
            assertThat(updatedExtension.getTriggers()).containsAll(triggers);
            return updatedExtension;
        });

    }


    @Test
    public void changeDestination(){

        withExtension(client(), extension -> {
            Destination destination =getDestination();
            Extension updatedExtension = client().executeBlocking(ExtensionUpdateCommand.of(extension, ChangeDestination.of(destination)));
            return updatedExtension;
        });

    }


    public void withExtensionDraft(final BlockingSphereClient client, final ExtensionDraft extensionDraft, final UnaryOperator<Extension> mapper) {

        Extension extension = client.executeBlocking(ExtensionCreateCommand.of(extensionDraft));
        Extension result = mapper.apply(extension);
        client.executeBlocking(ExtensionDeleteCommand.of(result));
    }

    public void withExtension(final BlockingSphereClient client,final UnaryOperator<Extension> mapper) {

        List<Trigger> triggers = asList(TriggerBuilder.of(ExtensionResourceType.CART, asList(TriggerType.CREATE, TriggerType.UPDATE)).build());
        Destination destination = getDestination();
        withExtensionDraft(client, ExtensionDraftBuilder.of(randomKey(), destination, triggers).build(), mapper);

    }


}
