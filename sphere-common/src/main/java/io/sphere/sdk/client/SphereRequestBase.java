package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;

/**
 * A base class with utility methods for the implementation of {@link io.sphere.sdk.client.SphereRequest}s.
 */
public abstract class SphereRequestBase extends Base {
    //do not add field members here

    protected SphereRequestBase() {
    }

    protected static <T> T deserialize(final HttpResponse httpResponse, final TypeReference<T> typeReference) {
        return SphereClientUtils.deserialize(httpResponse, typeReference);
    }

    protected static <T> T deserialize(final HttpResponse httpResponse, final JavaType javaType) {
        return SphereClientUtils.deserialize(httpResponse, javaType);
    }

    protected static String getBodyAsString(final HttpResponse httpResponse) {
        return SphereClientUtils.getBodyAsString(httpResponse);
    }
}
