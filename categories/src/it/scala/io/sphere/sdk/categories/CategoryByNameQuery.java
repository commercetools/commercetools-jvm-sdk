package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Function;
import io.sphere.sdk.client.HttpMethod;
import io.sphere.sdk.client.HttpRequest;
import io.sphere.sdk.client.HttpResponse;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.QueryDslImpl;
import io.sphere.sdk.queries.StringQueryModel;

import java.util.Locale;

import static io.sphere.sdk.utils.UrlUtils.urlEncode;

/**
 * This is an example of hard coding queries without using helper classes.
 * It is better to use the helpers concerning existing parts and escaping.
 */
public class CategoryByNameQuery implements Query<Category> {
    private final Locale locale;
    private final String name;

    public CategoryByNameQuery(final Locale locale, final String name) {
        this.locale = locale;
        this.name = name;
    }

    @Override
    public HttpRequest httpRequest() {
        return HttpRequest.of(HttpMethod.GET, "/categories?where=" + urlEncode("name(" + locale.toLanguageTag() + "=\"" + StringQueryModel.escape(name) + "\")"));
    }

    @Override
    public String toString() {
        return "CategoryByNameQuery{" +
                "locale=" + locale +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public Function<HttpResponse, PagedQueryResult<Category>> resultMapper() {
        return QueryDslImpl.resultMapperOf(new TypeReference<PagedQueryResult<CategoryImpl>>() {
        });
    }
}
