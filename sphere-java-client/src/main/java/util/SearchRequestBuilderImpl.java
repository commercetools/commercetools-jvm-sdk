package de.commercetools.sphere.client.util;

import de.commercetools.sphere.client.model.SearchQueryResult;
import de.commercetools.sphere.client.async.ListenableFutureAdapter;
import de.commercetools.sphere.client.BackendException;
import de.commercetools.sphere.client.util.Log;
import de.commercetools.sphere.client.util.Util;
import com.google.common.base.Strings;
import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;

public class SearchRequestBuilderImpl<T> implements SearchRequestBuilder<T> {
    
    private String fullTextQuery;
    private RequestHolder<SearchQueryResult<T>> requestHolder;
    private TypeReference<SearchQueryResult<T>> jsonParserTypeRef;

    public SearchRequestBuilderImpl(
            String fullTextQuery, RequestHolder<SearchQueryResult<T>> requestHolder, TypeReference<SearchQueryResult<T>> jsonParserTypeRef) {
        this.fullTextQuery = fullTextQuery;
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> limit(int limit) {
        requestHolder.addQueryParameter("limit", Integer.toString(limit));
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> offset(int offset) {
        requestHolder.addQueryParameter("offset", Integer.toString(offset));
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilderImpl<T> expand(String... paths) {
        for (String path: paths) {
            requestHolder.addQueryParameter("expand", path);
        }
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filter(String path, String value) {
        requestHolder.addQueryParameter("filter", path + ":" + "\"" + value + "\"");
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filter(String path, double value) {
        requestHolder.addQueryParameter("filter", path + ":" + value);
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filter(String path, int value) {
        requestHolder.addQueryParameter("filter", path + ":" + value);
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> facet(String path) {
        requestHolder.addQueryParameter("facet", path);
        return this;
    }

    /** @inheritdoc */
    public SearchQueryResult<T> fetch() throws BackendException {
        try {
            return fetchAsync().get();
        } catch(Exception ex) {
            throw new BackendException(ex);
        }
    }

    /** @inheritdoc */
    public ListenableFuture<SearchQueryResult<T>> fetchAsync() throws BackendException {
        try {
            if (!Strings.isNullOrEmpty(fullTextQuery)) {
                requestHolder.addQueryParameter("text", fullTextQuery);
            }
            if (Log.isTraceEnabled()) {
                Log.trace(requestHolder.getRawUrl());
            }
            return new ListenableFutureAdapter<SearchQueryResult<T>>(requestHolder.executeRequest(new AsyncCompletionHandler<SearchQueryResult<T>>() {
                @Override
                public SearchQueryResult<T> onCompleted(Response response) throws Exception {
                    if (response.getStatusCode() != 200) {
                        String message = String.format(
                                "The backend returned an error response: %s\n[%s]\n%s",
                                requestHolder.getRawUrl(),
                                response.getStatusCode(),
                                response.getResponseBody(Charsets.UTF_8.name())
                        );
                        Log.error(message);
                        throw new BackendException(message);
                    } else {
                        ObjectMapper jsonParser = new ObjectMapper();
                        SearchQueryResult<T> parsed = jsonParser.readValue(response.getResponseBody(Charsets.UTF_8.name()), jsonParserTypeRef);
                        if (Log.isTraceEnabled()) {
                            Log.trace(Util.prettyPrintJsonString(response.getResponseBody(Charsets.UTF_8.name())));
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
