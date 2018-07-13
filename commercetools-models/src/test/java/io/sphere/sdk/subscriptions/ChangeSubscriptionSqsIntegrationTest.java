package io.sphere.sdk.subscriptions;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.subscriptions.SubscriptionFixtures.assumeHasAwsCliEnv;
import static io.sphere.sdk.subscriptions.SubscriptionFixtures.sqsSubscriptionDraftBuilder;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link ChangeSubscription} and {@link SqsDestination}.
 */
@org.junit.experimental.categories.Category(SubscriptionMessagingIntegrationTest.class)
public class ChangeSubscriptionSqsIntegrationTest extends SqsIntegrationTest {

    @Test
    public void productCreated() throws Exception {
        assumeHasAwsCliEnv();

        withProduct(client(), product -> {
            assertEventually(() -> {
                final ReceiveMessageResult result = sqsClient.receiveMessage(queueUrl);

                assertThat(result).isNotNull();
                final List<Message> sqsMessages = result.getMessages();
                assertThat(sqsMessages).hasSize(1);

                final Message sqsMessage = sqsMessages.get(0);
                sqsClient.deleteMessage(queueUrl, sqsMessage.getReceiptHandle());

                final ResourceCreatedPayload<Product> resourceCreatedPayload =
                        SphereJsonUtils.readObject(sqsMessage.getBody(), ResourceCreatedPayload.class);
                assertThat(resourceCreatedPayload).isNotNull();
                assertThat(resourceCreatedPayload.getModifiedAt()).isNotNull();
                final Reference resource = resourceCreatedPayload.getResource();

                assertThat(resource).isNotNull();
                assertThat(resource.getTypeId()).isEqualTo(Product.referenceTypeId());

                sqsClient.deleteMessage(queueUrl, sqsMessage.getReceiptHandle());
            });
        });
    }

    @Override
    protected SubscriptionDraft createSubscriptionDraft() {
        return SubscriptionFixtures.withProductChanges(sqsSubscriptionDraftBuilder(queueUrl)).build();
    }
}
