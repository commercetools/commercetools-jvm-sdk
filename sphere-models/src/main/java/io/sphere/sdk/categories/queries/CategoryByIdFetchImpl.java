package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

final class CategoryByIdFetchImpl extends MetaModelFetchDslImpl<Category, Category, CategoryByIdFetch, CategoryExpansionModel<Category>> implements CategoryByIdFetch {
    CategoryByIdFetchImpl(final String id) {
        super(id, CategoryEndpoint.ENDPOINT, CategoryExpansionModel.of(), CategoryByIdFetchImpl::new);
    }

    public CategoryByIdFetchImpl(MetaModelFetchDslBuilder<Category, Category, CategoryByIdFetch, CategoryExpansionModel<Category>> builder) {
        super(builder);
    }
}
