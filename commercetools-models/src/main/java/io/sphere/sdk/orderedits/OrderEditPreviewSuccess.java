package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.messages.OrderMessage;
import java.util.List;

@JsonDeserialize(as = OrderEditPreviewSuccessImpl.class)
@ResourceValue
public interface OrderEditPreviewSuccess extends OrderEditResult {

    Order getPreview();

    List<OrderMessage> getMessagePayloads();
}