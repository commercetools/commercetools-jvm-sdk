package io.sphere.sdk.taxcategories.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.MetaModelQueryDsl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

/**
 {@doc.gen summary tax categories}
 */
public interface TaxCategoryQuery extends MetaModelQueryDsl<TaxCategory, TaxCategoryQuery, TaxCategoryQueryModel, TaxCategoryExpansionModel<TaxCategory>> {
    static TypeReference<PagedQueryResult<TaxCategory>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<TaxCategory>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<TaxCategory>>";
            }
        };
    }

    default TaxCategoryQuery byName(final String name) {
        return withPredicates(m -> m.name().is(name));
    }

    static TaxCategoryQuery of() {
        return new TaxCategoryQueryImpl();
    }
}
