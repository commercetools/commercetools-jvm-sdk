package io.sphere.sdk.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.CompletionException;

@Deprecated
public final class UrlUtils {
    private UrlUtils() {
    }

    /**
     * Encodes urls with UTF-8.
     * @param s String which should be URL encoded
     * @return url encoded s
     */
    @Deprecated
    public static String urlEncode(final String s) {
        final String encoding = "UTF-8";
        try {
            return URLEncoder.encode(s, encoding);
        } catch (final UnsupportedEncodingException e) {
            throw new CompletionException(String.format("Could not encode url %s with encoding %s", s, encoding), e);
        }
    }
}
