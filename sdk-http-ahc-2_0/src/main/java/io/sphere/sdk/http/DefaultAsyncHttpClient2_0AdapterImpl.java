package io.sphere.sdk.http;

import org.asynchttpclient.*;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

final class DefaultAsyncHttpClient2_0AdapterImpl extends HttpClientAdapterBase {
    private final AsyncHttpClient asyncHttpClient;
    private final String userAgent;

    DefaultAsyncHttpClient2_0AdapterImpl(final AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
        userAgent = new DefaultAsyncHttpClientConfig.Builder().build().getUserAgent();
    }

    @Override
    protected CompletionStage<HttpResponse> executeDelegate(final HttpRequest httpRequest) {
        final Request request = asAhcRequest(httpRequest);
        final CompletableFuture<Response> future = new CompletableFuture<>();
        asyncHttpClient.executeRequest(request, new ResponseAsyncCompletionHandler(future));
        return future.thenApplyAsync(response -> convert(httpRequest, response), threadPool());
    }

    private HttpResponse convert(final HttpRequest httpRequest, final Response response) {
        final byte[] responseBodyAsBytes = getResponseBodyAsBytes(response);
        final int statusCode = response.getStatusCode();
        final HttpHeaders headers = HttpHeaders.ofMapEntryList(response.getHeaders().entries());
        return HttpResponse.of(statusCode, responseBodyAsBytes, httpRequest, headers);
    }

    private byte[] getResponseBodyAsBytes(final Response response) {
        return response.getResponseBodyAsBytes();
    }

    /* package scope for testing */
    Request asAhcRequest(final HttpRequest request) {
        final RequestBuilder builder = new RequestBuilder()
                .setUrl(request.getUrl())
                .setMethod(request.getHttpMethod().toString());

        request.getHeaders().getHeadersAsMap().forEach((name, values) -> values.forEach(value -> builder.addHeader(name, value)));

        Optional.ofNullable(request.getBody()).ifPresent(body -> {
            if (body instanceof StringHttpRequestBody) {
                final String bodyAsString = ((StringHttpRequestBody) body).getString();
                builder.setBody(bodyAsString);
                if (!request.getHeaders().findFlatHeader(HttpHeaders.CONTENT_TYPE).isPresent()) {
                    builder.addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
                }
            } else if (body instanceof FileHttpRequestBody) {
                builder.setBody(((FileHttpRequestBody) body).getFile());
            } else if (body instanceof FormUrlEncodedHttpRequestBody) {
                final FormUrlEncodedHttpRequestBody formUrlEncodedHttpRequestBody = (FormUrlEncodedHttpRequestBody) body;
                formUrlEncodedHttpRequestBody.getParameters().forEach(pair -> builder.addFormParam(pair.getName(), pair.getValue()));
            }
        });
        return builder.build();
    }

    @Override
    protected void closeDelegate() throws Throwable {
        asyncHttpClient.close();
    }

    @Nullable
    @Override
    public String getUserAgent() {
        return userAgent;
    }
}