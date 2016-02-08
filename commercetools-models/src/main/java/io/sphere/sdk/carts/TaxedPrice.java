package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;
import java.util.List;

public final class TaxedPrice extends Base {
    private final MonetaryAmount totalNet;
    private final MonetaryAmount totalGross;
    private final List<TaxPortion> taxPortions;

    @JsonCreator
    private TaxedPrice(final MonetaryAmount totalNet, final MonetaryAmount totalGross, final List<TaxPortion> taxPortions) {
        this.totalNet = totalNet;
        this.totalGross = totalGross;
        this.taxPortions = taxPortions;
    }

    public static TaxedPrice of(final MonetaryAmount totalNet, final MonetaryAmount totalGross, final List<TaxPortion> taxPortions) {
        return new TaxedPrice(totalNet, totalGross, taxPortions);
    }

    public MonetaryAmount getTotalNet() {
        return totalNet;
    }

    public MonetaryAmount getTotalGross() {
        return totalGross;
    }

    public List<TaxPortion> getTaxPortions() {
        return taxPortions;
    }
}
