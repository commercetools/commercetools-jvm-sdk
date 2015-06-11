package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.queries.*;

final class CategoryQueryImpl extends MetaModelQueryDslImpl<Category, CategoryQuery, CategoryQueryModel, CategoryExpansionModel<Category>> implements CategoryQuery {
    CategoryQueryImpl(){
        super(CategoryEndpoint.ENDPOINT.endpoint(), CategoryQuery.resultTypeReference(), CategoryQueryModel.of(), CategoryExpansionModel.of(), CategoryQueryImpl::new);
    }

    private CategoryQueryImpl(final MetaModelQueryDslBuilder<Category, CategoryQuery, CategoryQueryModel, CategoryExpansionModel<Category>> builder) {
        super(builder);
    }
}