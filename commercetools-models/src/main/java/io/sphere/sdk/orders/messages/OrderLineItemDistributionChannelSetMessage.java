package io.sphere.sdk.orders.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;

import java.time.ZonedDateTime;

@JsonDeserialize(as = OrderLineItemDistributionChannelSetMessage.class)
public final class OrderLineItemDistributionChannelSetMessage extends GenericMessageImpl<Order> implements SimpleOrderMessage{
    public static final String MESSAGE_TYPE = "OrderLineItemDistributionChannelSet";
    public static final MessageDerivateHint<OrderLineItemDistributionChannelSetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, OrderLineItemDistributionChannelSetMessage.class, Order.referenceTypeId());

    private final String lineItemId;
    private final Reference<Channel> distributionChannel;

    @JsonCreator
    private OrderLineItemDistributionChannelSetMessage(String id, Long version, ZonedDateTime createdAt, ZonedDateTime lastModifiedAt, JsonNode resource, Long sequenceNumber, Long resourceVersion, String type, UserProvidedIdentifiers resourceUserProvidedIdentifiers, String lineItemId, Reference<Channel> distributionChannel) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, resourceUserProvidedIdentifiers, Order.class);
        this.lineItemId = lineItemId;
        this.distributionChannel = distributionChannel;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    public Reference<Channel> getDistributionChannel() {
        return distributionChannel;
    }
}
