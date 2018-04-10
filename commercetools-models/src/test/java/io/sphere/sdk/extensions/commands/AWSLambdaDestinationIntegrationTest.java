package io.sphere.sdk.extensions.commands;

import io.sphere.sdk.extensions.AWSLambdaDestinationBuilder;
import io.sphere.sdk.extensions.Destination;
import io.sphere.sdk.subscriptions.AwsCredentials;
import io.sphere.sdk.subscriptions.SubscriptionFixtures;
import org.junit.BeforeClass;

public class AWSLambdaDestinationIntegrationTest extends AbstractExtensionIntegrationTest{

    private static String functionArn;
    private static AwsCredentials awsCredentials;

    @BeforeClass
    public static void init(){
        SubscriptionFixtures.assumeHasAwsCliEnv();
        SubscriptionFixtures.assumeHasAWSLambdaArn();
        awsCredentials = AwsCredentials.ofAwsCliEnv();
        functionArn = SubscriptionFixtures.awsLambdaArn();
    }

    @Override
    public Destination getDestination() {
        return AWSLambdaDestinationBuilder.of(functionArn, awsCredentials).build();
    }
}
