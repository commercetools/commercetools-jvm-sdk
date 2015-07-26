package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

class TaxRateImpl extends Base implements TaxRate {
    @Nullable
    private final String id;
    private final String name;
    private final double amount;
    private final boolean includedInPrice;
    private final CountryCode country;
    @Nullable
    private final String state;

    @JsonCreator
    TaxRateImpl(@Nullable final String id, final String name, final double amount, final boolean includedInPrice,
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
    public double getAmount() {
        return amount;
    }

    @Override
    public boolean isIncludedInPrice() {
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
}
