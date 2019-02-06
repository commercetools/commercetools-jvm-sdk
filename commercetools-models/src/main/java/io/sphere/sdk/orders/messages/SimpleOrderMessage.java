package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessage;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.orders.Order;

/**
 * Messages for an {@link Order}.
 */
@JsonDeserialize(as = SimpleOrderMessageImpl.class)
public interface SimpleOrderMessage extends GenericMessage<Order>, OrderMessage  {
    MessageDerivateHint<SimpleOrderMessage> MESSAGE_HINT =
            MessageDerivateHint.ofResourceType(Order.referenceTypeId(), SimpleOrderMessage.class, Order.referenceTypeId());

}
