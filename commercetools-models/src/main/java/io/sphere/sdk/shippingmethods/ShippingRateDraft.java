package io.sphere.sdk.shippingmethods;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.CopyFactoryMethod;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodsByCartGet;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodsByLocationGet;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;

@JsonDeserialize(as = ShippingRateDraftDsl.class)
@ResourceDraftValue(copyFactoryMethods = @CopyFactoryMethod(ShippingRate.class), factoryMethods = {@FactoryMethod(parameterNames = {"price","freeAbove","tiers"})})
public interface ShippingRateDraft {

    MonetaryAmount getPrice();

    /**
     * The shipping is free if the order total (the sum of line item prices) exceeds the free above value.
     *
     * @return the free aboce property
     */
    @Nullable
    MonetaryAmount getFreeAbove();

    /**
     * A list of shipping rate price tiers.
     * @return List of shippingRatePriceTier
     */
    @Nullable
    List<ShippingRatePriceTier> getTiers();


    static ShippingRateDraft of(final MonetaryAmount price) {
        return of(price, null , null);
    }

    static ShippingRateDraft of(final MonetaryAmount price, @Nullable final MonetaryAmount freeAbove) {
        return of(price, freeAbove, null);
    }


    static ShippingRateDraft of(final MonetaryAmount price, @Nullable final MonetaryAmount freeAbove,  @Nullable final List<ShippingRatePriceTier> tiers) {
        return  ShippingRateDraftDsl.of(freeAbove, price,tiers);
    }

    static ShippingRateDraft of(final ShippingRate shippingRate){
        return of(shippingRate.getPrice(),shippingRate.getFreeAbove(),shippingRate.getTiers());
    }

}
