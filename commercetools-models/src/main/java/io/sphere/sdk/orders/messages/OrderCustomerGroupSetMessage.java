package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;

import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderCustomerGroupSetMessage.class)//important to override annotation in Message class
public final class OrderCustomerGroupSetMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {
    public static final String MESSAGE_TYPE = "OrderCustomerGroupSet";
    public static final MessageDerivateHint<OrderCustomerGroupSetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderCustomerGroupSetMessage.class, Order.referenceTypeId());

    private final Reference<CustomerGroup> customerGroup;

    private final Reference<CustomerGroup> oldCustomerGroup;

    @JsonCreator
    private OrderCustomerGroupSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Reference<CustomerGroup> customerGroup, final Reference<CustomerGroup> oldCustomerGroup) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.customerGroup = customerGroup;
        this.oldCustomerGroup = oldCustomerGroup;
    }

    public Reference<CustomerGroup> getOldCustomerGroup() {
        return oldCustomerGroup;
    }

    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }
}
