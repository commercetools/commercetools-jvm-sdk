package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @see TaxRateDraftBuilder
 */
@JsonDeserialize(as=TaxRateDraftImpl.class)
public interface TaxRateDraft {
    String getName();

    Double getAmount();

    Boolean isIncludedInPrice();

    CountryCode getCountry();

    @Nullable
    String getState();

    List<SubRate> getSubRates();

    static TaxRateDraft of(final String name, final double amount, final boolean includedInPrice, final CountryCode country) {
        return TaxRateDraftBuilder.of(name, amount, includedInPrice, country).build();
    }
}
