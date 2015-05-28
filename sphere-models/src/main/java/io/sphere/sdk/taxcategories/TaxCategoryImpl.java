package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.DefaultModelImpl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

class TaxCategoryImpl extends DefaultModelImpl<TaxCategory> implements TaxCategory {
    private final String name;
    private final Optional<String> description;
    private final List<TaxRate> taxRates;

    @JsonCreator
    TaxCategoryImpl(final String id, final long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
                    final String name, final Optional<String> description,
                    @JsonProperty("rates") final List<TaxRate> taxRates) {
        super(id, version, createdAt, lastModifiedAt);
        this.name = name;
        this.description = description;
        this.taxRates = taxRates;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<String> getDescription() {
        return description;
    }

    @Override
    @JsonProperty("rates")
    public List<TaxRate> getTaxRates() {
        return taxRates;
    }
}
