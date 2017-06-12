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
     * @return the connection uri
     */
    URI getConnectionString();

    static AzureServiceBusDestination of(final URI uri) {
        return new AzureServiceBusDestinationImpl(requireNonNull(uri), "AzureServiceBus");
    }
}
