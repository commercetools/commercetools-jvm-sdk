package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * In case multiple shipping addresses are needed for a cart, {@link ItemShippingDetails} contains the information where
 * the individual items should be sent to. If needed, it is possible to set different addresses per sub-quantity of a line item.
 */
@ResourceValue
@JsonDeserialize(as = ItemShippingDetailsImpl.class)
public interface ItemShippingDetails {

    List<ItemShippingTarget> getTargets();


    /**
     * Convenience method to extract map address keys to their target without further processing
     * @return Map with addressKey as key and destined quantity as value.
     */
    default Map<String, Long> getTargetsMap() {
        return getTargets().stream().collect(Collectors.toMap(ItemShippingTarget::getAddressKey, ItemShippingTarget::getQuantity));
    }


    /**
     * Boolean, {@literal true} if the quantity of the (custom) line item is equal to the sum of the quantities in {@code targets}, {@literal false}
     * otherwise. A cart cannot be ordered when the value is {@literal false} and the error
     * {@link io.sphere.sdk.shippingmethods.errors.InvalidItemShippingDetailsError} will be triggered.
     * @return
     */
    @JsonProperty("valid")
    boolean isValid();

}
