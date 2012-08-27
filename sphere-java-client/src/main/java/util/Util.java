package de.commercetools.sphere.client.util;

import com.google.common.base.Charsets;
import com.ning.http.client.Request;
import com.ning.http.client.Response;

public class Util {
    /** Serializes request and response, usually for logging or debugging purposes. */
    public static String requestResponseToString(Request request, Response response) {
        try {
          return request.getMethod() + " " + request.getRawUrl() + " :\n" +
                 response.getStatusCode() + " " + response.getResponseBody(Charsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
