package io.sphere.sdk.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.AutoCloseInputStream;
import org.apache.hc.client5.http.async.methods.*;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.nio.AsyncRequestProducer;
import org.apache.hc.core5.http.nio.entity.AsyncEntityProducers;
import org.apache.hc.core5.http.nio.support.AsyncRequestBuilder;
import org.apache.hc.core5.net.WWWFormCodec;
import org.apache.hc.core5.reactor.IOReactorStatus;

import javax.annotation.Nullable;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
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
        if (!(apacheHttpClient.getStatus() == IOReactorStatus.ACTIVE)) {
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
        final CompletableFuture<SimpleHttpResponse> apacheResponseFuture = new CompletableFuture<>();
        apacheHttpClient.execute(toApacheRequest(httpRequest), SimpleResponseConsumer.create(), new CompletableFutureCallbackAdapter<>(apacheResponseFuture));
        return apacheResponseFuture.thenApply(apacheResponse -> convertApacheToSphereResponse(apacheResponse, httpRequest));
    }

    private HttpResponse convertApacheToSphereResponse(final SimpleHttpResponse apacheResponse, final HttpRequest httpRequest) {

        final byte[] bodyNullable = Optional.ofNullable(apacheResponse.getBody())
                .map((SimpleBody entity) -> {
                    try {
                        final boolean gzipEncoded =
                                Optional.ofNullable(apacheResponse.getFirstHeader(HttpHeaders.CONTENT_ENCODING))
                                .map(Header::getValue)
                                .map(v -> v.equalsIgnoreCase("gzip"))
                                .orElse(false);
                        final InputStream body = new ByteArrayInputStream(entity.getBodyBytes());
                        final InputStream content = gzipEncoded ? new GZIPInputStream(body): body;
                        final AutoCloseInputStream autoCloseInputStream = new AutoCloseInputStream(content);
                        final byte[] bytes = IOUtils.toByteArray(autoCloseInputStream);
                        return bytes;
                    } catch (final IOException e) {
                        throw new HttpException(e);
                    }
                }).orElse(null);
        final Integer statusCode = apacheResponse.getCode();
        final Map<String, List<Header>> apacheHeaders = Arrays.stream(apacheResponse.getHeaders())
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

    private AsyncRequestProducer toApacheRequest(final HttpRequest httpRequest) {
        final String method = httpRequest.getHttpMethod().toString();
        final String uri = httpRequest.getUrl();
        final AsyncRequestBuilder builder = AsyncRequestBuilder.create(method);
        builder.setUri(uri);
        httpRequest.getHeaders().getHeadersAsMap().forEach((name, values) -> values.forEach(value -> builder.addHeader(name, value)));

        if (httpRequest.getBody() != null) {
            final HttpRequestBody body = httpRequest.getBody();
            if (body instanceof StringHttpRequestBody) {
                builder.setEntity(((StringHttpRequestBody) body).getString(), ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8));
            } else if (body instanceof FileHttpRequestBody) {
                builder.setEntity(AsyncEntityProducers.create(((FileHttpRequestBody)body).getFile(), ContentType.DEFAULT_BINARY));
            } else if (body instanceof FormUrlEncodedHttpRequestBody) {
                builder.setEntity(urlEncodedOf((FormUrlEncodedHttpRequestBody) body), ContentType.APPLICATION_FORM_URLENCODED);
            } else {
                throw new HttpException("Cannot interpret request " + httpRequest);
            }
        }
        return builder.build();
    }

    private static String urlEncodedOf(final FormUrlEncodedHttpRequestBody body) {
        final List<BasicNameValuePair> values = body.getParameters()
                                                    .stream()
                                                    .map(entry -> new BasicNameValuePair(entry.getName(), entry.getValue()))
                                                    .collect(Collectors.toList());
        return WWWFormCodec.format(values, ContentType.APPLICATION_FORM_URLENCODED.getCharset());
    }

    @Nullable
    @Override
    public String getUserAgent() {
        return "Apache-CloseableHttpAsyncClient/unknown";
    }
}
