package io.sphere.sdk.subscriptions;

import io.sphere.sdk.annotations.ResourceValue;

import java.net.URI;
import java.util.Objects;

/**
 *  IronMQ can be used as a pull-queue, but it can also be used to push messages to IronWorkers or HTTP endpoints (webhooks) or fan-out messages to other IronMQ queues.
 */
@ResourceValue
public interface IronMqDestination extends Destination {

    /**
     * The webhook uri of your IronMQ.
     *
     * @return the webhook uri
     */
    URI getUri();

    static IronMqDestination of(final URI uri) {
        return new IronMqDestinationImpl("IronMQ", Objects.requireNonNull(uri));
    }
}
