package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpMethod;
import io.sphere.sdk.client.HttpRequest;
import io.sphere.sdk.client.PagedQueryResult;
import io.sphere.sdk.queries.AtMostOneResultQuery;

import java.util.Locale;

import static io.sphere.sdk.categories.CategoryRequestDefaults.ENDPOINT;
import static io.sphere.sdk.categories.CategoryRequestDefaults.PAGED_QUERY_RESULT_TYPE_REFERENCE;
import static io.sphere.sdk.utils.UrlUtils.urlEncode;

public class GetCategoryByName implements AtMostOneResultQuery<Category, CategoryImpl> {
    private final Locale locale;
    private final String name;

    public GetCategoryByName(final Locale locale, final String name) {
        this.locale = locale;
        this.name = name;
    }

    @Override
    public HttpRequest httpRequest() {
        return HttpRequest.of(HttpMethod.GET, ENDPOINT + "?where=" + urlEncode("name(" + locale.toLanguageTag() + "=\"" + name + "\")"));
    }

    @Override
    public TypeReference<PagedQueryResult<CategoryImpl>> typeReference() {
        return PAGED_QUERY_RESULT_TYPE_REFERENCE;
    }

}
