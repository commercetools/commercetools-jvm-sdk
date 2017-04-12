package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.messages.CategoryCreatedMessage;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.messages.Message;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MessageSubscriptionPayload}.
 */
public class MessageSubscriptionPayloadTest {

    @Test
    public void deserialize() throws Exception {
        final TypeReference<MessageSubscriptionPayload<Category>> typeReference =
                new TypeReference<MessageSubscriptionPayload<Category>>() {};

        final MessageSubscriptionPayload<Category> messageSubscriptionPayload =
                SphereJsonUtils.readObjectFromResource("MessageSubscriptionPayload.json", typeReference);

        assertThat(messageSubscriptionPayload).isNotNull();

        final Message message = messageSubscriptionPayload.getMessage();
        assertThat(message).isNotNull();

        final CategoryCreatedMessage categoryCreatedMessage = message.as(CategoryCreatedMessage.class);
        assertThat(categoryCreatedMessage).isNotNull();

        final Category category = categoryCreatedMessage.getCategory();
        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo("test-category-id");
    }
}
