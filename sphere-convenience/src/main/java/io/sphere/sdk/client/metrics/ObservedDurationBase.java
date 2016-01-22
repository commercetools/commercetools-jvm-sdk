package io.sphere.sdk.client.metrics;

import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.models.Base;

abstract class ObservedDurationBase extends Base implements ObservedDuration {
    protected final String requestId;
    protected final Long durationInMilliseconds;
    protected final SphereRequest<?> request;

    public ObservedDurationBase(final String requestId, final SphereRequest<?> request, final long durationInMilliseconds) {
        this.requestId = requestId;
        this.request = request;
        this.durationInMilliseconds = durationInMilliseconds;
    }

    public Long getDurationInMilliseconds() {
        return durationInMilliseconds;
    }

    public String getRequestId() {
        return requestId;
    }

    public SphereRequest<?> getRequest() {
        return request;
    }

    @Override
    public final String getTopic() {
        return getClass().getSimpleName();
    }
}
