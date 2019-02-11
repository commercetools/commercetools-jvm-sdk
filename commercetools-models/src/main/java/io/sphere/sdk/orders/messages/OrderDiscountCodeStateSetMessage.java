package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeState;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderDiscountCodeStateSetMessage.class)
public final class OrderDiscountCodeStateSetMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage {

    public static final String MESSAGE_TYPE = "OrderDiscountCodeStateSet";
    public static final MessageDerivateHint<OrderDiscountCodeStateSetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderDiscountCodeStateSetMessage.class, Order.referenceTypeId());

    private final Reference<DiscountCode> discountCode;

    private final DiscountCodeState state;

    @Nullable
    private final DiscountCodeState oldState;

    @JsonCreator
    private OrderDiscountCodeStateSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
                                             final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers,
                                             final Reference<DiscountCode> discountCode, final DiscountCodeState state, @Nullable final DiscountCodeState oldState) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Order.class);
        this.discountCode = discountCode;
        this.state = state;
        this.oldState = oldState;
    }

    public Reference<DiscountCode> getDiscountCode() {
        return discountCode;
    }

    public DiscountCodeState getState() {
        return state;
    }

    @Nullable
    public DiscountCodeState getOldState() {
        return oldState;
    }
}
