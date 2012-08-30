package de.commercetools.sphere.client.util;

import de.commercetools.sphere.client.async.ListenableFutureAdapter;
import de.commercetools.sphere.client.BackendException;
import de.commercetools.sphere.client.util.Log;
import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;

public class SearchRequestBuilderImpl<T> implements SearchRequestBuilder<T> {
    
    private String fullTextQuery;
    AsyncHttpClient.BoundRequestBuilder httpRequestBuilder;
    private TypeReference<T> jsonParserTypeRef;

    public SearchRequestBuilderImpl(
            String fullTextQuery, AsyncHttpClient.BoundRequestBuilder httpRequestBuilder, TypeReference<T> jsonParserTypeRef) {
        this.fullTextQuery = fullTextQuery;
        this.httpRequestBuilder = httpRequestBuilder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    /** @inheritdoc */
    public T fetch() throws BackendException {
        try {
            return fetchAsync().get();
        } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

    /** @inheritdoc */
    public ListenableFuture<T> fetchAsync() throws BackendException {
        try {
            httpRequestBuilder.addQueryParameter("text", fullTextQuery);
            if (Log.isTraceEnabled()) {
                Log.trace(httpRequestBuilder.build().getRawUrl());
            }
            return new ListenableFutureAdapter<T>(httpRequestBuilder.execute(new AsyncCompletionHandler<T>() {
                @Override
                public T onCompleted(Response response) throws Exception {
                    if (response.getStatusCode() != 200) {
                        String message = String.format(
                                "The backend returned an error response: %s\n[%s]\n%s",
                                httpRequestBuilder.build().getRawUrl(),
                                response.getStatusCode(),
                                response.getResponseBody(Charsets.UTF_8.name())
                        );
                        Log.error(message);
                        throw new BackendException(message);
                    } else {
                        Log.warn(response.getResponseBody(Charsets.UTF_8.name()));
                        ObjectMapper jsonParser = new ObjectMapper();
                        T parsed = jsonParser.readValue(response.getResponseBody(Charsets.UTF_8.name()), jsonParserTypeRef);
                        if (Log.isTraceEnabled()) {
                            // Log pretty printed json response
                            ObjectWriter writer = jsonParser.writerWithDefaultPrettyPrinter();
                            Log.trace("\n" + writer.writeValueAsString(parsed));
                        }
                        return parsed;
                    }
                }
            }));
        } catch (Exception e) {
            throw new BackendException(e);
        }
    }
}
