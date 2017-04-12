package io.sphere.sdk.subscriptions;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueResult;

import static io.sphere.sdk.test.SphereTestUtils.randomInt;

/**
 * Util methods for working with aws sqs.
 */
public class SqsUtils {
    private SqsUtils() {
    }

    /**
     * Creates a new sqs test queue.
     *
     * @param sqsClient the sqs client
     * @return the url of the created sqs queue
     */
    public static String createTestQueue(final AmazonSQS sqsClient) {
        final String queueName = "jvm-sdk-test-queue-" + randomInt();
        final CreateQueueResult queueCreationResult = sqsClient.createQueue(queueName);

        return queueCreationResult.getQueueUrl();
    }

    /**
     * Deletes the queue and shutdown the given sqs client.
     *
     * @param queueUrl  the url of the queue to delete
     * @param sqsClient the sqs client
     */
    public static void deleteQueueAndShutdown(final String queueUrl, final AmazonSQS sqsClient) {
        sqsClient.deleteQueue(queueUrl);
        sqsClient.shutdown();
    }
}
