package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.orders.Order;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * This message may appear if the customer email of an order is changed.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#setBillingAddress()}
 *
 * @see Order#getCustomerEmail()
 * @see io.sphere.sdk.orders.commands.updateactions.SetCustomerEmail
 */
@JsonDeserialize(as = OrderCustomerEmailSetMessage.class)//important to override annotation in Message class
@JsonTypeName(OrderCustomerEmailSetMessage.MESSAGE_TYPE)
public final class OrderCustomerEmailSetMessage extends GenericMessageImpl<Order> {
    public static final String MESSAGE_TYPE = "OrderCustomerEmailSet";
    public static final MessageDerivateHint<OrderCustomerEmailSetMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderCustomerEmailSetMessage.class, Order.referenceTypeId());

    @Nullable
    private String email;

    @JsonCreator
    private OrderCustomerEmailSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final String email) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, Order.class);
        this.email = email;
    }

    @Nullable
    public String getEmail() {
        return email;
    }
}
