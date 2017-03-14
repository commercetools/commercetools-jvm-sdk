package io.sphere.sdk.subscriptions;

import io.sphere.sdk.annotations.ResourceValue;

/**
 * AWS SNS can be used to push messages to AWS Lambda, HTTP endpoints (webhooks) or fan-out messages to SQS queues.
 */
@ResourceValue
public interface AwsSnsDestination extends Destination {

    String getTopicArn();

    String getAccessKey();

    String getAccessSecret();
}
