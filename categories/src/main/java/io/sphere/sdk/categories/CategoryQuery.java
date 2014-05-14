package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.*;

public class CategoryQuery extends BaseQuery<Category, CategoryImpl> implements Query<Category, CategoryImpl>, Requestable {

    @Override
    protected String endPoint() {
        return "/categories";
    }

    @Override
    public TypeReference<PagedQueryResult<CategoryImpl>> typeReference() {
        return new TypeReference<PagedQueryResult<CategoryImpl>>(){

        };
    }
}
