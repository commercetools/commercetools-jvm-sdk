package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderCustomerSetMessage.class)//important to override annotation in Message class
public final class OrderCustomerSetMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {

    public static final String MESSAGE_TYPE = "OrderCustomerSet";
    public static final MessageDerivateHint<OrderCustomerSetMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderCustomerSetMessage.class, Order.referenceTypeId());

    Reference<Customer> customer;
    Reference<Customer> oldCustomer;

    @Nullable
    Reference<CustomerGroup> customerGroup;

    @Nullable
    Reference<CustomerGroup> oldCustomerGroup;

    @JsonCreator
    private OrderCustomerSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers,
                                    final Reference<Customer> customer, @Nullable final Reference<CustomerGroup> customerGroup, final Reference<Customer> oldCustomer,@Nullable  final Reference<CustomerGroup> oldCustomerGroup) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.customer = customer;
        this.customerGroup = customerGroup;
        this.oldCustomer = oldCustomer;
        this.oldCustomerGroup = oldCustomerGroup;
    }

    public Reference<Customer> getCustomer() {
        return customer;
    }

    public Reference<Customer> getOldCustomer() {
        return oldCustomer;
    }

    @Nullable
    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }

    @Nullable
    public Reference<CustomerGroup> getOldCustomerGroup() {
        return oldCustomerGroup;
    }
}
