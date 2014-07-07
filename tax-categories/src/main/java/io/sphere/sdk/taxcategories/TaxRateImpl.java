package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Optional;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;

public class TaxRateImpl extends Base implements TaxRate {
    private final Optional<String> id;
    private final String name;
    private final double amount;
    private final boolean includedInPrice;
    private final CountryCode country;
    private final Optional<String> state;

    @JsonCreator
    TaxRateImpl(final Optional<String> id, final String name, final double amount, final boolean includedInPrice,
                final CountryCode country, final Optional<String> state) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.includedInPrice = includedInPrice;
        this.country = country;
        this.state = state;
    }

    @Override
    public Optional<String> getId() {
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
    public Optional<String> getState() {
        return state;
    }
}
