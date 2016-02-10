package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.json.SphereJsonUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletionException;

/**
 * Utils for common behaviour of {@link SphereRequest}s.
 */
public final class SphereRequestUtils {
    private SphereRequestUtils() {
    }

    public static <T> T deserialize(final HttpResponse httpResponse, final TypeReference<T> typeReference) {
        return SphereJsonUtils.readObject(Optional.ofNullable(httpResponse.getResponseBody()).orElseThrow(() -> new JsonException(httpResponse)), typeReference);
    }

    public static <T> T deserialize(final HttpResponse httpResponse, final JavaType javaType) {
        return SphereJsonUtils.readObject(Optional.ofNullable(httpResponse.getResponseBody()).orElseThrow(() -> new JsonException(httpResponse)), javaType);
    }

    public static String getBodyAsString(final HttpResponse httpResponse) {
        return new String(httpResponse.getResponseBody(), StandardCharsets.UTF_8);
    }

    /**
     * Encodes urls with UTF-8.
     * @param s String which should be URL encoded
     * @return url encoded s
     */
    public static String urlEncode(final String s) {
        final String encoding = "UTF-8";
        try {
            return URLEncoder.encode(s, encoding);
        } catch (final UnsupportedEncodingException e) {
            throw new CompletionException(String.format("Could not encode url %s with encoding %s", s, encoding), e);
        }
    }
}
