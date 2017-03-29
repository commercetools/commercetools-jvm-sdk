package io.sphere.sdk.subscriptions.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.subscriptions.*;
import io.sphere.sdk.subscriptions.queries.SubscriptionQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.Test;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeNotNull;

/**
 * Integration tests for {@link SubscriptionCreateCommand}.
 */
public class SubscriptionCreateCommandIntegrationTest extends IntegrationTest {
    private final static String SUBSCRIPTION_KEY = "subscription-integration-test";

    /**
     * We will run an ironMQ test if this environment variable is set.
     */
    private final static String CTP_IRON_MQ_URI_ENV = "CTP_IRON_MQ_URI";

    @AfterClass
    public static void clean() {
        List<Subscription> results = client().executeBlocking(SubscriptionQuery.of()
                .withPredicates(l -> l.key().is(SUBSCRIPTION_KEY)))
                .getResults();
        results.forEach(subscription -> client().executeBlocking(SubscriptionDeleteCommand.of(subscription)));
    }

    @Test
    public void createIronMQ() throws Exception {
        final String ironMqUri = System.getenv(CTP_IRON_MQ_URI_ENV);
        assumeNotNull(ironMqUri);

        final IronMqDestination ironMqDestination = IronMqDestination.of(URI.create(ironMqUri));
        final List<ChangeSubscription> changeSubscriptions = Collections.singletonList(ChangeSubscription.of(Category.class));

        final SubscriptionDraftDsl subscriptionDraft = SubscriptionDraftBuilder.of()
                .key(SUBSCRIPTION_KEY)
                .destination(ironMqDestination)
                .changes(changeSubscriptions).build();

        final SubscriptionCreateCommand createCommand = SubscriptionCreateCommand.of(subscriptionDraft);
        final Subscription subscription = client().executeBlocking(createCommand);

        assertThat(subscription).isNotNull();

        final SubscriptionDeleteCommand deleteCommand = SubscriptionDeleteCommand.of(subscription);
        client().executeBlocking(deleteCommand);
    }
}
