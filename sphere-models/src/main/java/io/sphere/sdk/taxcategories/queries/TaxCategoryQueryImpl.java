package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

final class TaxCategoryQueryImpl extends MetaModelQueryDslImpl<TaxCategory, TaxCategoryQuery, TaxCategoryQueryModel, TaxCategoryExpansionModel<TaxCategory>> implements TaxCategoryQuery {
    TaxCategoryQueryImpl(){
        super(TaxCategoryEndpoint.ENDPOINT.endpoint(), TaxCategoryQuery.resultTypeReference(), TaxCategoryQueryModel.of(), TaxCategoryExpansionModel.of(), TaxCategoryQueryImpl::new);
    }

    private TaxCategoryQueryImpl(final MetaModelQueryDslBuilder<TaxCategory, TaxCategoryQuery, TaxCategoryQueryModel, TaxCategoryExpansionModel<TaxCategory>> builder) {
        super(builder);
    }
}