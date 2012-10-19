package de.commercetools.internal.util;

import com.google.common.base.Charsets;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import javax.annotation.Nullable;
import java.io.IOException;

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

    /** Pretty prints given JSON string. */
    public static String prettyPrintJsonString(String json) throws IOException {
        ObjectMapper jsonParser = new ObjectMapper();
        JsonNode jsonTree = jsonParser.readValue(json, JsonNode.class);
        ObjectWriter writer = jsonParser.writerWithDefaultPrettyPrinter();
        return writer.writeValueAsString(jsonTree);
    }
}
