package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = ItemShippingTargetImpl.class)
public interface ItemShippingTarget {

    String getAddressKey();

    int getQuantity();

}
