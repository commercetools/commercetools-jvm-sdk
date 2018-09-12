package io.sphere.sdk.subscriptions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.messages.CategoryCreatedMessage;
import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.subscriptions.commands.SubscriptionCreateCommand;
import io.sphere.sdk.subscriptions.commands.SubscriptionDeleteCommand;
import org.junit.Assume;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

import static io.sphere.sdk.test.SphereTestUtils.randomInt;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

/**
 * Test fixtures for {@link Subscription} tests.
 */
public class SubscriptionFixtures {
    /**
     * We will run an IronMQ test if this environment variable is set to an IronMQ URI.
     */
    private final static String CTP_IRON_MQ_URI_ENV = "CTP_IRON_MQ_URI";

    private final static String AWS_REGION = "AWS_REGION";

    private final static String AZUREFUNCTION_URL = "AZUREFUNCTION_URL";

    private final static String AWS_FUNCTION_ARN = "AWS_FUNCTION_ARN";


    private final static String AZURE_SERVICE_BUS_CONNECTION_STRING_ENV = "AZURE_SERVICE_BUS_CONNECTION_STRING_ENV";

    public static SubscriptionDraftBuilder ironMqSubscriptionDraftBuilder() {
        final String ironMqUriFromEnv = ironMqUriFromEnv();

        final String ironMqKey = "iron-mq-test-subscription-" + randomInt();
        final URI ironMqUri = URI.create(ironMqUriFromEnv);
        return SubscriptionDraftBuilder.of(IronMqDestination.of(ironMqUri))
                .key(ironMqKey);
    }


    public static SubscriptionDraftBuilder azureServiceBusSubscriptionDraftBuilder() {
        final String connectionString = azureSBConnectionStringFromEnv();
        final String subscriptionQueue = "azure-sb-test-subscription-" + randomInt();
        return SubscriptionDraftBuilder.of(AzureServiceBusDestination.of(connectionString)).key(subscriptionQueue);
    }
    /**
     * Checks if the environment variables required for IronMQ tests are set.
     *
     * @see #CTP_IRON_MQ_URI_ENV
     * @see Assume#assumeTrue(boolean)
     */
    public static void assumeHasIronMqEnv() {
        final String ironMqUri = ironMqUriFromEnv();

        assumeNotNull(ironMqUri);
    }

    /**
     * Checks if the environment variables required for AzureServiceBus tests are set.
     *
     * @see #AZURE_SERVICE_BUS_CONNECTION_STRING_ENV
     * @see Assume#assumeTrue(boolean)
     */
    public static void assumeHasAzureSBEnv() {
        final String azureSBConnectionString = azureSBConnectionStringFromEnv();

        assumeNotNull(azureSBConnectionString);
    }

    /**
     * Check if azure url is defined
     */
    public static void assumeHasAzureFunctionUrl(){
        final String url = azureFunctionUrl();
        assumeNotNull(url);
    }


    /**
     * Check if azure url is defined
     */
    public static void assumeHasAWSLambdaArn(){
        final String arn = awsLambdaArn();
        assumeNotNull(arn);
    }

    /**
     *
     * @return The azure function used to validate the resource lifecycle (create, delete ....)
     * @see io.sphere.sdk.extensions.Extension
     */
    public static String azureFunctionUrl(){
        final String ironMqUriEnv = System.getenv(AZUREFUNCTION_URL);
        return ironMqUriEnv;
    }

    /**
     *
     * @return The aws lambda function used to validate the resource lifecycle (create, delete ....)
     * @see io.sphere.sdk.extensions.Extension
     */
    public static String awsLambdaArn(){
        final String awsLambdaArn = System.getenv(AWS_FUNCTION_ARN);
        return awsLambdaArn;
    }


    /**
     * Returns the iron mq uri to run tests against.
     *
     * @see #CTP_IRON_MQ_URI_ENV
     *
     * @return the IronMQ uri or null
     */
    public static String ironMqUriFromEnv() {
        final String ironMqUriEnv = System.getenv(CTP_IRON_MQ_URI_ENV);
        return ironMqUriEnv;
    }

    /**
     * Gets the connection string from the environment
     *
     * @return the Azure Service Bus Connection String
     */
    public static String azureSBConnectionStringFromEnv(){
        final String connectionString = System.getenv(AZURE_SERVICE_BUS_CONNECTION_STRING_ENV);
        return connectionString;
    }


    public static SubscriptionDraftBuilder sqsSubscriptionDraftBuilder(final String queueUrl) {
        final AwsCredentials awsCredentials = AwsCredentials.ofAwsCliEnv();
        final String awsRegion = System.getenv(AWS_REGION);
        assumeNotNull(awsRegion);

        final String sqsKey = "sqs-test-subscription-" + randomInt();

        return SubscriptionDraftBuilder.of(SqsDestination.of(awsCredentials, awsRegion, URI.create(queueUrl)))
                .key(sqsKey);
    }

    /**
     * Checks if the environment variable required for AWS tests are set.
     *
     * @see Assume#assumeTrue(boolean)
     * @see AwsCredentials#hasAwsCliEnv()
     */
    public static void assumeHasAwsCliEnv() {
        assumeTrue(AwsCredentials.hasAwsCliEnv());
    }

    public static SubscriptionDraftBuilder snsSubscriptionDraftBuilder(final String topicArn) {
        final AwsCredentials awsCredentials = AwsCredentials.ofAwsCliEnv();

        final String snsKey = "sns-test-subscription-" + randomInt();

        return SubscriptionDraftBuilder.of(SnsDestination.of(awsCredentials, topicArn))
                .key(snsKey);
    }

    public static SubscriptionDraftBuilder createPubSubSubscription( final String projectKey ) {
        final String topic = "projects/ctp-playground/topics/sdk-test";
        return SubscriptionDraftBuilder.of(PubSubDestination.of(projectKey, topic));
    }


    public static SubscriptionDraftBuilder withCategoryChanges(final SubscriptionDraftBuilder subscriptionDraftBuilder) {
        return subscriptionDraftBuilder.changes(Collections.singletonList(ChangeSubscription.of(Category.resourceTypeId())));
    }

    public static SubscriptionDraftBuilder withProductChanges(final SubscriptionDraftBuilder subscriptionDraftBuilder) {
        return subscriptionDraftBuilder.changes(Collections.singletonList(ChangeSubscription.of(Product.resourceTypeId())));
    }

    public static SubscriptionDraftBuilder withCategoryCreatedMessage(final SubscriptionDraftBuilder subscriptionDraftBuilder) {
        return subscriptionDraftBuilder.messages(Collections.singletonList(MessageSubscription.of(Category.resourceTypeId(),
                Arrays.asList(CategoryCreatedMessage.MESSAGE_TYPE))));
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

    public static void deleteSubscription(final BlockingSphereClient client, final Subscription subscription) {
        if (subscription != null) {
            client.executeBlocking(SubscriptionDeleteCommand.of(subscription));
        }
    }
}
