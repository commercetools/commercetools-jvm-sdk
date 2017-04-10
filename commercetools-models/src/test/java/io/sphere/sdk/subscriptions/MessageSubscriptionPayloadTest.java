package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.messages.CategoryCreatedMessage;
import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MessageSubscriptionPayload}.
 */
public class MessageSubscriptionPayloadTest {

    @Test
    public void deserialize() throws Exception {
        final TypeReference<MessageSubscriptionPayload<Category, CategoryCreatedMessage>> typeReference =
                new TypeReference<MessageSubscriptionPayload<Category, CategoryCreatedMessage>>() {};

        final MessageSubscriptionPayload<Category, CategoryCreatedMessage> messageSubscriptionPayload =
                SphereJsonUtils.readObjectFromResource("MessageSubscriptionPayload.json", typeReference);

        assertThat(messageSubscriptionPayload).isNotNull();

        final CategoryCreatedMessage message = messageSubscriptionPayload.getMessage();
        assertThat(message).isNotNull();

        final Category category = message.getCategory();
        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo("test-category-id");
    }
}
