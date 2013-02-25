package de.commercetools.internal.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.annotation.Nullable;

import com.google.common.base.Charsets;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import de.commercetools.internal.request.TestableRequestHolder;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class Util {
    /** Creates a range based on (nullable) bounds. */
    public static <T extends Comparable> Range<T> closedRange(@Nullable T from, @Nullable T to) {
        if (from == null && to == null) return Ranges.<T>all();
        if (from == null && to != null) return Ranges.atMost(to);
        if (from != null && to == null) return Ranges.atLeast(from);
        return Ranges.closed(from, to);
    }

    /** Serializes request, usually for logging or debugging purposes. */
    public static String requestToString(Request request) {
        try {
            return request.getMethod() + " " + request.getRawUrl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Serializes request and response, usually for logging or debugging purposes. */
    public static String requestResponseToString(Request request, Response response) {
        try {
            return requestToString(request) + " :\n" +
                    response.getStatusCode() + " " + response.getResponseBody(Charsets.UTF_8.name());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Serializes a TestableRequestHolder, usually for logging or debugging purposes. */
    public static String debugPrintRequestHolder(TestableRequestHolder request) {
        try {
            return request.getMethod() + " " + request.getFullUrl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Pretty prints given JSON string. */
    public static String prettyPrintJsonString(String json) throws IOException {
        ObjectMapper jsonParser = new ObjectMapper();
        JsonNode jsonTree = jsonParser.readValue(json, JsonNode.class);
        ObjectWriter writer = jsonParser.writerWithDefaultPrettyPrinter();
        return writer.writeValueAsString(jsonTree);
    }

    /** Pretty prints a java object as JSON string. */
    public static String prettyPrintObjectAsJsonString(Object value) throws IOException {
        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        return writer.writeValueAsString(value);
    }

    /** Encodes urls with US-ASCII. */
    public static String urlEncode(String s) {
        //TODO verify the error handling
        try {
            return URLEncoder.encode(s, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new Error("Could not encode url.", e);
        }
    }
}
