package io.sphere.sdk.subscriptions.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.messages.CategoryCreatedMessage;
import io.sphere.sdk.subscriptions.*;
import io.sphere.sdk.subscriptions.queries.SubscriptionQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SubscriptionCreateCommand}.
 */
public class SubscriptionCreateCommandIntegrationTest extends IntegrationTest {

    @After
    public void clean() {
        List<Subscription> results = client().executeBlocking(SubscriptionQuery.of()
                .withPredicates(l -> l.key().is(SubscriptionFixtures.IRON_MQ_SUBSCRIPTION_KEY)))
                .getResults();
        results.forEach(subscription -> client().executeBlocking(SubscriptionDeleteCommand.of(subscription)));
    }

    @Test
    public void createIronMqChangesSubscription() throws Exception {
        final SubscriptionDraftDsl subscriptionDraft = SubscriptionFixtures.ironMqSubscriptionDraftBuilder()
                .changes(Collections.singletonList(ChangeSubscription.of(Category.class)))
                .build();

        final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
        final Subscription subscription = client().executeBlocking(createCommand);

        assertThat(subscription).isNotNull();
        assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
        assertThat(subscription.getChanges()).isEqualTo(subscriptionDraft.getChanges());
    }

    @Test
    public void createIronMqMessagesSubscription() throws Exception {
        final SubscriptionDraftDsl subscriptionDraft = SubscriptionFixtures.ironMqSubscriptionDraftBuilder()
                .messages(Collections.singletonList(MessageSubscription.of(Category.class, CategoryCreatedMessage.class)))
                .build();

        final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
        final Subscription subscription = client().executeBlocking(createCommand);

        assertThat(subscription).isNotNull();
        assertThat(subscription.getDestination()).isEqualTo(subscriptionDraft.getDestination());
        assertThat(subscription.getMessages()).isEqualTo(subscriptionDraft.getMessages());
    }
}
