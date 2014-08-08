package test;

import java.util.function.Function;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.requests.HttpMethod;
import io.sphere.sdk.requests.HttpRequest;
import io.sphere.sdk.requests.HttpResponse;

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
        return HttpRequest.of(HttpMethod.GET, "/categories?where=" + urlEncode("name(" + locale.toLanguageTag() + "=\"" + StringQuerySortingModel.escape(name) + "\")"));
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
        return QueryDslImpl.resultMapperOf(CategoryQuery.resultTypeReference());
    }
}
