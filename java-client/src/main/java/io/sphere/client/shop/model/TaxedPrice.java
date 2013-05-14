package io.sphere.client.shop.model;

import io.sphere.client.model.Money;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/** The cart price with product tax rates applied. */
public class TaxedPrice {
    @Nonnull private Money totalNet;
    @Nonnull private Money totalGross;
    @Nonnull private List<TaxPortion> taxPortions = new ArrayList<TaxPortion>();

    // for JSON deserializer
    private TaxedPrice() {}

    /** The total net price (total price excluding tax). */
    @Nonnull public Money getTotalNet() { return totalNet; }

    /** The total gross price (total price including tax).  */
    @Nonnull public Money getTotalGross() { return totalGross; }

    /** The portions for individual tax rates.
     *  The following always holds: {@code totalNet + sum(tax portions) = totalGross}. */
    @Nonnull public List<TaxPortion> getTaxPortions() { return taxPortions; }
}
