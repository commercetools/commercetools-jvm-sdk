package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.money.MonetaryAmount;
import java.util.List;

@JsonDeserialize(as = TaxedPriceImpl.class)
public interface TaxedPrice extends TaxedItemPrice {
    static TaxedPrice of(final MonetaryAmount totalNet, final MonetaryAmount totalGross, final List<TaxPortion> taxPortions) {
        return new TaxedPriceImpl(totalNet, totalGross, taxPortions);
    }

    @Override
    MonetaryAmount getTotalNet();

    @Override
    MonetaryAmount getTotalGross();

    List<TaxPortion> getTaxPortions();
}
