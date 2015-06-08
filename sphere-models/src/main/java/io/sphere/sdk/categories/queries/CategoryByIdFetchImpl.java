package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

public class CategoryByIdFetchImpl extends MetaModelFetchDslImpl<Category, CategoryByIdFetch, CategoryExpansionModel<Category>> implements CategoryByIdFetch {
    CategoryByIdFetchImpl(final String id) {
        super(id, CategoryEndpoint.ENDPOINT, CategoryExpansionModel.of(), CategoryByIdFetchImpl::new);
    }

    public CategoryByIdFetchImpl(MetaModelFetchDslBuilder<Category, CategoryByIdFetch, CategoryExpansionModel<Category>> builder) {
        super(builder);
    }
}
