package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * A destination contains all info necessary for the commercetools platform to deliver a message onto your Message Queue.
 * Message Queues can be differentiated by the sub types of this interface.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = IronMqDestinationImpl.class, name = "IronMQ"),
        @JsonSubTypes.Type(value = AwsSqsDestinationImpl.class, name = "SQS"),
        @JsonSubTypes.Type(value = AwsSnsDestinationImpl.class, name = "SNS")
})
public interface Destination {
    String getType();
}
