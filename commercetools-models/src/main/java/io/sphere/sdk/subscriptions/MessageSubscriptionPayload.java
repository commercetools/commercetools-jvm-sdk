package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.Message;

import javax.annotation.Nullable;

/**
 * This payload will be sent for a MessageSubscription.
 *
 * @param <T> the resource type {@link MessageSubscription#getResourceTypeId()}
 */
@JsonDeserialize(as = MessageSubscriptionPayloadImpl.class)
public interface MessageSubscriptionPayload<T> extends Payload<T> {
    /**
     * The message payload will always contain the common fields:
     * <ul>
     * <li>{@link Message#getId()}</li>
     * <li>{@link Message#getVersion()}</li>
     * <li>{@link Message#getSequenceNumber()}</li>
     * <li>{@link Message#getResourceVersion()}</li>
     * <li>{@link Message#getCreatedAt()}</li>
     * <li>{@link Message#getLastModifiedAt()}</li>
     * </ul>
     * for any message. If the payload fits within the size limit of you message queue (the limit is often 256kb),
     * all additional fields for the specific message are included as well (along with the {@link Message#getType()} property).
     * If the payload did not fit, it can be retrieved from the Messages endpoint if messages are enabled.
     * <p>
     * You can check if this payloads contains a complete message with {@link #hasCompleteMessage()}.
     * In this case you can use {@link #as(Class)} to retrieve the typed message.
     *
     * @return the message payload
     */
    @JsonUnwrapped
    @JsonProperty("message")
    Message getMessage();


    /**
     * @return
     */
    @Nullable
    PayloadNotIncluded getPaylaodNotIncluded();


    /**
     * Returns true iff. this payload contains a complete message.
     *
     * @return true iff. this payload contains a complete message
     * @see #getMessage()
     */
    @JsonIgnore
    default boolean hasCompleteMessage() {
        return getMessage().getType() != null;
    }

    /**
     * Returns {@link #getMessage()} as a message of the given message class.
     *
     * @param messageClass the message class
     * @param <M>          the message type
     *
     * @return the typed message
     * @throws IllegalStateException if this payload contains an incomplete message {@link #hasCompleteMessage()}
     */
    @JsonIgnore
    default <M extends Message> M as(final Class<M> messageClass) {
        if (!hasCompleteMessage()) {
            throw new IllegalStateException("Can't retrieve typed message for an incomplete message");
        }
        return getMessage().as(messageClass);
    }
}
