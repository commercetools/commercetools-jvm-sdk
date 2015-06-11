package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.queries.Fetch;

import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.http.HttpMethod.GET;
import static io.sphere.sdk.http.HttpStatusCode.NOT_FOUND_404;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link io.sphere.sdk.customobjects.CustomObject} by container and key but using a custom JSON mapper instead of the SDK default one.
 *
 * @param <T> The type of the value of the custom object.
 */
public abstract class CustomObjectCustomJsonMappingByKeyFetch<T> extends SphereRequestBase implements Fetch<CustomObject<T>> {
    private final String container;
    private final String key;

    public CustomObjectCustomJsonMappingByKeyFetch(final String container, final String key) {
        this.container = container;
        this.key = key;
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(GET, CustomObjectEndpoint.PATH + "/" + container + "/" + key);
    }

    @Override
    public final Optional<CustomObject<T>> deserialize(final HttpResponse httpResponse) {
        final Optional<CustomObject<T>> result;
        if (httpResponse.getStatusCode() == NOT_FOUND_404) {
            result = Optional.empty();
        } else {
            final CustomObject<T> customObject = deserializeCustomObject().apply(httpResponse);
            result = Optional.of(customObject);
        }
        return result;
    }

    protected abstract Function<HttpResponse, CustomObject<T>> deserializeCustomObject();

    @Override
    public boolean canDeserialize(final HttpResponse httpResponse) {
        return httpResponse.hasSuccessResponseCode() || httpResponse.getStatusCode() == NOT_FOUND_404;
    }
}

