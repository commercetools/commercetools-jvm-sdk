package io.sphere.sdk.orderedits;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@JsonDeserialize(as = OrderEditNotProcessedImpl.class)
@ResourceValue
public interface OrderEditNotProcessed extends OrderEditResult {

}
