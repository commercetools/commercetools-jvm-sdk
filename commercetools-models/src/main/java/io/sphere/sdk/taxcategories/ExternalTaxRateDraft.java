package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(as = ExternalTaxRateDraftImpl.class)
public interface ExternalTaxRateDraft {
    String getName();

    /**
     * Percentage in the range of [0..1]. Must be supplied if no `subRates` are specified.
     * @return amount or null
     */
    @Nullable
    Double getAmount();

    CountryCode getCountry();

    @Nullable
    String getState();

    @Nullable
    Boolean isIncludedInPrice();

    /**
     * For countries (e.g. the US) where the total tax is a combination of multiple taxes (e.g. state and local taxes).
     * @return sub rates or null
     */
    @Nullable
    List<SubRate> getSubRates();
}
