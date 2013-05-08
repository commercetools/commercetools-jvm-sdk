package io.sphere.internal.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;

import com.google.common.base.Charsets;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import io.sphere.internal.request.TestableRequestHolder;
import io.sphere.client.SphereException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

public class Util {
    /** Creates a range based on (nullable) bounds. */
    public static <T extends Comparable> Range<T> closedRange(@Nullable T from, @Nullable T to) {
        if (from == null && to == null) return Ranges.<T>all();
        if (from == null && to != null) return Ranges.atMost(to);
        if (from != null && to == null) return Ranges.atLeast(from);
        return Ranges.closed(from, to);
    }

    /** Encodes urls with US-ASCII. */
    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Could not encode url: " + s);
        }
    }

    // ---------------------------
    // Async
    // ---------------------------

    /** Waits for a future, wrapping exceptions as {@link io.sphere.client.SphereException SphereExceptions}. */
    public static <T> T sync(ListenableFuture<T> future) {
        try {
            return future.get();
        } catch(ExecutionException e) {
            throw toSphereException(e.getCause());
        } catch (InterruptedException e) {
            throw new SphereException(e);
        }
    }

    // ---------------------------
    // Exceptions
    // ---------------------------

    public static SphereException toSphereException(Throwable t) {
        if (t instanceof RuntimeException && t.getCause() != null) {
            // Unwrap RuntimeException used by e.g. Netty to avoid checked exceptions
            return mapSphereExpcetion(t.getCause());
        }
        return mapSphereExpcetion(t);
    }

    private static SphereException mapSphereExpcetion(Throwable t) {
        if (t instanceof SphereException) return (SphereException)t;
        return new SphereException(t);
    }

    // ---------------------------
    // Printing and logging
    // ---------------------------

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
            return request.getMethod() + " " + request.getUrl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Pretty prints given JSON string, replacing passwords by {@code 'xxxxx'}. */
    public static String prettyPrintJsonStringSecure(String json) throws IOException {
        ObjectMapper jsonParser = new ObjectMapper();
        JsonNode jsonTree = jsonParser.readValue(json, JsonNode.class);
        secure(jsonTree);
        ObjectWriter writer = jsonParser.writerWithDefaultPrettyPrinter();
        return writer.writeValueAsString(jsonTree);
    }

    /** Very simple way to "erase" passwords -
     *  replaces all field values whose names contains {@code 'pass'} by {@code 'xxxxx'}. */
    private static JsonNode secure(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode)node;
            Iterator<Map.Entry<String, JsonNode>> fields = node.getFields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (field.getValue().isTextual() && field.getKey().toLowerCase().contains("pass")) {
                    objectNode.put(field.getKey(), "xxxxx");
                } else {
                    secure(field.getValue());
                }
            }
            return objectNode;
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode)node;
            Iterator<JsonNode> elements = arrayNode.getElements();
            while (elements.hasNext()) {
                secure(elements.next());
            }
            return arrayNode;
        } else {
            return node;
        }
    }
}
