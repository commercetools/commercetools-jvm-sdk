package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestUtils;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.json.SphereJsonUtils;

import java.util.Locale;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

/**
 * This is an example of hard coding queries without using helper classes.
 * It is better to use the helpers concerning existing parts and escaping.
 */
public class CategoryByNameQuery extends Base implements Query<Category> {
    private final Locale locale;
    private final String name;

    public CategoryByNameQuery(final Locale locale, final String name) {
        this.locale = locale;
        this.name = name;
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.GET, "/categories" + urlEncode("name(" + locale.toLanguageTag() + "=\"" + StringQuerySortingModel.escape(name) + "\")"));
    }

    @Override
    public PagedQueryResult<Category> deserialize(final HttpResponse httpResponse) {
        return SphereJsonUtils.readObject(httpResponse.getResponseBody(), new TypeReference<PagedQueryResult<Category>>() {
        });
    }
}
