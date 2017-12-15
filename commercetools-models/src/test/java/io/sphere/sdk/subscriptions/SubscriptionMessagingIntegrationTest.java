package io.sphere.sdk.subscriptions;

/**
 * This interface marks tests as being integration tests for subscriptions which depend on the actual sending
 * of external messages. This then allows us to use the {@link org.junit.experimental.categories.Category}
 * JUnit annotation to disable these tests on certain environments.
 */
public interface SubscriptionMessagingIntegrationTest {
}
