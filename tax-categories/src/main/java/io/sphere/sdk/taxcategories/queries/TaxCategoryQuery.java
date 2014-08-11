package io.sphere.sdk.taxcategories.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.queries.DefaultModelQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.taxcategories.TaxCategory;

public class TaxCategoryQuery extends DefaultModelQuery<TaxCategory> {
    public TaxCategoryQuery(){
        super("/tax-categories", resultTypeReference());
    }

    public static TypeReference<PagedQueryResult<TaxCategory>> resultTypeReference() {
        return new TypeReference<PagedQueryResult<TaxCategory>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedQueryResult<TaxCategory>>";
            }
        };
    }

    public QueryDsl<TaxCategory> byName(final String name) {
        return withPredicate(TaxCategoryQueryModel.get().name().is(name));
    }

    public static TaxCategoryQuery of() {
        return new TaxCategoryQuery();
    }
}
