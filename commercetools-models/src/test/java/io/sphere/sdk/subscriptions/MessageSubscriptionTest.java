package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.messages.CategoryCreatedMessage;
import io.sphere.sdk.categories.messages.CategorySlugChangedMessage;
import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MessageSubscription}
 */
public class MessageSubscriptionTest {

    @Test
    public void ofMessageTypes() throws Exception {
        final MessageSubscription categoryMessageSubscription =
                MessageSubscription.of(Category.class, CategoryCreatedMessage.class, CategorySlugChangedMessage.class);

        assertThat(categoryMessageSubscription.getResourceTypeId()).isEqualTo(Category.referenceTypeId());
        assertThat(categoryMessageSubscription.getTypes())
                .containsExactly(CategoryCreatedMessage.MESSAGE_TYPE, CategorySlugChangedMessage.MESSAGE_TYPE);
    }

    @Test
    public void ofResourceType() throws Exception {
        final MessageSubscription categoryMessageSubscription =
                MessageSubscription.of(Category.class);
        assertThat(categoryMessageSubscription.getResourceTypeId()).isEqualTo(Category.referenceTypeId());
        assertThat(categoryMessageSubscription.getTypes()).isEmpty();
    }

    @Test
    public void addType() throws Exception {
        final MessageSubscription categoryMessageSubscription =
                MessageSubscription.of(Category.class, CategoryCreatedMessage.class);

        final MessageSubscription addType = categoryMessageSubscription.addType(CategorySlugChangedMessage.class);
        assertThat(addType.getResourceTypeId()).isEqualTo(Category.referenceTypeId());
        assertThat(addType.getTypes())
                .containsExactly(CategoryCreatedMessage.MESSAGE_TYPE, CategorySlugChangedMessage.MESSAGE_TYPE);
    }

    @Test
    public void removeType() throws Exception {
        final MessageSubscription categoryMessageSubscription =
                MessageSubscription.of(Category.class, CategoryCreatedMessage.class, CategorySlugChangedMessage.class);

        final MessageSubscription removeType = categoryMessageSubscription.removeType(CategorySlugChangedMessage.class);
        assertThat(removeType.getResourceTypeId()).isEqualTo(Category.referenceTypeId());
        assertThat(removeType.getTypes())
                .containsExactly(CategoryCreatedMessage.MESSAGE_TYPE);
    }

    @Test
    public void serialize() throws Exception {
        final MessageSubscription categoryMessageSubscription =
                MessageSubscription.of(Category.class, CategoryCreatedMessage.class);
        final JsonNode jsonNode = SphereJsonUtils.toJsonNode(categoryMessageSubscription);

        assertThat(jsonNode.get("resourceTypeId").asText()).isEqualTo(Category.referenceTypeId());
        assertThat(jsonNode.get("types").get(0).asText()).isEqualTo(CategoryCreatedMessage.MESSAGE_TYPE);
    }


    @Test
    public void deserialize() throws Exception {
        MessageSubscription messageSubscription = SphereJsonUtils.readObject("{\"resourceTypeId\":\"category\"}", MessageSubscription.class);
        assertThat(messageSubscription.getResourceTypeId()).isEqualTo(Category.resourceTypeId());
    }
}
