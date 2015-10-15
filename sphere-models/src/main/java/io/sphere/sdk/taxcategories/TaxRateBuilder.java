package io.sphere.sdk.taxcategories;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Nullable;

public final class TaxRateBuilder extends Base implements Builder<TaxRate> {
    @Nullable
    private String id;
    private final String name;
    private Double amount;
    private final boolean includedInPrice;
    private final CountryCode country;
    @Nullable
    private String state;

    private TaxRateBuilder(final String name, final Double amount, final boolean includedInPrice, final CountryCode country) {
        this.name = name;
        this.amount = amount;
        this.includedInPrice = includedInPrice;
        this.country = country;
    }

    public static TaxRateBuilder of(final TaxRate taxRate) {
        return of(taxRate.getName(), taxRate.getAmount(), taxRate.isIncludedInPrice(), taxRate.getCountry())
                .id(taxRate.getId())
                .state(taxRate.getState());
    }

    public static TaxRateBuilder of(final String name, final double amount, final boolean includedInPrice, final CountryCode country) {
        return new TaxRateBuilder(name, amount, includedInPrice, country);
    }

    public TaxRateBuilder id(@Nullable final String id) {
        this.id = id;
        return this;
    }

    public TaxRateBuilder state(@Nullable final String state) {
        this.state = state;
        return this;
    }

    public TaxRateBuilder amount(final double amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public TaxRate build() {
        return new TaxRateImpl(id, name, amount, includedInPrice, country, state);
    }
}
