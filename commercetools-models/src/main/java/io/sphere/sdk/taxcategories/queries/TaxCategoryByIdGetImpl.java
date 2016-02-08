package io.sphere.sdk.taxcategories.queries;

import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

final class TaxCategoryByIdGetImpl extends MetaModelGetDslImpl<TaxCategory, TaxCategory, TaxCategoryByIdGet, TaxCategoryExpansionModel<TaxCategory>> implements TaxCategoryByIdGet {
    TaxCategoryByIdGetImpl(final String id) {
        super(id, TaxCategoryEndpoint.ENDPOINT, TaxCategoryExpansionModel.of(), TaxCategoryByIdGetImpl::new);
    }

    public TaxCategoryByIdGetImpl(MetaModelGetDslBuilder<TaxCategory, TaxCategory, TaxCategoryByIdGet, TaxCategoryExpansionModel<TaxCategory>> builder) {
        super(builder);
    }
}
