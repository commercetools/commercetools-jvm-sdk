package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;

import java.util.Optional;

import static io.sphere.sdk.http.HttpStatusCode.NOT_FOUND_404;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link CustomObject} by container and key.
 * @param <T> The type of the value of the custom object.
 */
final class CustomObjectByKeyGetImpl<T> extends SphereRequestBase implements CustomObjectByKeyGet<T> {
    private final String container;
    private final String key;
    private final TypeReference<T> typeReference;

    public CustomObjectByKeyGetImpl(final String container, final String key, final TypeReference<T> typeReference) {
        this.container = container;
        this.key = key;
        this.typeReference = typeReference;
    }

    @Override
    public CustomObject<T> deserialize(final HttpResponse httpResponse) {
        return Optional.ofNullable(httpResponse)
                .filter(response -> response.getResponseBody() != null && response.getResponseBody().length > 0)
                .map(response -> response.getResponseBody())
                .map(responseBody -> {
                    final TypeFactory typeFactory = TypeFactory.defaultInstance();
                    final JavaType typeParameterJavaType = typeFactory.constructType(typeReference);
                    final JavaType resultJavaType = typeFactory.constructParametrizedType(CustomObject.class, CustomObject.class, typeParameterJavaType);
                    return SphereJsonUtils.<CustomObject<T>>readObject(httpResponse.getResponseBody(), resultJavaType);
                })
                .orElse(null);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.GET, CustomObjectEndpoint.PATH + "/" + container + "/" + key);
    }

    @Override
    public boolean canDeserialize(final HttpResponse httpResponse) {
        return httpResponse.hasSuccessResponseCode() || httpResponse.getStatusCode() == NOT_FOUND_404;
    }
}
