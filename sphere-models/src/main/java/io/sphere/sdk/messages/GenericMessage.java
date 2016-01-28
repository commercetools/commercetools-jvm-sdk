package io.sphere.sdk.messages;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.Reference;

/**
 * Message for a certain Java type.
 *
 * @param <T> the type of the resource the message is belonging to, like {@code <T>} is {@link io.sphere.sdk.products.Product} for {@link io.sphere.sdk.products.messages.ProductPublishedMessage}
 *
 * @see io.sphere.sdk.messages.queries.MessageQuery
 */
@JsonDeserialize(as = GenericMessageImpl.class)
public interface GenericMessage<T> extends Message {
    Reference<T> getResource();
}
