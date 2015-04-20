package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import java.nio.charset.StandardCharsets;

final class HttpResponseBodyUtils extends Base {
    private HttpResponseBodyUtils() {
    }

    public static String bytesToString(final byte[] b) {
        return new String(b, StandardCharsets.UTF_8);
    }
}
