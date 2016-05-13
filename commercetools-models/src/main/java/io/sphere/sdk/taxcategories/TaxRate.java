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

    /**
     * compares tax categories by all fields except by ID
     *
     * @param other other
     * @return boolean
     * @deprecated try TaxRateDraftBuilder.of(other).build().equals(TaxRateDraftBuilder.of(this).build())
     */
    @Deprecated
    boolean equalsIgnoreId(TaxRate other);
}
