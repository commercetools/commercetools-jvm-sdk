package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import java.net.URI;

import static java.util.Objects.requireNonNull;

/**
 * AWS SQS is a pull-queue on AWS.
 */
@JsonTypeName("SQS")
@JsonDeserialize(as = AwsSqsDestinationImpl.class)
@ResourceValue
public interface AwsSqsDestination extends Destination {

    URI getQueueURL();

    String getAccessKey();

    String getAccessSecret();

    String getRegion();

    static AwsSqsDestination of(final AwsCredentials awsCredentials, final String region, final URI queueURL) {
        return new AwsSqsDestinationImpl(awsCredentials.getAccessKey(), awsCredentials.getAccessSecret(),
                requireNonNull(queueURL), requireNonNull(region), "SQS");
    }
}
