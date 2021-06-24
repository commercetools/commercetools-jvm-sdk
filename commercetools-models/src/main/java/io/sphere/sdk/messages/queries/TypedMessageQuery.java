package io.sphere.sdk.messages.queries;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestUtils;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;

final class TypedMessageQuery<T> extends Base implements Query<T> {
    private final HttpRequestIntent httpRequestIntent;
    private final JavaType resultJavaType;

    public TypedMessageQuery(final HttpRequestIntent httpRequestIntent, final JavaType elementJavaType) {
        this.httpRequestIntent = httpRequestIntent;
        final TypeFactory typeFactory = TypeFactory.defaultInstance();
        resultJavaType = typeFactory.constructParametricType(PagedQueryResult.class, elementJavaType);
    }

    @Override
    public PagedQueryResult<T> deserialize(final HttpResponse httpResponse) {
        return SphereRequestUtils.deserialize(httpResponse, resultJavaType);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return httpRequestIntent;
    }
}
