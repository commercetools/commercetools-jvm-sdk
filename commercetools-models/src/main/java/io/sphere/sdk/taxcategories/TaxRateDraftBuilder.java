package io.sphere.sdk.taxcategories;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public final class TaxRateDraftBuilder extends Base implements Builder<TaxRateDraft> {
    private final String name;
    private Double amount;
    private final boolean includedInPrice;
    private final CountryCode country;
    @Nullable
    private String state;
    private List<SubRate> subRates = Collections.emptyList();

    private TaxRateDraftBuilder(final String name, final Double amount, final boolean includedInPrice, final CountryCode country) {
        this.name = name;
        this.amount = amount;
        this.includedInPrice = includedInPrice;
        this.country = country;
    }

    public static TaxRateDraftBuilder of(final TaxRate taxRate) {
        return of(taxRate.getName(), taxRate.getAmount(), taxRate.isIncludedInPrice(), taxRate.getCountry())
                .state(taxRate.getState())
                .subRates(taxRate.getSubRates());
    }

    public static TaxRateDraftBuilder of(final TaxRateDraft taxRate) {
        return of(taxRate.getName(), taxRate.getAmount(), taxRate.isIncludedInPrice(), taxRate.getCountry())
                .state(taxRate.getState())
                .subRates(taxRate.getSubRates());
    }

    public static TaxRateDraftBuilder of(final String name, final double amount, final boolean includedInPrice, final CountryCode country) {
        return new TaxRateDraftBuilder(name, amount, includedInPrice, country);
    }

    public TaxRateDraftBuilder state(@Nullable final String state) {
        this.state = state;
        return this;
    }

    public TaxRateDraftBuilder amount(final double amount) {
        this.amount = amount;
        return this;
    }

    public TaxRateDraftBuilder subRates(final List<SubRate> subRates) {
        this.subRates = subRates;
        return this;
    }

    @Override
    public TaxRateDraft build() {
        return new TaxRateDraftImpl(name, amount, includedInPrice, country, state, subRates);
    }
}
