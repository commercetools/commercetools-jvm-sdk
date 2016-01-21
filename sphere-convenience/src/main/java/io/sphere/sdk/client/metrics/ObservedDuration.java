package io.sphere.sdk.client.metrics;

import io.sphere.sdk.client.SphereRequest;

/**
 * Contains duration information for one aspect for one execution of a {@link SimpleMetricsSphereClient#execute(SphereRequest)}.
 *
 * @see SimpleMetricsSphereClient
 */
public interface ObservedDuration {
    /**
     * The duration in milliseconds.
     * @return duration
     */
    Long getDurationInMilliseconds();

    /**
     * The {@link SimpleMetricsSphereClient} adds a request ID to each {@link SphereRequest} so that matching {@link ObservedDuration}s can be matched.
     * <p>This is necessary since a {@link SphereRequest} can be reused for multiple requests.</p>
     * @return ID for one execution of the client
     */
    String getRequestId();

    /**
     * A String for which topic (for example the deserialization of JSON) the duration is.
     * @return topic
     */
    String topic();

    /**
     * The sphere request which the duration is related to.
     * @return request
     */
    SphereRequest<?> getRequest();
}
