package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.queries.Get;

import javax.annotation.Nullable;
import java.util.function.Function;

import static io.sphere.sdk.http.HttpMethod.GET;
import static io.sphere.sdk.http.HttpStatusCode.NOT_FOUND_404;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link io.sphere.sdk.customobjects.CustomObject} by container and key but using a custom JSON mapper instead of the SDK default one.
 *
 * <p>Example for an implementation with Google GSON (multiple code snippets):</p>
 *
 * <p>The class of the custom object value:</p>
 * {@include.example io.sphere.sdk.customobjects.demo.GsonFoo}
 *
 * <p>The class including the wrapper custom object:</p>
 * {@include.example io.sphere.sdk.customobjects.demo.GsonFooCustomObject}
 *
 * <p>The implementation of the getter:</p>
 * {@include.example io.sphere.sdk.customobjects.demo.GsonFooCustomObjectByKeyGet}
 *
 * <p>An execution example:</p>
 * {@include.example io.sphere.sdk.customobjects.queries.CustomObjectCustomJsonMappingByKeyGetTest#execution()}
 * @param <T> The type of the value of the custom object.
 * @see CustomObject
 */
public abstract class CustomObjectCustomJsonMappingByKeyGet<T> extends SphereRequestBase implements Get<CustomObject<T>> {
    private final String container;
    private final String key;

    public CustomObjectCustomJsonMappingByKeyGet(final String container, final String key) {
        this.container = container;
        this.key = key;
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(GET, CustomObjectEndpoint.PATH + "/" + container + "/" + key);
    }

    @Nullable
    @Override
    public final CustomObject<T> deserialize(final HttpResponse httpResponse) {
        return (httpResponse.getStatusCode() == NOT_FOUND_404) ? null : deserializeCustomObject(httpResponse);
    }

    /**
     *
     * @deprecated override {@link #deserializeCustomObject(HttpResponse)} instead.
     */
    @Deprecated
    protected final Function<HttpResponse, CustomObject<T>> deserializeCustomObject() {
        return httpResponse -> deserializeCustomObject(httpResponse);
    }

    protected abstract CustomObject<T> deserializeCustomObject(final HttpResponse httpResponse);

    @Override
    public boolean canDeserialize(final HttpResponse httpResponse) {
        return httpResponse.hasSuccessResponseCode() || httpResponse.getStatusCode() == NOT_FOUND_404;
    }
}

