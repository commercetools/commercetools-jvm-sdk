package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

import java.util.List;
import java.util.function.Function;

/**
 * Fetches a tax category by a known ID.
 *
 * {@include.example io.sphere.sdk.taxcategories.queries.TaxCategoryByIdGetIntegrationTest#execution()}
 */
public interface TaxCategoryByIdGet extends MetaModelGetDsl<TaxCategory, TaxCategory, TaxCategoryByIdGet, TaxCategoryExpansionModel<TaxCategory>> {
    static TaxCategoryByIdGet of(final Identifiable<TaxCategory> taxCategoryIdentifiable) {
        return of(taxCategoryIdentifiable.getId());
    }

    static TaxCategoryByIdGet of(final String id) {
        return new TaxCategoryByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<TaxCategory>> expansionPaths();

    @Override
    TaxCategoryByIdGet plusExpansionPaths(final ExpansionPath<TaxCategory> expansionPath);

    @Override
    TaxCategoryByIdGet withExpansionPaths(final ExpansionPath<TaxCategory> expansionPath);

    @Override
    TaxCategoryByIdGet withExpansionPaths(final List<ExpansionPath<TaxCategory>> expansionPaths);
}

