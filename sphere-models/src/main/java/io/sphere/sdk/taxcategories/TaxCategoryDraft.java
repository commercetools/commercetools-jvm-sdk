package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.List;

public final class TaxCategoryDraft extends Base {
    private final String name;
    @Nullable
    private final String description;
    private final List<TaxRate> taxRates;

    private TaxCategoryDraft(final String name, @Nullable final String description, final List<TaxRate> taxRates) {
        this.name = name;
        this.description = description;
        this.taxRates = taxRates;
    }

    public static TaxCategoryDraft of(final String name, final List<TaxRate> taxRates, @Nullable final String description) {
        return new TaxCategoryDraft(name, description, taxRates);
    }

    public static TaxCategoryDraft of(final String name, final List<TaxRate> taxRates) {
        return of(name, taxRates, null);
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @JsonProperty("rates")
    public List<TaxRate> getTaxRates() {
        return taxRates;
    }
}
