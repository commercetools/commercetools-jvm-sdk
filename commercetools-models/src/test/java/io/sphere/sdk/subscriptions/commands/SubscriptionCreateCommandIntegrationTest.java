package io.sphere.sdk.subscriptions.commands;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.subscriptions.*;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SubscriptionCreateCommand}.
 */

public class SubscriptionCreateCommandIntegrationTest extends SubscriptionIntegrationTest {

    @Ignore
    @Test
    public void createIronMqChangesSubscription() throws Exception {
        assumeHasIronMqEnv();

        final SubscriptionDraftDsl subscriptionDraft = withCategoryChanges(ironMqSubscriptionDraftBuilder()).build();

        final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
        Subscription subscription = null;
        try {
            subscription = client().executeBlocking(createCommand);

            assertThat(subscription).isNotNull();
            assertThat(subscription.getDestination()).isInstanceOf(IronMqDestination.class);
            assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
            assertThat(subscription.getChanges()).isEqualTo(subscriptionDraft.getChanges());
        } finally {
            SubscriptionFixtures.deleteSubscription(client(), subscription);
        }
    }

    @Ignore
    @Test
    public void createAzureSBChangesSubscription() throws Exception {
        assumeHasAzureSBEnv();

        final SubscriptionDraftDsl subscriptionDraft = withCategoryChanges(azureServiceBusSubscriptionDraftBuilder()).build();

        final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
        Subscription subscription = null;
        try {
            subscription = client().executeBlocking(createCommand);
            assertThat(subscription).isNotNull();
            assertThat(subscription.getDestination()).isInstanceOf(AzureServiceBusDestination.class);
            assertThat(((AzureServiceBusDestination)subscription.getDestination()).getConnectionString()).isNotNull();
            assertThat(subscription.getChanges()).isEqualTo(subscriptionDraft.getChanges());
        } finally {
            SubscriptionFixtures.deleteSubscription(client(), subscription);
            AzureSBUtils.consumeMessages();
        }
    }

    @Ignore
    @Test
    public void createIronMqMessagesSubscription() throws Exception {
        assumeHasIronMqEnv();

        final SubscriptionDraftDsl subscriptionDraft = withCategoryCreatedMessage(ironMqSubscriptionDraftBuilder()).build();

        final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
        Subscription subscription = null;
        try {
            subscription = client().executeBlocking(createCommand);

            assertThat(subscription).isNotNull();
            assertThat(subscription.getDestination()).isInstanceOf(IronMqDestination.class);
            assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
            assertThat(subscription.getMessages()).isEqualTo(subscriptionDraft.getMessages());
        } finally {
            SubscriptionFixtures.deleteSubscription(client(), subscription);
        }
    }

    @Ignore
    @Test
    public void createSqsChangesSubscription() throws Exception {
        assumeHasAwsCliEnv();

        final AmazonSQS sqsClient = AmazonSQSClientBuilder.defaultClient();
        final String queueUrl = SqsUtils.createTestQueue(sqsClient);
        Subscription subscription = null;
        try {
            final SubscriptionDraftDsl subscriptionDraft = withCategoryChanges(sqsSubscriptionDraftBuilder(queueUrl)).build();

            final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
            subscription = client().executeBlocking(createCommand);

            assertThat(subscription).isNotNull();
//            assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
            assertThat(subscription.getChanges()).isEqualTo(subscriptionDraft.getChanges());
        } finally {
            SqsUtils.deleteQueueAndShutdown(queueUrl, sqsClient);
            SubscriptionFixtures.deleteSubscription(client(), subscription);
        }
    }

    @Ignore
    @Test
    public void createSnsChangesSubscription() throws Exception {
        assumeHasAwsCliEnv();

        final AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
        final String topicArn = SnsUtils.createTestTopic(snsClient);
        Subscription subscription = null;
        try {
            final SubscriptionDraftDsl subscriptionDraft = withCategoryChanges(snsSubscriptionDraftBuilder(topicArn)).build();

            final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
            subscription = client().executeBlocking(createCommand);

            assertThat(subscription).isNotNull();
//            assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
            assertThat(subscription.getChanges()).isEqualTo(subscriptionDraft.getChanges());
        } finally {
            SnsUtils.deleteTopicAndShutdown(topicArn, snsClient);
            SubscriptionFixtures.deleteSubscription(client(), subscription);
        }
    }

    @Ignore
    /**
     * The exception  expected here is due to the fact that the topic doesn't exist
     */
    @Test(expected = ErrorResponseException.class)
    public void createPubSubChangesSubscription() throws Exception {
        Subscription subscription = null;
        try {
            final SubscriptionDraftDsl subscriptionDraft = withCategoryChanges(createPubSubSubscription(getSphereClientConfig().getProjectKey())).build();

            final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
            subscription = client().executeBlocking(createCommand);

            assertThat(subscription).isNotNull();
            assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
            assertThat(subscription.getChanges()).isEqualTo(subscriptionDraft.getChanges());
        } finally {
            SubscriptionFixtures.deleteSubscription(client(), subscription);
        }
    }

    @Ignore
    @AfterClass
    public static void cleanUPQueues() throws Exception{
        AzureSBUtils.consumeMessages();
    }
}
