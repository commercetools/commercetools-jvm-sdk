package io.sphere.sdk.taxcategories;

import com.google.common.base.Optional;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Builder;

public final class TaxRateBuilder implements Builder<TaxRate> {
    private Optional<String> id = Optional.absent();
    private String name;
    private double amount;
    private boolean includedInPrice;
    private CountryCode country;
    private Optional<String> state = Optional.absent();

    private TaxRateBuilder(final String name, final double amount, final boolean includedInPrice, final CountryCode country) {
        this.name = name;
        this.amount = amount;
        this.includedInPrice = includedInPrice;
        this.country = country;
    }

    public static TaxRateBuilder of(final String name, final double amount, final boolean includedInPrice, final CountryCode country) {
        return new TaxRateBuilder(name, amount, includedInPrice, country);
    }

    public TaxRateBuilder id(final Optional<String> id) {
        this.id = id;
        return this;
    }
    
    public TaxRateBuilder id(final String id) {
        return id(Optional.fromNullable(id));
    }

    public TaxRateBuilder state(final Optional<String> state) {
        this.state = state;
        return this;
    }

    public TaxRateBuilder state(final String state) {
        return state(Optional.fromNullable(state));
    }

    @Override
    public TaxRate build() {
        return new TaxRateImpl(id, name, amount, includedInPrice, country, state);
    }
}
