package io.sphere.sdk.client;


import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import com.typesafe.config.Config;
import io.sphere.sdk.requests.HttpRequest;
import io.sphere.sdk.requests.HttpResponse;
import io.sphere.sdk.requests.Requestable;
import io.sphere.sdk.utils.Log;
import io.sphere.sdk.meta.BuildInfo;

import java.io.IOException;

class NingAsyncHttpClient implements HttpClient {

    private final ClientCredentials clientCredentials;
    private final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private final String coreUrl;
    private final String projectKey;

    public NingAsyncHttpClient(final Config config) {
        clientCredentials = SphereClientCredentials.createAndBeginRefreshInBackground(config, new OAuthClient(asyncHttpClient));
        coreUrl = config.getString("sphere.core");
        projectKey = config.getString("sphere.project");
    }

    @Override
    public <T> ListenableFuture<HttpResponse> execute(final Requestable requestable) {
        final Request request = asRequest(requestable);
//        Log.error("request " + request.toString()); TODO do not log bearer
        try {
            final ListenableFutureAdapter<Response> future = new ListenableFutureAdapter<>(asyncHttpClient.executeRequest(request));
            return Futures.transform(future, (Response response) -> {
                try {
                    return new HttpResponse(response.getStatusCode(), response.getResponseBody(Charsets.UTF_8.name()));
                } catch (IOException e) {
                    Log.error(requestable.toString() + "\n" + request, e);
                    throw new RuntimeException(e);//TODO unify exception handling, to sphere exception
                }
            });
        } catch (final IOException e) {
            throw new RuntimeException(e);//TODO unify exception handling, to sphere exception
        }
    }

    /* package scope for testing */
    <T> Request asRequest(final Requestable requestable) {
        final HttpRequest request = requestable.httpRequest();
        final RequestBuilder builder = new RequestBuilder().
                setUrl(CharMatcher.is('/').trimTrailingFrom(coreUrl) + "/" + projectKey + request.getPath()).
                setMethod(request.getHttpMethod().toString()).
                setHeader("User-Agent", "SPHERE.IO JVM SDK version " + BuildInfo.version()).
                setHeader("Authorization", "Bearer " + clientCredentials.getAccessToken());
        return request.getBody().transform(builder::setBody).or(builder).build();
    }

    @Override
    public void close() {
        asyncHttpClient.close();
    }
}
