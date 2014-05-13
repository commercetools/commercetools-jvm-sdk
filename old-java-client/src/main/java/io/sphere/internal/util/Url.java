package io.sphere.internal.util;

import java.net.URL;
import java.net.MalformedURLException;

public class Url {
    /** Given "http://foo/" and "/bar" returns "http://foo/bar". */
    public static String combine(String baseUrl, String relativeUrl) {
        try {
          return new URL(new URL(baseUrl), relativeUrl).toString();
        } catch (MalformedURLException e) {
            throw Util.toSphereException(e);
        }
    }

    /** Given "http://foo/bar.jpg" returns ".jpg". Assumes no query parameters. */
    public static String getExtension(String url) {
        int i = url.lastIndexOf(".");
        return i == -1 ? "" : url.substring(i);
    }

    /** Given "http://foo/bar.jpg" returns "http://foo/bar". Assumes no query parameters. */
    public static String stripExtension(String url) {
        int i = url.lastIndexOf(".");
        return i == -1 ? url : url.substring(0, i);
    }
}
