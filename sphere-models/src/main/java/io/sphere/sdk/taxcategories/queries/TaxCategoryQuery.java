package io.sphere.sdk.taxcategories.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.UltraQueryDsl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

/**
 {@doc.gen summary tax categories}
 */
public interface TaxCategoryQuery extends UltraQueryDsl<TaxCategory, TaxCategoryQuery, TaxCategoryQueryModel<TaxCategory>, TaxCategoryExpansionModel<TaxCategory>> {
    static TypeReference<PagedQueryResult<TaxCategory>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<TaxCategory>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<TaxCategory>>";
            }
        };
    }

    default TaxCategoryQuery byName(final String name) {
        return withPredicate(TaxCategoryQueryModel.of().name().is(name));
    }

    static TaxCategoryQuery of() {
        return new TaxCategoryQueryImpl();
    }
}
