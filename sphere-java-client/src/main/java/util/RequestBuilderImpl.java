package de.commercetools.sphere.client.util;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncCompletionHandler;
import org.codehaus.jackson.type.TypeReference;

/** @inheritdoc */
public class RequestBuilderImpl<T> extends AbstractRequestBuilder<T> {

    protected AsyncHttpClient.BoundRequestBuilder httpRequestBuilder;

    public RequestBuilderImpl(AsyncHttpClient.BoundRequestBuilder httpRequestBuilder, TypeReference<T> jsonParserTypeRef) {
        super(jsonParserTypeRef);
        this.httpRequestBuilder = httpRequestBuilder;
    }

    @Override
    protected com.ning.http.client.ListenableFuture<T> executeRequest(AsyncCompletionHandler<T> onResponse) throws Exception {
        // make a request to the server
        return httpRequestBuilder.execute(onResponse);
    }

    @Override
    protected String getRawRequestUrl() {
        return httpRequestBuilder.build().getRawUrl();
    }

    /** @inheritdoc */
    public RequestBuilder<T> expand(String... paths) {
        for (String path: paths) {
            httpRequestBuilder.addQueryParameter("expand", path);
        }
        return this;
    }
}
