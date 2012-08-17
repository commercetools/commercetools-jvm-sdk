package sphere;

import com.google.common.base.Charsets;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.Response;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import de.commercetools.sphere.client.async.ListenableFutureAdapter;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/** @inheritdoc */
class RequestBuilderImpl<T> extends AbstractRequestBuilder<T> {

    protected AsyncHttpClient.BoundRequestBuilder httpRequestBuilder;

    protected RequestBuilderImpl(AsyncHttpClient.BoundRequestBuilder httpRequestBuilder, TypeReference<T> jsonParserTypeRef) {
        super(jsonParserTypeRef);
        this.httpRequestBuilder = httpRequestBuilder;
    }

    @Override
    protected com.ning.http.client.ListenableFuture<T> executeRequest(AsyncCompletionHandler<T> onResponse) throws Exception {
        // make a request to the server
        return this.httpRequestBuilder.execute(onResponse);
    }

    @Override
    protected String getRawRequestUrl() {
        return this.httpRequestBuilder.build().getRawUrl();
    }

    /** @inheritdoc */
    public RequestBuilder<T> expand(String... paths) {
        for (String path: paths) {
            this.httpRequestBuilder.addQueryParameter("expand", path);
        }
        return this;
    }
}
