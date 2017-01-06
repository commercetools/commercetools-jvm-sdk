package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

final class TaxRateImpl extends Base implements TaxRate {
    @Nullable
    private final String id;
    private final String name;
    private final Double amount;
    private final Boolean includedInPrice;
    private final CountryCode country;
    @Nullable
    private final String state;
    private final List<SubRate> subRates;

    @JsonCreator
    TaxRateImpl(@Nullable final String id, final String name, final Double amount, final Boolean includedInPrice,
                final CountryCode country, @Nullable final String state, final List<SubRate> subRates) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.includedInPrice = includedInPrice;
        this.country = country;
        this.state = state;
        this.subRates = subRates;
    }

    @Override
    @Nullable
    public String getId() {
        return id;
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
    @Nonnull
    public List<SubRate> getSubRates() {
        return subRates;
    }
}
