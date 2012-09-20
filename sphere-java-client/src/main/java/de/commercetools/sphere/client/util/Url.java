package de.commercetools.sphere.client.util;

import java.net.URL;
import java.net.MalformedURLException;

public class Url {
    public static String combine(String baseUrl, String relativeUrl) {
        try {
          return new URL(new URL(baseUrl), relativeUrl).toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
