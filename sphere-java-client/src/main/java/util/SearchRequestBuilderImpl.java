package de.commercetools.sphere.client.util;

import com.google.common.base.*;
import com.google.common.collect.FluentIterable;
import de.commercetools.sphere.client.model.SearchQueryResult;
import de.commercetools.sphere.client.async.ListenableFutureAdapter;
import de.commercetools.sphere.client.BackendException;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.annotation.Nullable;
import java.util.Collection;

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
    
    private static final Joiner joinFilterValues = Joiner.on(',');

    private static final Function<String, String> addQuotes = new Function<String, String>() {
        public String apply(String s) {
            return "\"" + s + "\"";
        }
    };

    private static final Predicate<String> isNotEmpty = new Predicate<String>() {
        public boolean apply(String s) {
            return !Strings.isNullOrEmpty(s);
        }
    };

    /** @inheritdoc */
    public SearchRequestBuilder<T> filter(String path, Collection<String> values) {
        String joined = joinFilterValues.join(FluentIterable.from(values).filter(isNotEmpty).transform(addQuotes));
        requestHolder.addQueryParameter("filter", path + ":" + joined);
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterRange(String path, Integer from, Integer to) {
        if (from == null && to == null) {
            // do nothing
            return this;
        }
        String f = from == null ? "*" : from.toString();
        String t =   to == null ? "*" : to.toString();
        requestHolder.addQueryParameter("filter", path + String.format(":range (%s to %s)", f, t));
        return this;
    }

    /** @inheritdoc */
    public SearchRequestBuilder<T> filterMoneyRange(String path, Integer from, Integer to) {
        Integer f = from == null ? null : from * 100;
        Integer t =   to == null ? null :   to * 100;
        return filterRange(path + ".centAmount", f, t);
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
