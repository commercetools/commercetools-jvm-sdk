package io.sphere.sdk.subscriptions.commands;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import io.sphere.sdk.subscriptions.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SubscriptionCreateCommand}.
 */
public class SubscriptionCreateCommandIntegrationTest extends IntegrationTest {

    @BeforeClass
    public static void setup() {
        SubscriptionFixtures.deleteSubscription(client(), SubscriptionFixtures.IRON_MQ_SUBSCRIPTION_KEY);
        SubscriptionFixtures.deleteSubscription(client(), SubscriptionFixtures.AWS_SQS_SUBSCRIPTION_KEY);
    }

    @Test
    public void createIronMqChangesSubscription() throws Exception {
        final SubscriptionDraftDsl subscriptionDraft = withCategoryChanges(ironMqSubscriptionDraftBuilder()).build();

        final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
        final Subscription subscription = client().executeBlocking(createCommand);

        assertThat(subscription).isNotNull();
        assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
        assertThat(subscription.getChanges()).isEqualTo(subscriptionDraft.getChanges());
    }

    @Test
    public void createIronMqMessagesSubscription() throws Exception {
        final SubscriptionDraftDsl subscriptionDraft = withCategoryCreatedMessage(ironMqSubscriptionDraftBuilder()).build();

        final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
        final Subscription subscription = client().executeBlocking(createCommand);

        assertThat(subscription).isNotNull();
        assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
        assertThat(subscription.getMessages()).isEqualTo(subscriptionDraft.getMessages());
    }

    @Test
    public void createSqsChangesSubscription() throws Exception {
        final AmazonSQS sqsClient = AmazonSQSClientBuilder.defaultClient();
        final String queueUrl = SqsUtils.createTestQueue(sqsClient);

        try {
            final SubscriptionDraftDsl subscriptionDraft = withCategoryChanges(sqsSubscriptionDraftBuilder(queueUrl)).build();

            final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
            final Subscription subscription = client().executeBlocking(createCommand);

            assertThat(subscription).isNotNull();
            assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
            assertThat(subscription.getChanges()).isEqualTo(subscriptionDraft.getChanges());
        } finally {
            // snsClient is unfortunately not a AutoCloseable, so we have to release it manually
            SqsUtils.deleteQueueAndShutdown(queueUrl, sqsClient);
        }
    }

    @Test
    public void createSnsChangesSubscription() throws Exception {
        final AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();
        final String topicArn = SnsUtils.createTestTopic(snsClient);
        try {
            final SubscriptionDraftDsl subscriptionDraft = withCategoryChanges(snsSubscriptionDraftBuilder(topicArn)).build();

            final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
            final Subscription subscription = client().executeBlocking(createCommand);

            assertThat(subscription).isNotNull();
            assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
            assertThat(subscription.getChanges()).isEqualTo(subscriptionDraft.getChanges());
        } finally {
            SnsUtils.deleteTopicAndShutdown(topicArn, snsClient);
        }
    }
}
