package io.sphere.sdk.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class UrlUtils {
    private UrlUtils() {
    }

    /**
     * Encodes urls with US-ASCII.
     * @param s String which should be URL encoded
     * @return url encoded s
     */
    public static String urlEncode(final String s) {
        final String encoding = "UTF-8";
        try {
            return URLEncoder.encode(s, encoding);
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException(String.format("Could not encode url %s with encoding %s", s, encoding));
        }
    }
}
