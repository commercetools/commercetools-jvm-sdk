package io.sphere.sdk.client;

import io.sphere.sdk.http.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

final class ApacheHttpClientAdapterImpl implements ApacheHttpClientAdapter {
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
    public void close() {
        try {
            apacheHttpClient.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public CompletionStage<HttpResponse> execute(final HttpRequest httpRequest) {
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
        final Map<String, List<Header>> apacheHeaders = asList(apacheResponse.getAllHeaders()).stream()
                .collect(Collectors.groupingBy(Header::getName));
        final Function<Map.Entry<String, List<Header>>, String> keyMapper = e -> e.getKey();
        final Map<String, List<String>> headers = apacheHeaders.entrySet().stream()
                .collect(Collectors.toMap(
                                keyMapper,
                        e -> e.getValue().stream().map(Header::getValue).collect(Collectors.toList())
                        )
                );

        return HttpResponse.of(statusCode, bodyOption, Optional.of(httpRequest), HttpHeaders.of(headers));
    }

    private HttpUriRequest toApacheRequest(final HttpRequest httpRequest) {
        final String method = httpRequest.getHttpMethod().toString();
        final String uri = httpRequest.getUrl();
        final RequestBuilder builder = RequestBuilder
                .create(method)
                .setUri(uri);
        httpRequest.getHeaders().getHeadersAsMap().forEach((name, values) -> values.forEach(value -> builder.addHeader(name, value)));

        if (httpRequest.getBody().isPresent()) {
            final HttpRequestBody body = httpRequest.getBody().get();
            final HttpEntity httpEntity;
            if (body instanceof StringHttpRequestBody) {
                httpEntity = stringEntityOf(((StringHttpRequestBody) body).getString());
            } else if (body instanceof FileHttpRequestBody) {
                httpEntity = new FileEntity(((FileHttpRequestBody)body).getFile());
            } else if (body instanceof FormUrlEncodedHttpRequestBody) {
                httpEntity = urlEncodedOf((FormUrlEncodedHttpRequestBody) body);
            } else {
                throw new HttpException("Cannot interpret request " + httpRequest);
            }
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
