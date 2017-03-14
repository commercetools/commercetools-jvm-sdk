package io.sphere.sdk.subscriptions;

import io.sphere.sdk.annotations.ResourceValue;

import static java.util.Objects.requireNonNull;

/**
 * AWS SNS can be used to push messages to AWS Lambda, HTTP endpoints (webhooks) or fan-out messages to SQS queues.
 */
@ResourceValue
public interface AwsSnsDestination extends Destination {

    String getTopicArn();

    String getAccessKey();

    String getAccessSecret();

    static AwsSnsDestination of(final AwsCredentials awsCredentials, final String topicArn) {
        return new AwsSnsDestinationImpl(awsCredentials.getAccessKey(), awsCredentials.getAccessSecret(),
                requireNonNull(topicArn), "SNS");
    }
}
