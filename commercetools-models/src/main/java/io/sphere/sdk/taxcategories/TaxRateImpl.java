package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

final class TaxRateImpl extends Base implements TaxRate {
    @Nullable
    private final String id;
    private final String name;
    private final Double amount;
    private final Boolean includedInPrice;
    private final CountryCode country;
    @Nullable
    private final String state;

    @JsonCreator
    TaxRateImpl(@Nullable final String id, final String name, final Double amount, final Boolean includedInPrice,
                final CountryCode country, @Nullable final String state) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.includedInPrice = includedInPrice;
        this.country = country;
        this.state = state;
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
    public boolean equalsIgnoreId(final TaxRate other) {
        return other != null
                && TaxRateBuilder.of(other).id(null).build().equals(TaxRateBuilder.of(this).id(null).build());
    }
}
