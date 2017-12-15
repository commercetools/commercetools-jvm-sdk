package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodsByCartGet;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodsByLocationGet;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;

@JsonDeserialize(as = ShippingRateImpl.class)
@ResourceValue
public interface ShippingRate {

    MonetaryAmount getPrice();

    /**
     * The shipping is free if the order total (the sum of line item prices) exceeds the free above value.
     *
     * @return the free aboce property
     */
    @Nullable
    MonetaryAmount getFreeAbove();

    /**
     * Only appears in response to requests for shipping methods by  {@link ShippingMethodsByCartGet}
     * or {@link ShippingMethodsByLocationGet} to mark this shipping rate as one that matches the cart or location.
     *
     * @return true iff. this shipping rate matches the cart or location
     */
    @JsonProperty("isMatching")
    @Nullable
    Boolean isMatching();


    /**
     * A list of shipping rate price tiers.
     * @return List of shippingRatePriceTier
     */
    @Nullable
    List<ShippingRatePriceTier> getTiers();

    static ShippingRate of(final MonetaryAmount price, @Nullable final MonetaryAmount freeAbove) {
        return of(price, freeAbove, null,null);
    }

    static ShippingRate of(final MonetaryAmount price, @Nullable final MonetaryAmount freeAbove, @Nullable final Boolean isMatching) {
        return of(price, freeAbove, isMatching,null);
    }


    static ShippingRate of(final MonetaryAmount price, @Nullable final MonetaryAmount freeAbove, @Nullable final List<ShippingRatePriceTier> tiers) {
        return of(price, freeAbove, null,tiers);
    }

    static ShippingRate of(final MonetaryAmount price, @Nullable final MonetaryAmount freeAbove, @Nullable final Boolean isMatching, @Nullable final List<ShippingRatePriceTier> tiers) {
        return new ShippingRateImpl(freeAbove, isMatching, price,tiers);
    }

    static ShippingRate of(final MonetaryAmount price) {
        return of(price, null);
    }
}
