package io.sphere.sdk.subscriptions.commands;

import io.sphere.sdk.subscriptions.Subscription;
import io.sphere.sdk.subscriptions.SubscriptionDraftBuilder;
import io.sphere.sdk.subscriptions.SubscriptionFixtures;
import io.sphere.sdk.subscriptions.SubscriptionIntegrationTest;
import io.sphere.sdk.subscriptions.queries.SubscriptionQuery;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.function.Supplier;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link SubscriptionDeleteCommand}.
 */
public class SubscriptionDeleteCommandIntegrationTest extends SubscriptionIntegrationTest {

    @Ignore
    @Test
    public void deleteByKeyAzureQueue() throws Exception {
        assumeHasAzureSBEnv();
        deleteByKey(SubscriptionFixtures::azureServiceBusSubscriptionDraftBuilder);
    }

    @Ignore
    @Test
    public void deleteByIdAzureQueue() throws Exception {
        assumeHasAzureSBEnv();
        deleteByID(SubscriptionFixtures::azureServiceBusSubscriptionDraftBuilder);
    }

    @Ignore
    @Test
    public void deleteByKeyIronMq() throws Exception {
        assumeHasIronMqEnv();
        deleteByKey(SubscriptionFixtures::ironMqSubscriptionDraftBuilder);
    }

    @Ignore
    @Test
    public void deleteByIdIronMq() throws Exception {
        assumeHasIronMqEnv();
        deleteByID(SubscriptionFixtures::ironMqSubscriptionDraftBuilder);

    }

    public void deleteByKey(Supplier<SubscriptionDraftBuilder> subscriptionDraftBuilderSupplier) throws Exception {
        final Subscription subscription = createSubscription(client(), withCategoryChanges(subscriptionDraftBuilderSupplier.get()));

        final SubscriptionDeleteCommand subscriptionDeleteCommand = SubscriptionDeleteCommand.ofKey(subscription.getKey(), subscription.getVersion());

        client().executeBlocking(subscriptionDeleteCommand);

        assertEventually(() -> {
            final SubscriptionQuery subscriptionQuery = SubscriptionQuery.of().withPredicates(m -> m.is(subscription));
            assertThat(client().executeBlocking(subscriptionQuery).head()).isEmpty();
        });
    }

    public void deleteByID(Supplier<SubscriptionDraftBuilder> subscriptionDraftBuilderSupplier) throws Exception {
        final Subscription subscription = createSubscription(client(), withCategoryChanges(subscriptionDraftBuilderSupplier.get()));

        final SubscriptionDeleteCommand subscriptionDeleteCommand = SubscriptionDeleteCommand.of(subscription);

        client().executeBlocking(subscriptionDeleteCommand);

        assertEventually(() -> {
            final SubscriptionQuery subscriptionQuery = SubscriptionQuery.of().withPredicates(m -> m.is(subscription));
            assertThat(client().executeBlocking(subscriptionQuery).head()).isEmpty();
        });
    }

    @Ignore
    @AfterClass
    public static void cleanUPQueues() throws Exception{
        AzureSBUtils.consumeMessages();
    }

}
