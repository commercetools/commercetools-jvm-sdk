package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderShippingInfo;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderShippingInfoSetMessage.class)//important to override annotation in Message class
public final class OrderShippingInfoSetMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {

    public static final String MESSAGE_TYPE = "OrderShippingInfoSet";
    public static final MessageDerivateHint<OrderShippingInfoSetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderShippingInfoSetMessage.class, Order.referenceTypeId());

    @Nullable
    private final OrderShippingInfo shippingInfo;

    @Nullable
    private final OrderShippingInfo oldShippingInfo;

    @JsonCreator
    private OrderShippingInfoSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber,
                                        final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers,
                                        final OrderShippingInfo shippingInfo, final OrderShippingInfo oldShippingInfo) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.shippingInfo = shippingInfo;
        this.oldShippingInfo = oldShippingInfo;
    }

    @Nullable
    public OrderShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    @Nullable
    public OrderShippingInfo getOldShippingInfo() {
        return oldShippingInfo;
    }
}
