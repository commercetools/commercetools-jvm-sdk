package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import com.neovisionaries.i18n.CountryCode;

@JsonDeserialize(as=TaxRateImpl.class)
public interface TaxRate {
    /**
     * The id is created by the backend, so will only be present if fetched from the backend.
     * @return the id or absent
     */
    Optional<String> getId();

    String getName();

    double getAmount();

    boolean isIncludedInPrice();

    CountryCode getCountry();

    Optional<String> getState();

    public static TaxRate of(final String name, final double amount, final boolean includedInPrice, final CountryCode country) {
        return TaxRateBuilder.of(name, amount, includedInPrice, country).build();
    }
}
