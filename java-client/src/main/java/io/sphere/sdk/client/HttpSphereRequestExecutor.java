package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.typesafe.config.Config;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.utils.JsonUtils;
import io.sphere.sdk.logging.Log;

public class HttpSphereRequestExecutor implements SphereRequestExecutor {
    private static final TypeReference<SphereErrorResponse> errorResponseJsonTypeRef = new TypeReference<SphereErrorResponse>() {
    };
    private final ObjectMapper objectMapper = JsonUtils.newObjectMapper();
    private final HttpClient requestExecutor;
    private final Config config;

    public HttpSphereRequestExecutor(final HttpClient httpClient, final Config config) {
        this.requestExecutor = httpClient;
        this.config = config;
    }

    @Override
    public <I, R> ListenableFuture<Optional<I>> execute(final Fetch<I, R> fetch) {
        final ListenableFuture<HttpResponse> future = requestExecutor.execute(fetch);
        return Futures.transform(future, new Function<HttpResponse, Optional<I>>() {
            @Override
            public Optional<I> apply(final HttpResponse httpResponse) {
                final SphereResultRaw<I> sphereResultRaw = requestToSphereResult(httpResponse, fetch, fetch.typeReference());
                Optional<I> result = Optional.absent();
                if (sphereResultRaw.isSuccess()) {
                    result = Optional.of(sphereResultRaw.getValue());
                }
                return result;
            }
        });
    }

    @Override
    public void close() {
        requestExecutor.close();
    }

    @Override
    public <I, R> ListenableFuture<PagedQueryResult<I>> execute(final Query<I, R> query) {
        final ListenableFuture<HttpResponse> future = requestExecutor.execute(query);
        return Futures.transform(future, new Function<HttpResponse, PagedQueryResult<I>>() {
            @Override
            public PagedQueryResult<I> apply(final HttpResponse httpResponse) {
                final SphereResultRaw<PagedQueryResult<I>> sphereResultRaw = requestToSphereResult(httpResponse, query, query.typeReference());
                if (sphereResultRaw.isError()) {
                    throw new RuntimeException(sphereResultRaw.getError());//TODO
                }
                return sphereResultRaw.getValue();
            }
        });
    }

    @Override
    public <I, R> ListenableFuture<I> execute(final Command<I, R> command) {
        final ListenableFuture<HttpResponse> future = requestExecutor.execute(command);
        return Futures.transform(future, new Function<HttpResponse, I>() {
            @Override
            public I apply(final HttpResponse httpResponse) {
                final SphereResultRaw<I> sphereResultRaw = requestToSphereResult(httpResponse, command, command.typeReference());
                return sphereResultRaw.getValue();
            }
        });
    }

    //package scope for testing
    <I, R> SphereResultRaw<I> requestToSphereResult(final HttpResponse httpResponse, final Requestable requestable,
                                                    final TypeReference<R> typeReference) {
        final int status = httpResponse.getStatusCode();
        final String body = httpResponse.getResponseBody();
        final boolean hasError = status / 100 != 2;
        if (hasError) {
            SphereErrorResponse errorResponse;
            try {
                errorResponse = objectMapper.readValue(body, errorResponseJsonTypeRef);
            } catch (final Exception e) {
                // This can only happen when the backend and SDK don't match.

                final SphereException exception = new SphereException("Can't parse backend response", e);
                exception.setSphereRequest(requestable.toString());
                exception.setUnderlyingHttpRequest(requestable.httpRequest().toString());
                exception.setUnderlyingHttpResponse(httpResponse.toString());
                exception.setProjectKey(config.getString("sphere.project"));
                throw exception;
            }
            if ((status >= 400 && status < 500) && Log.isDebugEnabled()) {
                Log.debug(errorResponse + "\n\nRequest: " + requestable);
            } else if (status >= 500) {
                Log.error(errorResponse + "\n\nRequest: " + requestable);
            }
            return SphereResultRaw.<I>error(new SphereBackendException(requestable.httpRequest().getPath(), errorResponse));
        } else {
            try {
                if (Log.isTraceEnabled()) {

                    Log.trace(requestable + "\n=> " + httpResponse.getStatusCode() + "\n" + JsonUtils.prettyPrintJsonStringSecure(body) + "\n");
                } else if (Log.isDebugEnabled()) {
                    Log.debug(requestable.toString());
                }
                return new SphereResultRaw<>(objectMapper.<I>readValue(body, typeReference), null);
            } catch (final Exception e) {
                throw new RuntimeException(e);//TODO
            }
        }
    }
}