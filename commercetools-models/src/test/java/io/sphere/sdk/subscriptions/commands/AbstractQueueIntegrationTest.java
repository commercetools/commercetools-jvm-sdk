package io.sphere.sdk.subscriptions.commands;

import com.microsoft.azure.servicebus.*;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static io.sphere.sdk.subscriptions.SubscriptionFixtures.*;
import static org.junit.Assume.assumeNotNull;


public abstract class AbstractQueueIntegrationTest extends IntegrationTest {


    public static String AZURE_CONSUME_MESSAGES_TIMEOUT_IN_SECONDS = "AZURE_CONSUME_MESSAGES_TIMEOUT_IN_SECONDS";

    @AfterClass
    public static void consumeMessages() throws Exception {
        assumeHasAzureSBEnv();
        final String connectionString = azureSBConnectionStringFromEnv();
        final QueueClient queueClient = new QueueClient(new ConnectionStringBuilder(connectionString), ReceiveMode.PeekLock);
        receiveMessages(queueClient);
        Thread.sleep(getAzureTimeoutFromEnvInMillis());
        queueClient.close();
    }

    private static long getAzureTimeoutFromEnvInMillis(){
        String azureTimeout = System.getenv(AZURE_CONSUME_MESSAGES_TIMEOUT_IN_SECONDS);
        assumeNotNull(azureTimeout);
        return Long.parseLong(azureTimeout)*1000;
    }

    private static void receiveMessages(QueueClient queueClient) throws InterruptedException, ServiceBusException {

        queueClient.registerMessageHandler(new IMessageHandler() {

            public CompletableFuture<Void> onMessageAsync(IMessage message) {
                System.out.println(new String(message.getBody()));
                return CompletableFuture.completedFuture(null);
            }

            public void notifyException(Throwable exception, ExceptionPhase phase) {
                System.out.println(phase + " encountered exception:" + exception.getMessage());
            }
        }, new MessageHandlerOptions(10000, true, Duration.ofMinutes(2)));
    }
}
