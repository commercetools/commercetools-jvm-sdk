package io.sphere.sdk.products;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.taxcategories.SubRate;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class TaxRateTestImpl implements TaxRate {

    private final double taxRateAmount;
    private final boolean isIncludedInPrice;

    private  TaxRateTestImpl(final double taxRateAmount, final boolean isIncludedInPrice) {
        this.isIncludedInPrice = isIncludedInPrice;
        this.taxRateAmount = taxRateAmount;
    }

    public static TaxRateTestImpl of(final double taxRateAmount, final boolean isIncludedInPrice) {
        return new TaxRateTestImpl(taxRateAmount, isIncludedInPrice);
    }

    @Nullable
    @Override
    public String getId() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Double getAmount() {
        return taxRateAmount;
    }

    @Override
    public Boolean isIncludedInPrice() {
        return isIncludedInPrice;
    }

    @Override
    public CountryCode getCountry() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public String getState() {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public List<SubRate> getSubRates() {
        throw new UnsupportedOperationException();
    }
}