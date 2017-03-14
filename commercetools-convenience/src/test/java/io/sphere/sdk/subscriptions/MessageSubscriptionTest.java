package io.sphere.sdk.subscriptions;

import io.sphere.sdk.categories.messages.CategoryCreatedMessage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link MessageSubscription}.
 */
public class MessageSubscriptionTest {

    @Test
    public void test() throws Exception {
        final MessageSubscription messageSubscription = MessageSubscription.of(CategoryCreatedMessage.class);

        assertThat(messageSubscription.getResourceTypeId()).isEqualTo("category");
        assertThat(messageSubscription.getTypes()).containsExactly(CategoryCreatedMessage.MESSAGE_TYPE);
    }
}
