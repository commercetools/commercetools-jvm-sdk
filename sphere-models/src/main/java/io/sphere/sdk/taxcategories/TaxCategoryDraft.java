package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

import java.util.List;
import java.util.Optional;

public final class TaxCategoryDraft extends Base {
    private final String name;
    private final Optional<String> description;
    private final List<TaxRate> taxRates;

    private TaxCategoryDraft(final String name, final Optional<String> description, final List<TaxRate> taxRates) {
        this.name = name;
        this.description = description;
        this.taxRates = taxRates;
    }

    public static TaxCategoryDraft of(final String name, final Optional<String> description, final List<TaxRate> taxRates) {
        return new TaxCategoryDraft(name, description, taxRates);
    }

    public static TaxCategoryDraft of(final String name, final String description, final List<TaxRate> taxRates) {
        return of(name, Optional.ofNullable(description), taxRates);
    }

    public static TaxCategoryDraft of(final String name, final List<TaxRate> taxRates) {
        return of(name, Optional.empty(), taxRates);
    }

    public String getName() {
        return name;
    }

    public Optional<String> getDescription() {
        return description;
    }

    @JsonProperty("rates")
    public List<TaxRate> getTaxRates() {
        return taxRates;
    }
}
