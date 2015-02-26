package io.sphere.sdk.client;

import io.sphere.sdk.http.*;
import io.sphere.sdk.utils.functional.FunctionalUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

final class ApacheHttpClientAdapterImpl extends AutoCloseableService implements HttpClient {
    private final CloseableHttpAsyncClient apacheHttpClient;

    private ApacheHttpClientAdapterImpl(final CloseableHttpAsyncClient apacheHttpClient) {
        this.apacheHttpClient = apacheHttpClient;
        if (!apacheHttpClient.isRunning()) {
            apacheHttpClient.start();
        }
    }

    public static HttpClient of(final CloseableHttpAsyncClient client) {
        return new ApacheHttpClientAdapterImpl(client);
    }

    @Override
    protected void internalClose() {
        closeQuietly(apacheHttpClient);
    }

    @Override
    public CompletableFuture<HttpResponse> execute(final HttpRequest httpRequest) {
        final HttpUriRequest realHttpRequest = toApacheRequest(httpRequest);
        final CompletableFuture<org.apache.http.HttpResponse> apacheResponseFuture = new CompletableFuture<>();
        apacheHttpClient.execute(realHttpRequest, new CompletableFutureCallbackAdapter<>(apacheResponseFuture));
        return apacheResponseFuture.thenApply(apacheResponse -> convertApacheToSphereResponse(apacheResponse, httpRequest));
    }

    private HttpResponse convertApacheToSphereResponse(final org.apache.http.HttpResponse apacheResponse, final HttpRequest httpRequest) {
        final Optional<byte[]> bodyOption = Optional.ofNullable(apacheResponse.getEntity())
                .map(entity -> {
                    try {
                        return IOUtils.toByteArray(entity.getContent());
                    } catch (final IOException e) {
                        throw new HttpException(e);
                    }
                });
        final int statusCode = apacheResponse.getStatusLine().getStatusCode();
        return HttpResponse.of(statusCode, bodyOption, Optional.of(httpRequest));
    }

    private HttpUriRequest toApacheRequest(final HttpRequest httpRequest) {
        final String method = httpRequest.getHttpMethod().toString();
        final String uri = httpRequest.getUrl();
        final RequestBuilder builder = RequestBuilder
                .create(method)
                .setUri(uri);
        httpRequest.getHeaders().getHeadersAsMap().forEach((name, value) -> builder.addHeader(name, value));

        if (httpRequest.getBody().isPresent()) {
            final HttpRequestBody body = httpRequest.getBody().get();
            final HttpEntity httpEntity = FunctionalUtils.<HttpEntity>patternMatching(body)
                    .when(StringHttpRequestBody.class, b -> stringEntityOf(b.getString()))
                    .when(FileHttpRequestBody.class, b -> new FileEntity(b.getFile()))
                    .when(FormUrlEncodedHttpRequestBody.class, b -> urlEncodedOf(b))
                    .toOption().orElseThrow(() -> new HttpException("Cannot interpret request " + httpRequest));
            builder.setEntity(httpEntity);
        }
        return builder.build();
    }

    private static HttpEntity urlEncodedOf(final FormUrlEncodedHttpRequestBody body) {
        final List<BasicNameValuePair> values = body.getData().entrySet()
                .stream()
                .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        try {
            return new UrlEncodedFormEntity(values);
        } catch (final UnsupportedEncodingException e) {
            throw new HttpException(e);
        }
    }

    private static HttpEntity stringEntityOf(final String body) {
        try {
            return new StringEntity(body);
        } catch (final UnsupportedEncodingException e) {
            throw new HttpException(e);
        }
    }
}
