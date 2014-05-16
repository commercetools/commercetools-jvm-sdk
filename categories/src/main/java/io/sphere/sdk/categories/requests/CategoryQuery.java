package io.sphere.sdk.categories.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryImpl;
import io.sphere.sdk.client.*;

import static io.sphere.sdk.categories.requests.CategoryRequestDefaults.*;

public class CategoryQuery extends BaseQuery<Category, CategoryImpl> implements Query<Category, CategoryImpl>, Requestable {

    @Override
    protected String endPoint() {
        return ENDPOINT;
    }

    @Override
    public TypeReference<PagedQueryResult<CategoryImpl>> typeReference() {
        return PAGED_QUERY_RESULT_TYPE_REFERENCE;
    }
}
