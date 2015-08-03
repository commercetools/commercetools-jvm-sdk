package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessage;
import io.sphere.sdk.messages.MessageDerivatHint;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.queries.PagedQueryResult;

@JsonDeserialize(as = SimpleOrderMessageImpl.class)
public interface SimpleOrderMessage extends GenericMessage<Order> {
        MessageDerivatHint<SimpleOrderMessage> MESSAGE_HINT =
            MessageDerivatHint.ofResourceType(Order.typeId(),
                    new TypeReference<PagedQueryResult<SimpleOrderMessage>>() {
                    },
                    new TypeReference<SimpleOrderMessage>() {
                    }
            );
}
