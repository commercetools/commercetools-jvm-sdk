package io.sphere.sdk.http;

import com.ning.http.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

final class DefaultAsyncHttpClientAdapterImpl extends HttpClientAdapterBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);
    private final AsyncHttpClient asyncHttpClient;
    private final ForkJoinPool threadPool = new ForkJoinPool();
    private String userAgent;

    DefaultAsyncHttpClientAdapterImpl(final AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
        userAgent = new AsyncHttpClientConfig.Builder().build().getUserAgent();
    }

    @Override
    protected CompletableFuture<HttpResponse> executeDelegate(final HttpRequest httpRequest) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.debug("executing " + httpRequest);
        } else if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("{} {}", httpRequest.getHttpMethod(), httpRequest.getUrl());
        }
        final Request request = asAhcRequest(httpRequest);
        final CompletableFuture<Response> future = new CompletableFuture<>();
        asyncHttpClient.executeRequest(request, new AsyncCompletionHandler<Response>() {
            @Override
            public Response onCompleted(final Response response) throws Exception {
                    future.complete(response);
                return response;
            }

            @Override
            public void onThrowable(final Throwable t) {
                //nginx does not send status code so this http client explodes
                final boolean maybeUriTooLongErrorFromNgingx = t.getMessage().contains("invalid version format: <HTML>");
                final String message = maybeUriTooLongErrorFromNgingx
                        ? "There is a problem, maybe the request URI was too long due to an inefficient query."
                        : "The underlying HTTP client detected a problem.";
                future.completeExceptionally(new HttpException(message, t));
                super.onThrowable(t);

            }
        });
        return future.thenApplyAsync(response -> {
            final byte[] responseBodyAsBytes = getResponseBodyAsBytes(response);
            final HttpResponse httpResponse = HttpResponse.of(response.getStatusCode(), responseBodyAsBytes, httpRequest, HttpHeaders.of(response.getHeaders()));
            if (LOGGER.isTraceEnabled()) {
                LOGGER.debug("response " + httpResponse);
            }
            return httpResponse;
        }, threadPool);
    }

    private byte[] getResponseBodyAsBytes(final Response response) {
        try {
            return response.getResponseBodyAsBytes();
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    /* package scope for testing */
    <T> Request asAhcRequest(final HttpRequest request) {
        final RequestBuilder builder = new RequestBuilder()
                .setUrl(request.getUrl())
                .setMethod(request.getHttpMethod().toString());

        request.getHeaders().getHeadersAsMap().forEach((name, values) -> values.forEach(value -> builder.addHeader(name, value)));

        Optional.ofNullable(request.getBody()).ifPresent(body -> {
            if (body instanceof StringHttpRequestBody) {
                final String bodyAsString = ((StringHttpRequestBody) body).getString();
                builder.setBodyEncoding(StandardCharsets.UTF_8.name()).setBody(bodyAsString);
            } else if (body instanceof FileHttpRequestBody) {
                builder.setBody(((FileHttpRequestBody) body).getFile());
            } else if (body instanceof FormUrlEncodedHttpRequestBody) {
                final FormUrlEncodedHttpRequestBody formUrlEncodedHttpRequestBody = (FormUrlEncodedHttpRequestBody) body;
                formUrlEncodedHttpRequestBody.getParameters().forEach(pair -> builder.addFormParam(pair.getName(), pair.getValue()));
            }
        });
        final Request build = builder.build();
        return build;
    }

    @Override
    protected void closeDelegate() {
        asyncHttpClient.close();
    }

    @Nullable
    @Override
    public String getUserAgent() {
        return userAgent;
    }
}