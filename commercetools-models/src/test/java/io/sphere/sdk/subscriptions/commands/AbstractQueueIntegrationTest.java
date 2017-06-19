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


public abstract class AbstractQueueIntegrationTest extends IntegrationTest {


    @AfterClass
    public static void consumeMassages() throws Exception {
        assumeHasAzureSBEnv();
        final String connectionString = azureSBConnectionStringFromEnv();
        final QueueClient queueClient = new QueueClient(new ConnectionStringBuilder(connectionString), ReceiveMode.PeekLock);
        receiveMessages(queueClient);
        Thread.sleep(60 * 1000);
        queueClient.close();
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
        }, new MessageHandlerOptions(10, true, Duration.ofMinutes(2)));
    }
}
