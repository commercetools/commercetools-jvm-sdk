package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;

@JsonDeserialize(as=TaxRateImpl.class)
public interface TaxRate {
    /**
     * The id is created by the backend, so will only be present if fetched from the backend.
     * @return the id or absent
     */
    @Nullable
    String getId();

    String getName();

    Double getAmount();

    boolean isIncludedInPrice();

    CountryCode getCountry();

    @Nullable
    String getState();

    static TaxRate of(final String name, final double amount, final boolean includedInPrice, final CountryCode country) {
        return TaxRateBuilder.of(name, amount, includedInPrice, country).build();
    }
}
