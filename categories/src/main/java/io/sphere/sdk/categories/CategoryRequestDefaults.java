package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.PagedQueryResult;

final class CategoryRequestDefaults {
    private CategoryRequestDefaults() {
    }

    static final String ENDPOINT = "/categories";

    static final TypeReference<PagedQueryResult<CategoryImpl>> PAGED_QUERY_RESULT_TYPE_REFERENCE = new TypeReference<PagedQueryResult<CategoryImpl>>() {

    };

    static final TypeReference<CategoryImpl> CATEGORY_TYPE_REFERENCE = new TypeReference<CategoryImpl>() {

    };
}
