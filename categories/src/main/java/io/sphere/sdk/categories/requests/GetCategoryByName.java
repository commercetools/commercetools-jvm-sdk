package io.sphere.sdk.categories.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.internal.util.Util;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryImpl;
import io.sphere.sdk.client.*;

import java.util.Locale;

import static io.sphere.sdk.categories.requests.CategoryRequestDefaults.*;

public class GetCategoryByName implements AtMostOneResultQuery<Category,CategoryImpl> {
    private final Locale locale;
    private final String name;

    public GetCategoryByName(final Locale locale, final String name) {
        this.locale = locale;
        this.name = name;
    }

    @Override
    public HttpRequest httpRequest() {
        return HttpRequest.of(HttpMethod.GET, ENDPOINT + "?where=" + Util.urlEncode("name(" + Util.toLanguageTag(locale) + "=\"" + name + "\")"));
    }

    @Override
    public TypeReference<PagedQueryResult<CategoryImpl>> typeReference() {
        return PAGED_QUERY_RESULT_TYPE_REFERENCE;
    }

}
