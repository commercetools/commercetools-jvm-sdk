package io.sphere.sdk.client;


import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.*;
import com.ning.http.client.Request;
import com.typesafe.config.Config;

import java.io.IOException;

class NingAsyncHttpClientHttpClient implements HttpClient {

    private final ClientCredentials clientCredentials;
    private final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    public NingAsyncHttpClientHttpClient(final Config config) {
        clientCredentials = SphereClientCredentials.createAndBeginRefreshInBackground(config, new OAuthClient(asyncHttpClient));
    }

    @Override
    public <T> ListenableFuture<HttpResponse> execute(final Requestable<T> requestable) {
        final Request request = asRequest(requestable);
        try {
            final ListenableFutureAdapter<Response> future = new ListenableFutureAdapter<Response>(asyncHttpClient.executeRequest(request));
            return Futures.transform(future, new Function<Response, HttpResponse>() {
                @Override
                public HttpResponse apply(final Response response) {
                    try {
                        return new HttpResponse(response.getStatusCode(), response.getResponseBody(Charsets.UTF_8.name()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);//TODO unify exception handling, to sphere exception
                    }
                }
            });
        } catch (final IOException e) {
            throw new RuntimeException(e);//TODO unify exception handling, to sphere exception
        }
    }

    private <T> Request asRequest(final Requestable<T> requestable) {
        final HttpRequest request = requestable.httpRequest();
        final RequestBuilder builder = new RequestBuilder().setUrl(request.getUrl()).setMethod(request.getHttpMethod().toString()).
                setHeader("Authorization", "Bearer " + clientCredentials.getAccessToken());
        return builder.build();
    }

}
