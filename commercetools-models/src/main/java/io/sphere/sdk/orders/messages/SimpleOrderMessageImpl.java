package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Order;

import java.time.ZonedDateTime;

final class SimpleOrderMessageImpl extends GenericMessageImpl<Order> implements SimpleOrderMessage {

    @JsonCreator
    public SimpleOrderMessageImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
    }
}
