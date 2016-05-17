package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

@JsonDeserialize(as = TaxPortionImpl.class)
public interface TaxPortion {

    static TaxPortion of(final double rate, final MonetaryAmount amount) {
        return new TaxPortionImpl(rate, amount, null);
    }

    static TaxPortion of(final double rate, final MonetaryAmount amount, final String name) {
        return new TaxPortionImpl(rate, amount, name);
    }

    Double getRate();

    MonetaryAmount getAmount();

    @Nullable
    String getName();
}
