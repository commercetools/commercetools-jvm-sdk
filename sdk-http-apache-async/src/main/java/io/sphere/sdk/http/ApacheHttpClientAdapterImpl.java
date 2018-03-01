package io.sphere.sdk.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import static java.util.Arrays.asList;

final class ApacheHttpClientAdapterImpl extends HttpClientAdapterBase {
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
    protected void closeDelegate() throws IOException {
        apacheHttpClient.close();
    }

    @Override
    protected CompletionStage<HttpResponse> executeDelegate(final HttpRequest httpRequest) throws Throwable {
        final HttpUriRequest realHttpRequest = toApacheRequest(httpRequest);
        final CompletableFuture<org.apache.http.HttpResponse> apacheResponseFuture = new CompletableFuture<>();
        apacheHttpClient.execute(realHttpRequest, new CompletableFutureCallbackAdapter<>(apacheResponseFuture));
        return apacheResponseFuture.thenApply(apacheResponse -> convertApacheToSphereResponse(apacheResponse, httpRequest));
    }

    private HttpResponse convertApacheToSphereResponse(final org.apache.http.HttpResponse apacheResponse, final HttpRequest httpRequest) {
        final byte[] bodyNullable = Optional.ofNullable(apacheResponse.getEntity())
                .map((HttpEntity entity) -> {
                    try {
                        final boolean gzipEncoded =
                                Optional.ofNullable(apacheResponse.getFirstHeader(HttpHeaders.CONTENT_ENCODING))
                                .map(Header::getValue)
                                .map(v -> v.equalsIgnoreCase("gzip"))
                                .orElse(false);
                        final InputStream content = gzipEncoded ? new GZIPInputStream(entity.getContent()): entity.getContent();
                        final AutoCloseInputStream autoCloseInputStream = new AutoCloseInputStream(content);
                        final byte[] bytes = IOUtils.toByteArray(autoCloseInputStream);
                        return bytes;
                    } catch (final IOException e) {
                        throw new HttpException(e);
                    }
                }).orElse(null);
        final Integer statusCode = apacheResponse.getStatusLine().getStatusCode();
        final Map<String, List<Header>> apacheHeaders = asList(apacheResponse.getAllHeaders()).stream()
                .collect(Collectors.groupingBy(Header::getName));
        final Function<Map.Entry<String, List<Header>>, String> keyMapper = e -> e.getKey();
        final Map<String, List<String>> headers = apacheHeaders.entrySet().stream()
                .collect(Collectors.toMap(
                                keyMapper,
                        e -> e.getValue().stream().map(Header::getValue).collect(Collectors.toList())
                        )
                );

        return HttpResponse.of(statusCode, bodyNullable, httpRequest, HttpHeaders.of(headers));
    }

    private HttpUriRequest toApacheRequest(final HttpRequest httpRequest) throws UnsupportedEncodingException {
        final String method = httpRequest.getHttpMethod().toString();
        final String uri = httpRequest.getUrl();
        final RequestBuilder builder = RequestBuilder
                .create(method)
                .setUri(uri);
        httpRequest.getHeaders().getHeadersAsMap().forEach((name, values) -> values.forEach(value -> builder.addHeader(name, value)));

        if (httpRequest.getBody() != null) {
            final HttpRequestBody body = httpRequest.getBody();
            final HttpEntity httpEntity;
            if (body instanceof StringHttpRequestBody) {
                final StringEntity stringEntity = new StringEntity(((StringHttpRequestBody) body).getString(), StandardCharsets.UTF_8);
                stringEntity.setContentType(ContentType.APPLICATION_JSON.toString());
                httpEntity = stringEntity;

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

    private static HttpEntity urlEncodedOf(final FormUrlEncodedHttpRequestBody body) throws UnsupportedEncodingException {
        final List<BasicNameValuePair> values = body.getParameters()
                .stream()
                .map(entry -> new BasicNameValuePair(entry.getName(), entry.getValue()))
                .collect(Collectors.toList());
        return new UrlEncodedFormEntity(values);
    }

    @Nullable
    @Override
    public String getUserAgent() {
        return "Apache-CloseableHttpAsyncClient/unkown";
    }
}
