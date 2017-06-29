package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import java.net.URI;

import static java.util.Objects.requireNonNull;

/**
 * AzureServiceBus can be used as a pull-queue with Queues or to fan-out messages with Topics and Subscriptions.
 */
@JsonDeserialize(as = AzureServiceBusDestinationImpl.class)
@ResourceValue
public interface AzureServiceBusDestination extends Destination {

    /**
     * The connection string for either one of the two generated keys for the Shared Access Policy
     *
     * the strcture of this String is as follow
     *
     * "Endpoint={Endpoint obtained from azure portal};SharedAccessKey={can be also obtained fro the azure portal};EntityPath={name of the referred entity in our case its the name of the queue}";
     *
     * @return the connection uri
     */
    String getConnectionString();

    static AzureServiceBusDestination of(final String connectString) {
        return new AzureServiceBusDestinationImpl(requireNonNull(connectString), "AzureServiceBus");
    }
}
