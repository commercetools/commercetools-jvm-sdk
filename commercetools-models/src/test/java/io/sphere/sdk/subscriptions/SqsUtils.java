package io.sphere.sdk.subscriptions;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueResult;

import java.util.UUID;

/**
 * Util methods for working with Amazon Simple Queue Service (SQS).
 */
public class SqsUtils {
    private SqsUtils() {
    }

    /**
     * Creates a new sqs test queue.
     *
     * @param sqsClient the Simple Queue Service (SQS) client
     * @return the url of the created Simple Queue Service (SQS) queue
     */
    public static String createTestQueue(final AmazonSQS sqsClient) {
        final String queueName = "jvm-sdk-test-queue-" + UUID.randomUUID();
        final CreateQueueResult queueCreationResult = sqsClient.createQueue(queueName);

        return queueCreationResult.getQueueUrl();
    }

    /**
     * Deletes the queue and shutdown the given Simple Queue Service (SQS) client if they are not null.
     *
     * @param queueUrl  the url of the queue to delete
     * @param sqsClient the Simple Queue Service (SQS) client
     */
    public static void deleteQueueAndShutdown(final String queueUrl, final AmazonSQS sqsClient) {
        if (queueUrl != null && sqsClient != null) {
            sqsClient.deleteQueue(queueUrl);
            sqsClient.shutdown();
        }
    }
}
