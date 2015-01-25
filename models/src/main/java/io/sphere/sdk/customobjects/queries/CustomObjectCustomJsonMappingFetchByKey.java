package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.client.ClientRequestBase;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.queries.Fetch;

import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.http.HttpMethod.GET;

/**
 * {@link io.sphere.sdk.client.ClientRequest} to fetch one {@link io.sphere.sdk.customobjects.CustomObject} by container and key but using a custom JSON mapper instead of the SDK default one.
 *
 * @param <T> The type of the value of the custom object.
 */
public abstract class CustomObjectCustomJsonMappingFetchByKey<T> extends ClientRequestBase implements Fetch<CustomObject<T>> {
    private final String container;
    private final String key;

    public CustomObjectCustomJsonMappingFetchByKey(final String container, final String key) {
        this.container = container;
        this.key = key;
    }

    @Override
    public HttpRequest httpRequest() {
        return HttpRequest.of(GET, CustomObjectsEndpoint.PATH + "/" + container + "/" + key);
    }

    @Override
    public final Function<HttpResponse, Optional<CustomObject<T>>> resultMapper() {
        return httpResponse -> {
            final Optional<CustomObject<T>> result;
            if (httpResponse.getStatusCode() == 404) {
                result = Optional.empty();
            } else {
                final CustomObject<T> customObject = deserializeCustomObject().apply(httpResponse);
                result = Optional.of(customObject);
            }
            return result;
        };
    }

    protected abstract Function<HttpResponse, CustomObject<T>> deserializeCustomObject();

    @Override
    public boolean canHandleResponse(final HttpResponse response) {
        return response.hasSuccessResponseCode() || response.getStatusCode() == 404;
    }
}

