package io.sphere.sdk.subscriptions;

import io.sphere.sdk.annotations.ResourceValue;

/**
 * AWS SQS is a pull-queue on AWS.
 */
@ResourceValue
public interface AwsSqsDestination extends Destination {

    String getQueueURL();

    String getAccessKey();

    String getAccessSecret();

    String getRegion();
}
