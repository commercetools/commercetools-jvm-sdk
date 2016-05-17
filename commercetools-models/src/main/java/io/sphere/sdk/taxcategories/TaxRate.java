package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(as=TaxRateImpl.class)
public interface TaxRate {
    @Nullable
    String getId();

    String getName();

    Double getAmount();

    Boolean isIncludedInPrice();

    CountryCode getCountry();

    @Nullable
    String getState();

    @Nonnull
    List<SubRate> getSubRates();
}
