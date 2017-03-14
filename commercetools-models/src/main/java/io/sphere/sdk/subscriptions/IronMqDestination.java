package io.sphere.sdk.subscriptions;

import io.sphere.sdk.annotations.ResourceValue;

/**
 *  IronMQ can be used as a pull-queue, but it can also be used to push messages to IronWorkers or HTTP endpoints (webhooks) or fan-out messages to other IronMQ queues.
 */
@ResourceValue
public interface IronMqDestination extends Destination {

    String getUri();
}
