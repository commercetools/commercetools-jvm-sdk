package io.sphere.sdk.subscriptions;

import java.net.URI;

import static org.junit.Assume.assumeNotNull;

/**
 * Test fixtures for {@link Subscription} tests.
 */
public class SubscriptionFixtures {
    public final static String IRON_MQ_SUBSCRIPTION_KEY = "iron-mq-subscription-integration-test";
    /**
     * We will run an IronMQ test if this environment variable is set to an IronMQ URI.
     */
    private final static String CTP_IRON_MQ_URI_ENV = "CTP_IRON_MQ_URI";

    public static SubscriptionDraftBuilder ironMqSubscriptionDraftBuilder() {
        final String ironMqUriEnv = System.getenv(CTP_IRON_MQ_URI_ENV);
        assumeNotNull(ironMqUriEnv);

        final URI ironMqUri = URI.create(ironMqUriEnv);
        return SubscriptionDraftBuilder.of(IronMqDestination.of(ironMqUri))
                .key(IRON_MQ_SUBSCRIPTION_KEY);
    }
}
