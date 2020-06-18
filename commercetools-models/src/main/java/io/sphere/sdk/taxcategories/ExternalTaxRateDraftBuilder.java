package io.sphere.sdk.taxcategories;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Nullable;
import java.util.List;

public final class ExternalTaxRateDraftBuilder extends Base implements Builder<ExternalTaxRateDraft> {
    private String name;
    @Nullable
    private Double amount;
    private CountryCode country;
    @Nullable
    private String state;
    @Nullable
    private List<SubRate> subRates;
    @Nullable
    private Boolean includedInPrice;

    private ExternalTaxRateDraftBuilder(final String name, final CountryCode country) {
        this.name = name;
        this.country = country;
    }

    public static ExternalTaxRateDraftBuilder ofAmount(final Double amount, final String name, final CountryCode country) {
        return new ExternalTaxRateDraftBuilder(name, country).amount(amount);
    }

    public static ExternalTaxRateDraftBuilder ofSubRates(final List<SubRate> subRates, final String name, final CountryCode country) {
        return new ExternalTaxRateDraftBuilder(name, country).subRates(subRates);
    }

    public ExternalTaxRateDraftBuilder subRates(final List<SubRate> subRates) {
        this.subRates = subRates;
        return this;
    }

    public ExternalTaxRateDraftBuilder amount(final Double amount) {
        this.amount = amount;
        return this;
    }

    public ExternalTaxRateDraftBuilder state(final String state) {
        this.state = state;
        return this;
    }

    public ExternalTaxRateDraftBuilder includedInPrice(final Boolean includedInPrice) {
        this.includedInPrice = includedInPrice;
        return this;
    }

    @Override
    public ExternalTaxRateDraft build() {
        return new ExternalTaxRateDraftImpl(amount, name, country, state, subRates, includedInPrice);
    }
}
