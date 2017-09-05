package io.sphere.sdk.subscriptions;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This test suite is needed in addition to the {@link SubscriptionMessagingIntegrationTest} marker interface.
 */
@RunWith(Categories.class)
@Categories.IncludeCategory(SubscriptionMessagingIntegrationTest.class)
@Suite.SuiteClasses({ ChangeSubscriptionSqsIntegrationTest.class, MessageSubscriptionSqsIntegrationTest.class })
public class SubscriptionMessagingIntegrationTestSuite {
}
