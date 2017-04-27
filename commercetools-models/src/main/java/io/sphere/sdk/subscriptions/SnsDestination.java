package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import static java.util.Objects.requireNonNull;

/**
 * Amazon Simple Notification Service (Amazon SNS) can be used to push messages to AWS Lambda, HTTP endpoints (webhooks)
 * or fan-out messages to Amazon Simple Queue Service (SQS).
 */
@JsonDeserialize(as = SnsDestinationImpl.class)
@ResourceValue
public interface SnsDestination extends Destination {

    /**
     * The Amazon Resource Name (ARN) of the Simple Notification Service (SNS) topic name.
     * Amazon Resource Names (ARNs) uniquely identify AWS resources.
     *
     * @return the Amazon Resource Name (ARN) of the destination topic
     */
    String getTopicArn();

    String getAccessKey();

    String getAccessSecret();

    /**
     * Creates a new sns destination with the given credentials and topic.
     *
     * @param awsCredentials the AWS credentials
     * @param topicArn       the ARN (Amazon Resource Name) of the SNS (Simple Notification Service) destination topic
     * @return the new SNS destination
     */
    static SnsDestination of(final AwsCredentials awsCredentials, final String topicArn) {
        return new SnsDestinationImpl(awsCredentials.getAccessKey(), awsCredentials.getAccessSecret(),
                requireNonNull(topicArn), "SNS");
    }
}
