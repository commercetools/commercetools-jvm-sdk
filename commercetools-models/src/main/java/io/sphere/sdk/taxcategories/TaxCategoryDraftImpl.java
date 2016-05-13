package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.List;

final class TaxCategoryDraftImpl extends Base implements TaxCategoryDraft {
    private final String name;
    @Nullable
    private final String description;
    private final List<TaxRateDraft> taxRates;

    @JsonCreator
    TaxCategoryDraftImpl(final String name, @Nullable final String description, final List<TaxRateDraft> taxRates) {
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
    public List<TaxRateDraft> getTaxRates() {
        return taxRates;
    }
}
