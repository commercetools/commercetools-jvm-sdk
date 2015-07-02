package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivatHint;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.queries.PagedQueryResult;

import java.time.ZonedDateTime;

@JsonDeserialize(as = SimpleOrderMessage.class)
public class SimpleOrderMessage extends GenericMessageImpl<Order> {
    public static final MessageDerivatHint<SimpleOrderMessage> MESSAGE_HINT =
        MessageDerivatHint.ofResourceType(Order.typeId(),
                new TypeReference<PagedQueryResult<SimpleOrderMessage>>() {
                },
                new TypeReference<SimpleOrderMessage>() {
                }
        );

    @JsonCreator
    public SimpleOrderMessage(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final long sequenceNumber, final long resourceVersion, final String type) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type);
    }
}
