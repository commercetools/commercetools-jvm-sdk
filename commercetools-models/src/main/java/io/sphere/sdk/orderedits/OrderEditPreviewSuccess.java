package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.messages.GenericMessage;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.orders.Order;

import java.util.List;

public final class OrderEditPreviewSuccess extends Base implements OrderEditResult {

    private final Order preview;

    private final List<GenericMessage<Order>> messagePayloads;

    @JsonCreator
    public OrderEditPreviewSuccess(Order preview, List<GenericMessage<Order>> messagePayloads) {
        this.preview = preview;
        this.messagePayloads = messagePayloads;
    }

    public Order getPreview() {
        return preview;
    }

    public List<GenericMessage<Order>> getMessagePayloads() {
        return messagePayloads;
    }
}