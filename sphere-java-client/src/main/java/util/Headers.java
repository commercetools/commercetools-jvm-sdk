package de.commercetools.sphere.client.util;

/** HTTP utils. */
public class Headers {
    /** Encodes username and password as a Base64 value suitable for the "Authorization: Basic" HTTP header. */
    public static String encodeBasicAuthHeader(String username, String password) {
       return "Basic " + Base64.encode(username + ":" + password);
    }
}
