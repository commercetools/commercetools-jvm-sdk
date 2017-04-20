package io.sphere.sdk.subscriptions;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.subscriptions.commands.SubscriptionCreateCommand;
import io.sphere.sdk.subscriptions.commands.SubscriptionDeleteCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;

import java.util.List;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.sqsSubscriptionDraftBuilder;
import static io.sphere.sdk.subscriptions.SubscriptionFixtures.withCategoryCreatedMessage;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base class for aws sqs integration tests.
 */
public abstract class SqsIntegrationTest extends IntegrationTest {
    protected AmazonSQS sqsClient;
    protected String queueUrl;
    protected Subscription subscription;

    @Before
    public void setup() {
        if (AwsCredentials.hasAwsCliEnv()) {
            sqsClient = AmazonSQSClientBuilder.defaultClient();
            queueUrl = SqsUtils.createTestQueue(sqsClient);

            final SubscriptionDraftDsl subscriptionDraft = withCategoryCreatedMessage(sqsSubscriptionDraftBuilder(queueUrl)).build();

            final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
            subscription = client().executeBlocking(createCommand);

            assertThat(subscription).isNotNull();

            waitForSubscriptionTestMessage();
        }
    }

    @After
    public void clean() {
        if (subscription != null) {
            final SubscriptionDeleteCommand deleteCommand = SubscriptionDeleteCommand.of(subscription);

            client().executeBlocking(deleteCommand);
        }

        SqsUtils.deleteQueueAndShutdown(queueUrl, sqsClient);
    }

    /**
     * Waits for the test subscription message.
     * (as documented at http://dev.commercetools.com/http-api-projects-subscriptions.html#create-a-subscription)
     */
    protected void waitForSubscriptionTestMessage() {
        assertEventually(() -> {
            final ReceiveMessageResult result = sqsClient.receiveMessage(queueUrl);
            assertThat(result).isNotNull();
            final List<Message> sqsMessages = result.getMessages();

            for (final Message sqsMessage : sqsMessages) {
                final ResourceCreatedPayload<Subscription> resourceCreatedPayload =
                        SphereJsonUtils.readObject(sqsMessage.getBody(), ResourceCreatedPayload.class);
                assertThat(resourceCreatedPayload).isNotNull();
                final Reference resource = resourceCreatedPayload.getResource();
                assertThat(resource).isNotNull();
                assertThat(resource.getTypeId()).isEqualTo(Subscription.referenceTypeId());

                sqsClient.deleteMessage(queueUrl, sqsMessage.getReceiptHandle());
            }
        });
    }
}
