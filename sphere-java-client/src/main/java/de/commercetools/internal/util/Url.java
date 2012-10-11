package de.commercetools.internal.util;

import com.google.common.base.Strings;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Map;

public class Url {
    public static String combine(String baseUrl, String relativeUrl) {
        try {
          return new URL(new URL(baseUrl), relativeUrl).toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
