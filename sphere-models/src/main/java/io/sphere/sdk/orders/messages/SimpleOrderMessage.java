package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessage;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.queries.PagedQueryResult;

@JsonDeserialize(as = SimpleOrderMessageImpl.class)
public interface SimpleOrderMessage extends GenericMessage<Order> {
        MessageDerivateHint<SimpleOrderMessage> MESSAGE_HINT =
            MessageDerivateHint.ofResourceType(Order.referenceTypeId(),
                    new TypeReference<PagedQueryResult<SimpleOrderMessage>>() {
                    },
                    new TypeReference<SimpleOrderMessage>() {
                    }
            );
}
