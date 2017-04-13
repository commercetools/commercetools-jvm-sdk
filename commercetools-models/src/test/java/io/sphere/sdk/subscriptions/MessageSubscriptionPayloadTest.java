package io.sphere.sdk.subscriptions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.messages.CategoryCreatedMessage;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.messages.Message;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link MessageSubscriptionPayload}.
 */
public class MessageSubscriptionPayloadTest {

    @Test
    public void deserialize() throws Exception {
        final MessageSubscriptionPayload<Category> messageSubscriptionPayload =
                SphereJsonUtils.readObjectFromResource("MessageSubscriptionPayload.json", MessageSubscriptionPayload.class);

        assertThat(messageSubscriptionPayload).isNotNull();

        final Message message = messageSubscriptionPayload.getMessage();
        assertThat(message).isNotNull();

        final CategoryCreatedMessage categoryCreatedMessage = message.as(CategoryCreatedMessage.class);
        assertThat(categoryCreatedMessage).isNotNull();

        final Category category = categoryCreatedMessage.getCategory();
        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo("test-category-id");
    }

    @Test
    public void deserializeIncompleteMessage() throws Exception {
        final MessageSubscriptionPayload<Category> messageSubscriptionPayload =
                SphereJsonUtils.readObjectFromResource("MessageSubscriptionIncompletePayload.json", MessageSubscriptionPayload.class);

        assertThat(messageSubscriptionPayload).isNotNull();
        assertThat(messageSubscriptionPayload.hasCompleteMessage()).isFalse();
        final Message message = messageSubscriptionPayload.getMessage();
        assertThat(message)
                .describedAs("Partial message is available").isNotNull();
        assertThat(message.getType()).isNull();

        assertThatThrownBy(() -> messageSubscriptionPayload.as(CategoryCreatedMessage.class)).isInstanceOf(IllegalStateException.class);
    }
}
