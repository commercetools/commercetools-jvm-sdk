package io.sphere.sdk.http;

import com.ning.http.client.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.zip.GZIPInputStream;

final class AsyncHttpClientAdapterImpl extends HttpClientAdapterBase {
    private final AsyncHttpClient asyncHttpClient;
    private String userAgent;

    AsyncHttpClientAdapterImpl(final AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
        userAgent = new AsyncHttpClientConfig.Builder().build().getUserAgent();
    }

    @Override
    protected CompletionStage<HttpResponse> executeDelegate(final HttpRequest httpRequest) {
        final Request request = asAhcRequest(httpRequest);
        try {
            final CompletionStage<Response> future = wrap(asyncHttpClient.executeRequest(request));
            return future.thenApplyAsync(response -> convert(httpRequest, response), threadPool());
        } catch (final IOException e) {
            final CompletableFuture<HttpResponse> future = new CompletableFuture<>();
            future.completeExceptionally(new HttpException(e));
            return future;
        }
    }

    private HttpResponse convert(final HttpRequest httpRequest, final Response response) {
        final byte[] responseBodyAsBytes = getResponseBodyAsBytes(response);
        return HttpResponse.of(response.getStatusCode(), responseBodyAsBytes, httpRequest, HttpHeaders.of(response.getHeaders()));
    }

    private byte[] getResponseBodyAsBytes(final Response response) {
        try {
            final boolean gzipEncoded =
                    Optional.ofNullable(response.getHeader(HttpHeaders.CONTENT_ENCODING))
                            .map(v -> v.equalsIgnoreCase("gzip"))
                            .orElse(false);
            return gzipEncoded ? unzip(response) : response.getResponseBodyAsBytes();
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }

    private byte[] unzip(final Response response) throws IOException {
        final InputStream autoCloseInputStream = new AutoCloseInputStream(new GZIPInputStream(response.getResponseBodyAsStream()));
        return IOUtils.toByteArray(autoCloseInputStream);
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
                builder.setBodyEncoding(StandardCharsets.UTF_8.name()).setBody(bodyAsString);
            } else if (body instanceof FileHttpRequestBody) {
                final File file = ((FileHttpRequestBody) body).getFile();
                builder.setBody(out -> FileUtils.copyFile(file, out));
                final long length = file.length();
                builder.addHeader(HttpHeaders.CONTENT_LENGTH, "" + length);
            } else if (body instanceof FormUrlEncodedHttpRequestBody) {
                final FormUrlEncodedHttpRequestBody formUrlEncodedHttpRequestBody = (FormUrlEncodedHttpRequestBody) body;
                formUrlEncodedHttpRequestBody.getParameters().forEach(pair -> builder.addParameter(pair.getName(), pair.getValue()));
            }
        });
        final Request ahcRequest = builder.build();
        return ahcRequest;
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

    /**
     * Creates a {@link CompletionStage} from a {@link ListenableFuture}.
     * @param listenableFuture the future of the ning library
     * @param executor the executor to run the future in
     * @param <T> Type of the value that will be returned.
     * @return the Java 8 future implementation
     */
    private static <T> CompletionStage<T> wrap(final ListenableFuture<T> listenableFuture, final Executor executor) {
        final CompletableFuture<T> result = new CompletableFuture<>();
        final Runnable listener = () -> {
            try {
                final T value = listenableFuture.get();
                result.complete(value);
            } catch (final InterruptedException | ExecutionException e) {
                result.completeExceptionally(e.getCause());
            }
        };
        listenableFuture.addListener(listener, executor);
        return result;
    }

    private CompletionStage<Response> wrap(final ListenableFuture<Response> listenableFuture) {
        return wrap(listenableFuture, threadPool());
    }
}