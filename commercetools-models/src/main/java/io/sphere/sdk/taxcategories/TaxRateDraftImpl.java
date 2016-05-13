package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.List;

final class TaxRateDraftImpl extends Base implements TaxRateDraft {
    private final String name;
    private final Double amount;
    private final Boolean includedInPrice;
    private final CountryCode country;
    @Nullable
    private final String state;
    private final List<SubRate> subRates;

    @JsonCreator
    TaxRateDraftImpl(final String name, final Double amount, final Boolean includedInPrice,
                     final CountryCode country, @Nullable final String state, final List<SubRate> subRates) {
        this.name = name;
        this.amount = amount;
        this.includedInPrice = includedInPrice;
        this.country = country;
        this.state = state;
        this.subRates = subRates;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getAmount() {
        return amount;
    }

    @Override
    public Boolean isIncludedInPrice() {
        return includedInPrice;
    }

    @Override
    public CountryCode getCountry() {
        return country;
    }

    @Override
    @Nullable
    public String getState() {
        return state;
    }

    @Override
    public List<SubRate> getSubRates() {
        return subRates;
    }
}
