package io.sphere.sdk.http;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
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
        try {
            return URLEncoder.encode(s, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Could not encode url: " + s);
        }
    }

    public static String combine(String baseUrl, String relativeUrl) {
        try {
            return new URL(new URL(baseUrl), relativeUrl).toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);//TODO
        }
    }
}
