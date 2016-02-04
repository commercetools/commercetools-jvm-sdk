package io.sphere.sdk.http;

import org.asynchttpclient.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

final class DefaultAsyncHttpClientAdapterImpl implements AsyncHttpClientAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultAsyncHttpClientAdapterImpl.class);
    private final AsyncHttpClient asyncHttpClient;
    private final ForkJoinPool threadPool = new ForkJoinPool();

    private DefaultAsyncHttpClientAdapterImpl(final AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    @Override
    public CompletableFuture<HttpResponse> execute(final HttpRequest httpRequest) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("executing " + httpRequest);
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
            final HttpResponse httpResponse = HttpResponse.of(response.getStatusCode(), responseBodyAsBytes, httpRequest, HttpHeaders.of(response.getHeaders().entries()));
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("response " + httpResponse);
            }
            return httpResponse;
        }, threadPool);
    }

    private byte[] getResponseBodyAsBytes(final Response response) {
        try {
            return response.getResponseBodyAsBytes();
        } catch (final Exception e) {
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
        final Request build = builder.build();
        return build;
    }

    @Override
    public void close() {
        threadPool.shutdown();
        try {
            asyncHttpClient.close();
        } catch (final IOException e) {
            throw new HttpException(e);
        }
    }

    public static DefaultAsyncHttpClientAdapterImpl of(final AsyncHttpClient asyncHttpClient) {
        return new DefaultAsyncHttpClientAdapterImpl(asyncHttpClient);
    }

}