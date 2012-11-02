package de.commercetools.internal.request;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import de.commercetools.internal.util.Log;
import de.commercetools.internal.util.Util;
import de.commercetools.sphere.client.ConflictException;
import de.commercetools.sphere.client.SphereBackendException;
import de.commercetools.sphere.client.SphereException;
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
                    String body = response.getResponseBody(Charsets.UTF_8.name());
                    int status = response.getStatusCode();
                    if (status / 100 != 2) {
                        Exception e;
                        switch (status) {
                            case 404: {
                                Log.warn("404 Not found: " + requestHolderToString(requestHolder));
                                return null;
                            }
                            case 409: e = new ConflictException(requestHolder.getRawUrl(), body); break;
                            default: e = new SphereBackendException(status, requestHolder.getRawUrl(), body); break;
                        }
                        Log.error(e.getMessage() + "\n\nRequest: " + requestHolderToString(requestHolder));
                        throw e;
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
                   requestHolder.getRawUrl() +
                   (Strings.isNullOrEmpty(requestHolder.getBody()) ?
                           "" :
                           "\n" + Util.prettyPrintJsonString(requestHolder.getBody())) +
                   "\n";
        } catch(IOException e) {
            throw new SphereException(e);
        }
    }

    private static String jsonResponseToString(Response response) {
        try {
            return Util.prettyPrintJsonString(response.getResponseBody(Charsets.UTF_8.name()));
        } catch(IOException e) {
            throw new SphereException(e);
        }
    }
}
