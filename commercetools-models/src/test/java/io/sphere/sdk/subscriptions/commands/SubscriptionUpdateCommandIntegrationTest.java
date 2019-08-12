package io.sphere.sdk.subscriptions.commands;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.subscriptions.*;
import io.sphere.sdk.subscriptions.commands.updateactions.ChangeDestination;
import io.sphere.sdk.subscriptions.commands.updateactions.SetChanges;
import io.sphere.sdk.subscriptions.commands.updateactions.SetKey;
import io.sphere.sdk.subscriptions.commands.updateactions.SetMessages;
import org.junit.AfterClass;
import org.junit.Ignore;
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
public class SubscriptionUpdateCommandIntegrationTest extends SubscriptionIntegrationTest {

    @Ignore
    @Test
    public void setKeyAzureSBQueue(){
        assumeHasAzureSBEnv();
        setKeyQueue(SubscriptionFixtures::azureServiceBusSubscriptionDraftBuilder);
    }

    @Ignore
    @Test
    public void setChangesAzureSBQueue(){
        assumeHasAzureSBEnv();
        setChangesQueue(SubscriptionFixtures::azureServiceBusSubscriptionDraftBuilder);
    }

    @Ignore
    @Test
    public void setMessageAzureSBQueue(){
        assumeHasAzureSBEnv();
        setMessagesQueue(SubscriptionFixtures::azureServiceBusSubscriptionDraftBuilder);
    }

    @Ignore
    @Test
    public void setKey() {
        assumeHasIronMqEnv();
        setKeyQueue(SubscriptionFixtures::ironMqSubscriptionDraftBuilder);
    }

    @Ignore
    @Test
    public void setChangesIronMq(){
        assumeHasIronMqEnv();
        setChangesQueue(SubscriptionFixtures::ironMqSubscriptionDraftBuilder);
    }

    @Ignore
    @Test
    public void setMessagesIronMq(){
        assumeHasIronMqEnv();
        setMessagesQueue(SubscriptionFixtures::ironMqSubscriptionDraftBuilder);
    }

    @Ignore
    @Test
    public void changeDestination(){
        assumeHasIronMqEnv();
        assumeHasAwsCliEnv();

        final AmazonSQS sqsClient = AmazonSQSClientBuilder.defaultClient();
        final String queueUrl = SqsUtils.createTestQueue(sqsClient);
        Subscription subscription = null;
        try {
            final SubscriptionDraftDsl subscriptionDraft = withCategoryChanges(sqsSubscriptionDraftBuilder(queueUrl)).build();

            final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
            subscription = client().executeBlocking(createCommand);
            final Destination newDestination = ironMqSubscriptionDraftBuilder().getDestination();
            subscription = client().executeBlocking(SubscriptionUpdateCommand.of(subscription,ChangeDestination.of(newDestination)));
            assertThat(subscription).isNotNull();
            assertThat(subscription.getDestination()).isEqualTo(newDestination);
        } finally {
            SqsUtils.deleteQueueAndShutdown(queueUrl, sqsClient);
            SubscriptionFixtures.deleteSubscription(client(), subscription);
        }

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
            final List<ChangeSubscription> newChangeSubscriptions = Collections.singletonList(ChangeSubscription.of(Payment.resourceTypeId()));


            final SubscriptionUpdateCommand setChangesCommand = SubscriptionUpdateCommand.of(subscription, SetChanges.of(newChangeSubscriptions));
            final Subscription updatedSubscription = client().executeBlocking(setChangesCommand);

            final List<ChangeSubscription> changeSubscriptions = updatedSubscription.getChanges();
            assertThat(changeSubscriptions).hasSize(newChangeSubscriptions.size());

            final ChangeSubscription changeSubscription = changeSubscriptions.get(0);
            assertThat(changeSubscription.getResourceTypeId()).isEqualTo(Payment.referenceTypeId());

            return updatedSubscription;
        });
    }

    public void setMessagesQueue(Supplier<SubscriptionDraftBuilder> subscriptionDraftBuilderSupplier) {
        withSubscription(client(), withCategoryCreatedMessage(subscriptionDraftBuilderSupplier.get()), subscription -> {
            final List<MessageSubscription> newMessageSubscriptions = Collections.singletonList(MessageSubscription.of(Payment.resourceTypeId(),Collections.emptyList()));


            final SubscriptionUpdateCommand setMessagesCommand = SubscriptionUpdateCommand.of(subscription, SetMessages.of(newMessageSubscriptions));
            final Subscription updatedSubscription = client().executeBlocking(setMessagesCommand);

            final List<MessageSubscription> messageSubscriptions = updatedSubscription.getMessages();
            assertThat(messageSubscriptions).hasSize(newMessageSubscriptions.size());

            final MessageSubscription messageSubscription = messageSubscriptions.get(0);
            assertThat(messageSubscription.getResourceTypeId()).isEqualTo(Payment.referenceTypeId());


            return updatedSubscription;
        });
    }

    @Ignore
    @AfterClass
    public static void cleanUPQueues() throws Exception{
        AzureSBUtils.consumeMessages();
    }
}
