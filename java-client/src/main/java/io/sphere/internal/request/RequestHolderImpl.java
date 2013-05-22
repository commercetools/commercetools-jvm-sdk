package io.sphere.internal.request;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.FluentStringsMap;
import io.sphere.client.SphereResult;
import io.sphere.internal.ListenableFutureAdapter;
import io.sphere.internal.Version;

import java.util.List;
import java.util.Map;

/** A request holder that does real HTTP requests. Can be mocked in tests. */
public class RequestHolderImpl<T> implements RequestHolder<T> {
    private final AsyncHttpClient.BoundRequestBuilder httpRequestBuilder;

    public RequestHolderImpl(AsyncHttpClient.BoundRequestBuilder httpRequestBuilder) {
        this.httpRequestBuilder = httpRequestBuilder;
        this.httpRequestBuilder.setHeader("User-Agent", "Sphere Java client, version " + Version.version);
    }

    public RequestHolderImpl<T> addQueryParameter(String name, String value) {
        httpRequestBuilder.addQueryParameter(name, value);
        return this;
    }

    public RequestHolderImpl<T> setBody(String requestBody) {
        httpRequestBuilder.setBody(requestBody);
        return this;
    }

    public ListenableFuture<SphereResult<T>> executeRequest(AsyncCompletionHandler<SphereResult<T>> onResponse) throws Exception {
        return new ListenableFutureAdapter<SphereResult<T>>(httpRequestBuilder.execute(onResponse));
    }

    /** The URL the request will be sent to, for debugging purposes. */
    public String getUrl() {
        return httpRequestBuilder.build().getRawUrl();
    }

    /** The HTTP method of the request, for debugging purposes. */
    public String getMethod() {
        return httpRequestBuilder.build().getMethod();
    }

    /** The body of the request, for debugging purposes. */
    public String getBody() {
        return httpRequestBuilder.build().getStringData();
    }

    /** The query parameters of the request, for debugging purposes. */
    public Multimap<String, String> getQueryParams() {
        FluentStringsMap params = httpRequestBuilder.build().getQueryParams();
        Multimap<String, String> converted = HashMultimap.create();
        for (Map.Entry<String, List<String>> param: params) {
            converted.putAll(param.getKey(), param.getValue());
        }
        return converted;
    }
}
