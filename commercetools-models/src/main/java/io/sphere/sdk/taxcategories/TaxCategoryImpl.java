package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.ResourceImpl;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

class TaxCategoryImpl extends ResourceImpl<TaxCategory> implements TaxCategory {
    private final String name;
    @Nullable
    private final String description;
    private final List<TaxRate> taxRates;

    @JsonCreator
    TaxCategoryImpl(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
                    final String name, @Nullable final String description,
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
    @Nullable
    public String getDescription() {
        return description;
    }

    @Override
    @JsonProperty("rates")
    public List<TaxRate> getTaxRates() {
        return taxRates;
    }
}
