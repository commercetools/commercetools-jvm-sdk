package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;

import java.util.List;

public final class NewTaxCategory {
    private final String name;
    private final Optional<String> description;
    private final List<TaxRate> taxRates;

    private NewTaxCategory(final String name, final Optional<String> description, final List<TaxRate> taxRates) {
        this.name = name;
        this.description = description;
        this.taxRates = taxRates;
    }

    public static NewTaxCategory of(final String name, final Optional<String> description, final List<TaxRate> taxRates) {
        return new NewTaxCategory(name, description, taxRates);
    }

    public static NewTaxCategory of(final String name, final List<TaxRate> taxRates) {
        return of(name, Optional.absent(), taxRates);
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
