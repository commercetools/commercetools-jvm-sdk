package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.messages.Message;

/**
 * This payload will be sent for a MessageSubscription.
 *
 * @param <T> the resource type {@link MessageSubscription#getResourceType()}
 * @param <M> the message type {@link MessageSubscription#getTypes()}
 */
@JsonTypeName("Message")
@JsonDeserialize(as = MessageSubscriptionPayloadImpl.class)
@ResourceValue
public interface MessageSubscriptionPayload<T, M extends Message> extends Payload<T> {
    /**
     * The message payload will always contain the common fields:
     * <ul>
     *     <li>{@link Message#getId()}</li>
     *     <li>{@link Message#getVersion()}</li>
     *     <li>{@link Message#getSequenceNumber()}</li>
     *     <li>{@link Message#getResourceVersion()}</li>
     *     <li>{@link Message#getCreatedAt()}</li>
     *     <li>{@link Message#getLastModifiedAt()}</li>
     * </ul>
     * for any message. If the payload fits within the size limit of you message queue (the limit is often 256kb),
     * all additional fields for the specific message are included as well (along with the type field).
     * If the payload did not fit, it can be retrieved from the Messages endpoint if messages are enabled.
     *
     * @return the message payload
     */
    @JsonUnwrapped
    @JsonProperty("message")
    M getMessage();
}
