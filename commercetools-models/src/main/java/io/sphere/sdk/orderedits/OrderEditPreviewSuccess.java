package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.messages.OrderMessage;

import java.util.List;

public final class OrderEditPreviewSuccess extends Base implements OrderEditResult {

    private final Order preview;

    private final List<OrderMessage> messagePayloads;

    @JsonCreator
    public OrderEditPreviewSuccess(Order preview, List<OrderMessage> messagePayloads) {
        this.preview = preview;
        this.messagePayloads = messagePayloads;
    }

    public Order getPreview() {
        return preview;
    }

    public List<  OrderMessage> getMessagePayloads() {
        return messagePayloads;
    }
}