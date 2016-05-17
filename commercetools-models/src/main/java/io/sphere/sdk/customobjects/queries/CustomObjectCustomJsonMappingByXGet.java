package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.queries.Get;

import javax.annotation.Nullable;
import java.util.function.Function;

import static io.sphere.sdk.http.HttpMethod.GET;
import static io.sphere.sdk.http.HttpStatusCode.NOT_FOUND_404;

abstract class CustomObjectCustomJsonMappingByXGet<T> extends Base implements Get<CustomObject<T>> {
    private final String path;

    public CustomObjectCustomJsonMappingByXGet(final String path) {
        this.path = path;
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(GET, CustomObjectEndpoint.PATH + path);
    }

    @Nullable
    @Override
    public final CustomObject<T> deserialize(final HttpResponse httpResponse) {
        return (httpResponse.getStatusCode() == NOT_FOUND_404) ? null : deserializeCustomObject(httpResponse);
    }

    protected abstract CustomObject<T> deserializeCustomObject(final HttpResponse httpResponse);

    @Override
    public boolean canDeserialize(final HttpResponse httpResponse) {
        return httpResponse.hasSuccessResponseCode() || httpResponse.getStatusCode() == NOT_FOUND_404;
    }
}

