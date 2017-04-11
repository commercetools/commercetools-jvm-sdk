package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import java.net.URI;

import static java.util.Objects.requireNonNull;

/**
 * AWS SQS is a pull-queue on AWS.
 */
@JsonDeserialize(as = SqsDestinationImpl.class)
@ResourceValue
public interface SqsDestination extends Destination {

    URI getQueueUrl();

    String getAccessKey();

    String getAccessSecret();

    String getRegion();

    static SqsDestination of(final AwsCredentials awsCredentials, final String region, final URI queueURL) {
        return new SqsDestinationImpl(awsCredentials.getAccessKey(), awsCredentials.getAccessSecret(),
                requireNonNull(queueURL), requireNonNull(region), "SQS");
    }
}
