package io.sphere.sdk.subscriptions.commands;

import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.subscriptions.ChangeSubscription;
import io.sphere.sdk.subscriptions.MessageSubscription;
import io.sphere.sdk.subscriptions.Subscription;
import io.sphere.sdk.subscriptions.SubscriptionFixtures;
import io.sphere.sdk.subscriptions.commands.updateactions.SetChanges;
import io.sphere.sdk.subscriptions.commands.updateactions.SetKey;
import io.sphere.sdk.subscriptions.commands.updateactions.SetMessages;
import io.sphere.sdk.subscriptions.queries.SubscriptionQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SubscriptionUpdateCommand}.
 */
public class SubscriptionUpdateCommandIntegrationTest extends IntegrationTest {

    @BeforeClass
    public static void clean() {
        List<Subscription> results = client().executeBlocking(SubscriptionQuery.of()
                .withPredicates(l -> l.key().is(SubscriptionFixtures.IRON_MQ_SUBSCRIPTION_KEY)))
                .getResults();
        results.forEach(subscription -> client().executeBlocking(SubscriptionDeleteCommand.of(subscription)));
    }

    @Test
    public void setKeyIronMq() {
        withSubscription(client(), withCategoryChanges(ironMqSubscriptionDraftBuilder()), subscription -> {
            final String newKey = randomKey();
            final SubscriptionUpdateCommand setKeyCommand = SubscriptionUpdateCommand.of(subscription, SetKey.of(newKey));
            final Subscription updatedSubscription = client().executeBlocking(setKeyCommand);

            assertThat(updatedSubscription.getKey()).isEqualTo(newKey);

            return updatedSubscription;
        });
    }

    @Test
    public void setChangesIronMq() {
        withSubscription(client(), withCategoryChanges(ironMqSubscriptionDraftBuilder()), subscription -> {
            final List<ChangeSubscription> newChanges = Collections.singletonList(ChangeSubscription.of(Payment.class));

            final SubscriptionUpdateCommand setChangesCommand = SubscriptionUpdateCommand.of(subscription, SetChanges.of(newChanges));
            final Subscription updatedSubscription = client().executeBlocking(setChangesCommand);

            assertThat(updatedSubscription.getChanges()).isEqualTo(newChanges);

            return updatedSubscription;
        });
    }

    @Test
    public void setMessagesIronMq() {
        withSubscription(client(), withCategoryCreatedMessage(ironMqSubscriptionDraftBuilder()), subscription -> {
            final List<MessageSubscription> newMessages = Collections.singletonList(MessageSubscription.of(Payment.class));

            final SubscriptionUpdateCommand setMessagesCommand = SubscriptionUpdateCommand.of(subscription, SetMessages.of(newMessages));
            final Subscription updatedSubscription = client().executeBlocking(setMessagesCommand);

            assertThat(updatedSubscription.getMessages()).isEqualTo(newMessages);

            return updatedSubscription;
        });
    }
}
