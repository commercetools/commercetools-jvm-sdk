package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class CategoryByIdGetImpl extends MetaModelGetDslImpl<Category, Category, CategoryByIdGet, CategoryExpansionModel<Category>> implements CategoryByIdGet {
    CategoryByIdGetImpl(final String id) {
        super(id, CategoryEndpoint.ENDPOINT, CategoryExpansionModel.of(), CategoryByIdGetImpl::new);
    }

    public CategoryByIdGetImpl(final MetaModelGetDslBuilder<Category, Category, CategoryByIdGet, CategoryExpansionModel<Category>> builder) {
        super(builder);
    }
}
