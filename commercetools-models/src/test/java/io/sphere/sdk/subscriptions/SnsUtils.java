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
     * @return the url of the created sqs queue
     */
    public static String createTestTopic(final AmazonSNS snsClient) {
        final String queueName = "jvm-sdk-test-topic-" + randomInt();
        final CreateTopicResult queueCreationResult = snsClient.createTopic(queueName);

        return queueCreationResult.getTopicArn();
    }

    /**
     * Deletes the topic and shutdown the given sns client.
     *
     * @param topicArn  the url of the queue to delete
     * @param snsClient the sqs client
     */
    public static void deleteTopicAndShutdown(final String topicArn, final AmazonSNS snsClient) {
        snsClient.deleteTopic(topicArn);
        snsClient.shutdown();
    }
}
