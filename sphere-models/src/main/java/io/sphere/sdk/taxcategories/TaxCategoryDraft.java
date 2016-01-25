package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Draft for a new TaxCategory.
 *
 * <p>If you need to create a TaxCategory without tax rates, just provide an empty list as parameter for {@code taxRates}.</p>
 *
 */
@JsonDeserialize(as = TaxCategoryDraftImpl.class)
public interface TaxCategoryDraft {
    String getName();

    @Nullable
    String getDescription();

    @JsonProperty("rates")
    List<TaxRate> getTaxRates();


    static TaxCategoryDraft of(final String name, final List<TaxRate> taxRates, @Nullable final String description) {
        return new TaxCategoryDraftImpl(name, description, taxRates);
    }

    static TaxCategoryDraft of(final String name, final List<TaxRate> taxRates) {
        return of(name, taxRates, null);
    }
}
