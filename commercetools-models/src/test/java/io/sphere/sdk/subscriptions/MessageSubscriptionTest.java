package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.messages.CategoryCreatedMessage;
import io.sphere.sdk.categories.messages.CategorySlugChangedMessage;
import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MessageSubscription}
 */
public class MessageSubscriptionTest {

    @Test
    public void ofMessageTypes() throws Exception {
        final MessageSubscription categoryMessageSubscription =
                MessageSubscription.of(Category.resourceTypeId(),
                        Arrays.asList(CategoryCreatedMessage.MESSAGE_TYPE, CategorySlugChangedMessage.MESSAGE_TYPE));

        assertThat(categoryMessageSubscription.getResourceTypeId()).isEqualTo(Category.referenceTypeId());
        assertThat(categoryMessageSubscription.getTypes())
                .containsExactly(CategoryCreatedMessage.MESSAGE_TYPE, CategorySlugChangedMessage.MESSAGE_TYPE);
    }

    @Test
    public void ofResourceType() throws Exception {
        final MessageSubscription categoryMessageSubscription =
                MessageSubscription.of(Category.resourceTypeId());
        assertThat(categoryMessageSubscription.getResourceTypeId()).isEqualTo(Category.referenceTypeId());
        assertThat(categoryMessageSubscription.getTypes()).isNull();
    }

    @Test
    public void serialize() throws Exception {
        final MessageSubscription categoryMessageSubscription =
                MessageSubscription.of(Category.resourceTypeId(), Arrays.asList(CategoryCreatedMessage.MESSAGE_TYPE));
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(categoryMessageSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Category.referenceTypeId());
        assertThat(jsonNode.get("types").get(0).asText()).isEqualTo(CategoryCreatedMessage.MESSAGE_TYPE);
    }

    @Test
    public void deserialize() throws Exception {
        final MessageSubscription messageSubscription =
                SphereJsonUtils.readObject("{\"resourceTypeId\":\"category\"}", MessageSubscription.class);

        assertThat(messageSubscription.getResourceTypeId()).isEqualTo(Category.resourceTypeId());
    }
}
