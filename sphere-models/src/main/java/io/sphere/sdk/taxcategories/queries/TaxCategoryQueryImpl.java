package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.queries.UltraQueryDslBuilder;
import io.sphere.sdk.queries.UltraQueryDslImpl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

final class TaxCategoryQueryImpl extends UltraQueryDslImpl<TaxCategory, TaxCategoryQuery, TaxCategoryQueryModel<TaxCategory>, TaxCategoryExpansionModel<TaxCategory>> implements TaxCategoryQuery {
    TaxCategoryQueryImpl(){
        super(TaxCategoryEndpoint.ENDPOINT.endpoint(), TaxCategoryQuery.resultTypeReference(), TaxCategoryQueryModel.of(), TaxCategoryExpansionModel.of(), TaxCategoryQueryImpl::new);
    }

    private TaxCategoryQueryImpl(final UltraQueryDslBuilder<TaxCategory, TaxCategoryQuery, TaxCategoryQueryModel<TaxCategory>, TaxCategoryExpansionModel<TaxCategory>> builder) {
        super(builder);
    }
}