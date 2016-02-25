package io.sphere.sdk.client.metrics;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientDecorator;
import io.sphere.sdk.client.SphereRequest;

import java.util.Observable;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A decorator for {@link SphereClient}s which collects the time of serialization from and to JSON
 * as well as the time waiting for the response of the commercetools platform.
 *
 * {@include.example io.sphere.sdk.client.metrics.SimpleMetricsSphereClientDemo}
 */
public final class SimpleMetricsSphereClient extends SphereClientDecorator implements SphereClient {
    private final AtomicLong requestIdGenerator = new AtomicLong(0);
    private final Observable metricObservable = new MetricObservable();

    private SimpleMetricsSphereClient(final SphereClient delegate) {
        super(delegate);
    }

    public static SimpleMetricsSphereClient of(final SphereClient delegate) {
        return new SimpleMetricsSphereClient(delegate);
    }

    @Override
    public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
        final String id = "" + requestIdGenerator.incrementAndGet();
        final long startTimestamp = System.currentTimeMillis();
        final MetricSphereRequest<T> metricSphereRequest = new MetricSphereRequest<>(sphereRequest, id, metricObservable);
        final CompletionStage<T> completionStage = super.execute(metricSphereRequest);
        completionStage.whenComplete((nullableResult, nullableThrowable) -> {
            final long stopTimestamp = System.currentTimeMillis();
            final long duration = stopTimestamp - startTimestamp;
            metricObservable.notifyObservers(ObservedTotalDuration.of(duration, id, sphereRequest, metricSphereRequest.getCorrelationId(), nullableResult, nullableThrowable));
        });
        return completionStage;
    }

    /**
     * The observable where observers can be registered.
     * @return observable
     */
    public Observable getMetricObservable() {
        return metricObservable;
    }
}
