package de.commercetools.sphere.client.util;

import com.google.common.base.Charsets;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

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

    /** Pretty prints given JSON string. */
    public static String prettyPrintJsonString(String json) throws IOException {
        ObjectMapper jsonParser = new ObjectMapper();
        JsonNode jsonTree = jsonParser.readValue(json, JsonNode.class);
        ObjectWriter writer = jsonParser.writerWithDefaultPrettyPrinter();
        return writer.writeValueAsString(jsonTree);
    }
}
