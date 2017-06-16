package io.sphere.sdk.subscriptions.commands;

import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.subscriptions.*;
import io.sphere.sdk.subscriptions.commands.updateactions.SetChanges;
import io.sphere.sdk.subscriptions.commands.updateactions.SetKey;
import io.sphere.sdk.subscriptions.commands.updateactions.SetMessages;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SubscriptionUpdateCommand}.
 */
public class SubscriptionUpdateCommandIntegrationTest extends IntegrationTest {


    @Test
    public void setKeyAzureSBQueue(){
        assumeHasAzureSBEnv();
        setKeyQueue(SubscriptionFixtures::azureServiceBusSubscriptionDraftBuilder);
    }

    @Test
    public void setChangesAzureSBQueue(){
        assumeHasAzureSBEnv();
        setChangesQueue(SubscriptionFixtures::azureServiceBusSubscriptionDraftBuilder);
    }

    @Test
    public void setMessageAzureSBQueue(){
        assumeHasAzureSBEnv();
        setMessagesQueue(SubscriptionFixtures::azureServiceBusSubscriptionDraftBuilder);
    }

    @Test
    public void setKeyIronMq(){
        assumeHasIronMqEnv();
        setKeyQueue(SubscriptionFixtures::ironMqSubscriptionDraftBuilder);
    }

    @Test
    public void setChangesIronMq(){
        assumeHasIronMqEnv();
        setChangesQueue(SubscriptionFixtures::ironMqSubscriptionDraftBuilder);
    }

    @Test
    public void setMessagesIronMq(){
        assumeHasIronMqEnv();
        setMessagesQueue(SubscriptionFixtures::ironMqSubscriptionDraftBuilder);
    }




    public void setKeyQueue(Supplier<SubscriptionDraftBuilder> subscriptionDraftBuilderSupplier) {

        withSubscription(client(), withCategoryChanges(subscriptionDraftBuilderSupplier.get()), subscription -> {
            final String newKey = randomKey();
            final SubscriptionUpdateCommand setKeyCommand = SubscriptionUpdateCommand.of(subscription, SetKey.of(newKey));
            final Subscription updatedSubscription = client().executeBlocking(setKeyCommand);

            assertThat(updatedSubscription.getKey()).isEqualTo(newKey);

            return updatedSubscription;
        });
    }

    public void setChangesQueue(Supplier<SubscriptionDraftBuilder> subscriptionDraftBuilderSupplier) {


        withSubscription(client(), withCategoryChanges(subscriptionDraftBuilderSupplier.get()), subscription -> {
            final List<ChangeSubscription> newChanges = Collections.singletonList(ChangeSubscription.of(Payment.resourceTypeId()));

            final SubscriptionUpdateCommand setChangesCommand = SubscriptionUpdateCommand.of(subscription, SetChanges.of(newChanges));
            final Subscription updatedSubscription = client().executeBlocking(setChangesCommand);

            assertThat(updatedSubscription.getChanges()).isEqualTo(newChanges);

            return updatedSubscription;
        });
    }

    public void setMessagesQueue(Supplier<SubscriptionDraftBuilder> subscriptionDraftBuilderSupplier) {

        withSubscription(client(), withCategoryCreatedMessage(subscriptionDraftBuilderSupplier.get()), subscription -> {
            final List<MessageSubscription> newMessages = Collections.singletonList(MessageSubscription.of(Payment.resourceTypeId(),Collections.emptyList()));

            final SubscriptionUpdateCommand setMessagesCommand = SubscriptionUpdateCommand.of(subscription, SetMessages.of(newMessages));
            final Subscription updatedSubscription = client().executeBlocking(setMessagesCommand);

            assertThat(updatedSubscription.getMessages()).isEqualTo(newMessages);


            return updatedSubscription;
        });
    }
}
