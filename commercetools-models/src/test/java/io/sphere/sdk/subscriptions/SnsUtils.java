package io.sphere.sdk.subscriptions;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicResult;

import java.util.UUID;

/**
 * Util methods for working with Amazon Simple Notification Service (Amazon SNS).
 */
public class SnsUtils {
    private SnsUtils() {
    }

    /**
     * Creates a new sns test topic.
     *
     * @param snsClient the Simple Notification Service (SNS) client
     * @return the Amazon Resource Name (ARN) of the created sns topic
     */
    public static String createTestTopic(final AmazonSNS snsClient) {
        final String queueName = "jvm-sdk-test-topic-" + UUID.randomUUID();
        final CreateTopicResult queueCreationResult = snsClient.createTopic(queueName);

        return queueCreationResult.getTopicArn();
    }

    /**
     * Deletes the given topic and shuts down the given sns client if they are not null.
     *
     * @param topicArn  the the Amazon Resource Name (ARN) of the topic to delete
     * @param snsClient the Simple Notification Service (SNS) client to shutdown
     */
    public static void deleteTopicAndShutdown(final String topicArn, final AmazonSNS snsClient) {
        if (topicArn != null && snsClient != null) {
            snsClient.deleteTopic(topicArn);
            snsClient.shutdown();
        }
    }
}
