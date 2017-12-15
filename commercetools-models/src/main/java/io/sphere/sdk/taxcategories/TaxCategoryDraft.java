package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Draft for a new TaxCategory.
 * <p>
 * If you need to create a TaxCategory without tax rates, just provide an empty list as parameter for {@code taxRates}.
 */
@JsonDeserialize(as = TaxCategoryDraftDsl.class)
@ResourceDraftValue(
        factoryMethods = {@FactoryMethod(parameterNames = {"name", "taxRates", "description"})})
public interface TaxCategoryDraft extends WithKey {
    String getName();

    @Nullable
    String getKey();

    @Nullable
    String getDescription();

    @JsonProperty("rates")
    List<TaxRateDraft> getTaxRates();


    static TaxCategoryDraft of(final String name, final List<TaxRateDraft> taxRates, @Nullable final String description) {
        return TaxCategoryDraftDsl.of(name, taxRates, description);
    }

    static TaxCategoryDraft of(final String name, final List<TaxRateDraft> taxRates) {
        return of(name, taxRates, null);
    }
}
