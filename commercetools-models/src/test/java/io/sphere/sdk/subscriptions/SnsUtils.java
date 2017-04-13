package io.sphere.sdk.subscriptions;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreateTopicResult;

import static io.sphere.sdk.test.SphereTestUtils.randomInt;

/**
 * Util methods for working with aws sns.
 */
public class SnsUtils {
    private SnsUtils() {
    }

    /**
     * Creates a new sns test topic.
     *
     * @param snsClient the sqs client
     * @return the arn of the created sns topic
     */
    public static String createTestTopic(final AmazonSNS snsClient) {
        final String queueName = "jvm-sdk-test-topic-" + randomInt();
        final CreateTopicResult queueCreationResult = snsClient.createTopic(queueName);

        return queueCreationResult.getTopicArn();
    }

    /**
     * Deletes the given topic and shuts down the given sns client if they are not null.
     *
     * @param topicArn  the arn of the topic to delete
     * @param snsClient the sns client
     */
    public static void deleteTopicAndShutdown(final String topicArn, final AmazonSNS snsClient) {
        if (topicArn != null && snsClient != null) {
            snsClient.deleteTopic(topicArn);
            snsClient.shutdown();
        }
    }
}
