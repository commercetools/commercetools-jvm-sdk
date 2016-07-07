package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orders.Order;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * This message may appear if the billing address of an order is changed.
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#setBillingAddress()}
 *
 * @see Order#getBillingAddress()
 * @see io.sphere.sdk.orders.commands.updateactions.SetBillingAddress
 */
@JsonDeserialize(as = BillingAddressSetMessage.class)//important to override annotation in Message class
public final class BillingAddressSetMessage extends GenericMessageImpl<Order> {
    public static final String MESSAGE_TYPE = "BillingAddressSet";
    public static final MessageDerivateHint<BillingAddressSetMessage> MESSAGE_HINT = MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, BillingAddressSetMessage.class, Order.referenceTypeId());

    @Nullable
    private final Address address;

    @JsonCreator
    private BillingAddressSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final Address address) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, Order.class);
        this.address = address;
    }

    @Nullable
    public Address getAddress() {
        return address;
    }
}
