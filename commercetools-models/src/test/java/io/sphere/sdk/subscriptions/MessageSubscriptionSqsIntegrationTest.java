package io.sphere.sdk.subscriptions;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.messages.CategoryCreatedMessage;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Reference;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static io.sphere.sdk.subscriptions.SubscriptionFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link MessageSubscription} and {@link SqsDestination}.
 */
@org.junit.experimental.categories.Category(SubscriptionMessagingIntegrationTest.class)
public class MessageSubscriptionSqsIntegrationTest extends SqsIntegrationTest {

    @Test
    public void categoryCreated() throws Exception {
        assumeHasAwsCliEnv();

        withCategory(client(), category -> {
            assertEventually(() -> {
                final ReceiveMessageResult result = sqsClient.receiveMessage(queueUrl);

                assertThat(result).isNotNull();
                final List<Message> sqsMessages = result.getMessages();
                assertThat(sqsMessages).hasSize(1);

                final Message sqsMessage = sqsMessages.get(0);
                final MessageSubscriptionPayload<Category> messageSubscriptionPayload =
                        SphereJsonUtils.readObject(sqsMessage.getBody(), MessageSubscriptionPayload.class);

                assertThat(messageSubscriptionPayload).isNotNull();
                final Reference resource = messageSubscriptionPayload.getResource();
                assertThat(resource).isNotNull();
                assertThat(resource.getTypeId()).isEqualTo(Category.referenceTypeId());

                assertThat(messageSubscriptionPayload.hasCompleteMessage()).isTrue();
                assertThat(messageSubscriptionPayload.as(CategoryCreatedMessage.class)).isNotNull();

                sqsClient.deleteMessage(queueUrl, sqsMessage.getReceiptHandle());
            });
        });
    }

    @Override
    protected SubscriptionDraft createSubscriptionDraft() {
        return withCategoryCreatedMessage(sqsSubscriptionDraftBuilder(queueUrl)).build();
    }
}
