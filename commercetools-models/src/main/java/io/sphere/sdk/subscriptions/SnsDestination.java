package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import static java.util.Objects.requireNonNull;

/**
 * AWS SNS can be used to push messages to AWS Lambda, HTTP endpoints (webhooks) or fan-out messages to SQS queues.
 */
@JsonTypeName("SNS")
@JsonDeserialize(as = SnsDestinationImpl.class)
@ResourceValue
public interface SnsDestination extends Destination {

    String getTopicArn();

    String getAccessKey();

    String getAccessSecret();

    static SnsDestination of(final AwsCredentials awsCredentials, final String topicArn) {
        return new SnsDestinationImpl(awsCredentials.getAccessKey(), awsCredentials.getAccessSecret(),
                requireNonNull(topicArn), "SNS");
    }
}
