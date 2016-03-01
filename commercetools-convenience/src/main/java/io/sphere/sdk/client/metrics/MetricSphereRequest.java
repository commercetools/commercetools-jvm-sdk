package io.sphere.sdk.client.metrics;

import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.client.SphereRequestDecorator;
import io.sphere.sdk.http.HttpResponse;

import javax.annotation.Nullable;
import java.util.Observable;

final class MetricSphereRequest<T> extends SphereRequestDecorator<T> {
    private final String id;
    private final Observable observable;
    @Nullable
    private String correlationId = null;

    MetricSphereRequest(final SphereRequest<T> delegate, final String id, final Observable observable) {
        super(delegate);
        this.id = id;
        this.observable = observable;
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        final long startTimestamp = System.currentTimeMillis();
        final HttpRequestIntent result = super.httpRequestIntent();
        final long stopTimestamp = System.currentTimeMillis();
        final long duration = stopTimestamp - startTimestamp;
        observable.notifyObservers(ObservedSerializationDuration.of(duration, id, delegate));
        return result;
    }

    @Nullable
    @Override
    public T deserialize(final HttpResponse httpResponse) {
        final long startTimestamp = System.currentTimeMillis();
        final T result = super.deserialize(httpResponse);
        final long stopTimestamp = System.currentTimeMillis();
        final long duration = stopTimestamp - startTimestamp;
        correlationId = httpResponse.getHeaders().findFlatHeader("X-Correlation-ID").orElse(null);
        observable.notifyObservers(ObservedDeserializationDuration.of(duration, id, delegate, correlationId, httpResponse, result));
        return result;
    }

    @Nullable
    String getCorrelationId() {
        return correlationId;
    }
}
