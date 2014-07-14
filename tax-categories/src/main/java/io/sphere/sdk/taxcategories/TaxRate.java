package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Optional;
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
}
