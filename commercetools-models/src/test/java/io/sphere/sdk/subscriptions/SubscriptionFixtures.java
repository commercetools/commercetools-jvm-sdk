package io.sphere.sdk.subscriptions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.messages.CategoryCreatedMessage;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.subscriptions.commands.SubscriptionCreateCommand;
import io.sphere.sdk.subscriptions.commands.SubscriptionDeleteCommand;

import java.net.URI;
import java.util.Collections;
import java.util.function.Function;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

/**
 * Test fixtures for {@link Subscription} tests.
 */
public class SubscriptionFixtures {
    public final static String IRON_MQ_SUBSCRIPTION_KEY = "iron-mq-subscription-integration-test";
    public final static String AWS_SQS_SUBSCRIPTION_KEY = "aws-sqs-subscription-integration-test";

    /**
     * We will run an IronMQ test if this environment variable is set to an IronMQ URI.
     */
    private final static String CTP_IRON_MQ_URI_ENV = "CTP_IRON_MQ_URI";

    private final static String CTP_AWS_REGION = "AWS_REGION";

    private final static String CTP_AWS_SQS_QUEUE_URL = "CTP_AWS_SQS_QUEUE_URL";

    private final static String CTP_AWS_SNS_TOPIC_ARN = "CTP_AWS_SNS_TOPIC_ARN";

    public static SubscriptionDraftBuilder ironMqSubscriptionDraftBuilder() {
        final String ironMqUriEnv = System.getenv(CTP_IRON_MQ_URI_ENV);
        assumeNotNull(ironMqUriEnv);

        final URI ironMqUri = URI.create(ironMqUriEnv);
        return SubscriptionDraftBuilder.of(IronMqDestination.of(ironMqUri))
                .key(IRON_MQ_SUBSCRIPTION_KEY);
    }

    public static SubscriptionDraftBuilder sqsSubscriptionDraftBuilder() {
        final String queueUrl = System.getenv(CTP_AWS_SQS_QUEUE_URL);
        return sqsSubscriptionDraftBuilder(queueUrl);
    }

    public static SubscriptionDraftBuilder sqsSubscriptionDraftBuilder(final String queueUrl) {
        assumeTrue(AwsCredentials.hasAwsCliEnv());
        final AwsCredentials awsCredentials = AwsCredentials.ofAwsCliEnv();
        final String awsRegion = System.getenv(CTP_AWS_REGION) == null ?
                "us-west-2" :
                System.getenv(CTP_AWS_REGION);
        assumeNotNull(awsRegion);

        return SubscriptionDraftBuilder.of(SqsDestination.of(awsCredentials, awsRegion, URI.create(queueUrl)))
                .key(AWS_SQS_SUBSCRIPTION_KEY);
    }

    public static SubscriptionDraftBuilder snsSubscriptionDraftBuilder() {
        assumeTrue(AwsCredentials.hasAwsCliEnv());
        final AwsCredentials awsCredentials = AwsCredentials.ofAwsCliEnv();
        final String snsTopicArn = System.getenv(CTP_AWS_SNS_TOPIC_ARN);
        assumeNotNull(snsTopicArn);

        return SubscriptionDraftBuilder.of(SnsDestination.of(awsCredentials, snsTopicArn))
                .key(AWS_SQS_SUBSCRIPTION_KEY);
    }

    public static SubscriptionDraftBuilder withCategoryChanges(final SubscriptionDraftBuilder subscriptionDraftBuilder) {
        return subscriptionDraftBuilder.changes(Collections.singletonList(ChangeSubscription.of(Category.resourceTypeId())));
    }

    public static SubscriptionDraftBuilder withProductChanges(final SubscriptionDraftBuilder subscriptionDraftBuilder) {
        return subscriptionDraftBuilder.changes(Collections.singletonList(ChangeSubscription.of(Product.resourceTypeId())));
    }

    public static SubscriptionDraftBuilder withCategoryCreatedMessage(final SubscriptionDraftBuilder subscriptionDraftBuilder) {
        return subscriptionDraftBuilder.messages(Collections.singletonList(MessageSubscription.of(Category.resourceTypeId(), CategoryCreatedMessage.MESSAGE_TYPE)));
    }

    public static void withSubscription(final BlockingSphereClient client, final SubscriptionDraftBuilder builder, final Function<Subscription, Subscription> f) {
        final SubscriptionDraftDsl subscriptionDraft = builder.build();
        final Subscription subscription = client.executeBlocking(SubscriptionCreateCommand.of(subscriptionDraft));
        final Subscription possiblyUpdatedSubscription = f.apply(subscription);
        client.executeBlocking(SubscriptionDeleteCommand.of(possiblyUpdatedSubscription));
    }

    public static Subscription createSubscription(final BlockingSphereClient client, final SubscriptionDraftBuilder builder) {
        final SubscriptionDraftDsl subscriptionDraftDsl = builder.build();
        return client.executeBlocking(SubscriptionCreateCommand.of(subscriptionDraftDsl));
    }
}
