package io.sphere.internal.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import io.sphere.client.SphereClientException;
import io.sphere.client.SphereError;
import io.sphere.client.SphereResult;
import io.sphere.client.exceptions.SphereBackendException;
import io.sphere.internal.request.TestableRequestHolder;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

public class Util {
    /** Returns empty string if given null, original string otherwise. */
    public static String emptyIfNull(String s) {
        return s == null ? "" : s;
    }

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

    /** Blocks, wraps exceptions as {@link io.sphere.client.SphereClientException SphereExceptions}. */
    public static <T> T sync(ListenableFuture<T> future) {
        try {
            return future.get();
        } catch(ExecutionException e) {
            // Future.get() catches any user exception and rethrows it as ExecutionException
            throw toSphereException(e);
        } catch (InterruptedException e) {
            throw toSphereException(e);
        }
    }

    // ---------------------------
    // Exceptions
    // ---------------------------

    public static SphereClientException toSphereException(Throwable t) {
        if (t instanceof RuntimeException && t.getCause() != null) {
            // Unwrap RuntimeException used by e.g. Netty to avoid checked exceptions
            return mapSphereException(t.getCause());
        }
        if (t instanceof ExecutionException && t.getCause() != null) {
            // ExecutionException often contains the real exception inside it
            return mapSphereException(t.getCause());
        }
        return mapSphereException(t);
    }

    private static SphereClientException mapSphereException(Throwable t) {
        if (t instanceof SphereClientException) return (SphereClientException)t;
        return new SphereClientException(t);
    }

    // ---------------------------
    // API Errors
    // ---------------------------

    /** Blocks, extracts success value, converts Sphere errors to exceptions. */
    public static <T> T syncResult(ListenableFuture<SphereResult<T>> future) {
        SphereResult<T> result = sync(future); // If the future itself failed, wrap the Exception as SphereException
        return getValueOrThrow(result);
    }

    /** If successful, gets the value. Otherwise throws the most specific error. */
    public static <T> T getValueOrThrow(SphereResult<T> result) {
        if (result.isError()) {
            if (result.getSpecificErrorInternal().isPresent()) {
                throw result.getSpecificErrorInternal().get();
            } else {
                throw result.getGenericError();
            }
        }
        return result.getValue();
    }

    /** If the exception contains exactly one error and it is of given type, returns it. Otherwise returns null. */
    public static <T extends SphereError> T getSingleError(SphereBackendException e, Class<T> errorClass) {
        if (e.getErrors().size() != 1) return null;
        SphereError firstError = e.getErrors().get(0);
        if (!errorClass.isInstance(firstError)) return null;
        return errorClass.cast(firstError);
    }

    /** If the exception contains an one error of given type, returns it. Otherwise returns null. */
    public static <T extends SphereError> T getError(SphereBackendException e, Class<T> errorClass) {
        for (SphereError error: e.getErrors()) {
            if (errorClass.isInstance(error)) return errorClass.cast(error);
        }
        return null;
    }

    // ---------------------------
    // Printing and logging
    // ---------------------------

    /** Serializes request, usually for logging or debugging purposes. */
    public static String requestToString(Request request) {
        return request.getMethod() + " " + getDecodedUrl(request);
    }

    /** Gets full decoded URL including query string. */
    public static String getDecodedUrl(Request request) {
        String encoded = request.getUrl(); // getRawUrl() broken
        try {
            return URLDecoder.decode(encoded, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return encoded;
        }
    }

    /** Serializes request and response, usually for logging or debugging purposes. */
    public static String requestResponseToString(Request request, Response response) {
        try {
            return requestToString(request) + " :\n" +
                    response.getStatusCode() + " " + response.getResponseBody(Charsets.UTF_8.name());
        } catch (IOException e) {
            throw toSphereException(e);
        }
    }

    /** Serializes a TestableRequestHolder, usually for logging or debugging purposes. */
    public static String debugPrintRequestHolder(TestableRequestHolder request) {
        return request.getMethod() + " " + request.getUrl();
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
