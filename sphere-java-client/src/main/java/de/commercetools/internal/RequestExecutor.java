package de.commercetools.internal;

import com.google.common.base.Strings;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.util.Log;
import de.commercetools.sphere.client.util.Util;
import de.commercetools.sphere.client.ConflictException;
import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.Response;
import com.ning.http.client.AsyncCompletionHandler;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

public class RequestExecutor {
    /** Executes request and parses JSON response as given type. */
    public static <T> ListenableFuture<T> execute(final RequestHolder<T> requestHolder, final TypeReference<T> jsonParserTypeRef) {
        try {
            return requestHolder.executeRequest(new AsyncCompletionHandler<T>() {
                @Override
                public T onCompleted(Response response) throws Exception {
                    if (response.getStatusCode() / 100 != 2) {
                        String message = String.format(
                                "Response status %s from Sphere: %s\n%s",
                                response.getStatusCode(),
                                requestHolder.getRawUrl(),
                                response.getResponseBody(Charsets.UTF_8.name())
                        );
                        Log.error(message + "\n\nRequest: " + requestHolderToString(requestHolder));
                        if (response.getStatusCode() == 409) {
                            throw new ConflictException(message);
                        }
                        throw new SphereException(message);
                    } else {
                        if (Log.isTraceEnabled()) {
                            Log.trace(requestHolderToString(requestHolder) + jsonResponseToString(response));
                        }
                        ObjectMapper jsonParser = new ObjectMapper();
                        T parsed = jsonParser.readValue(response.getResponseBody(Charsets.UTF_8.name()), jsonParserTypeRef);
                        return parsed;
                    }
                }
            });
        } catch (Exception e) {
            throw new SphereException(e);
        }
    }

    private static <T> String requestHolderToString(RequestHolder<T> requestHolder) {
        try {
            return requestHolder.getMethod() + " " +
                   requestHolder.getRawUrl() + "\n" +
                   (Strings.isNullOrEmpty(requestHolder.getBody()) ?
                           "" :
                           Util.prettyPrintJsonString(requestHolder.getBody())) +
                   "\n\n";
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String jsonResponseToString(Response response) {
        try {
            return Util.prettyPrintJsonString(response.getResponseBody(Charsets.UTF_8.name()));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}
