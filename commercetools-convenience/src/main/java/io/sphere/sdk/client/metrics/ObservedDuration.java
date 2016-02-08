package io.sphere.sdk.client.metrics;

import io.sphere.sdk.client.SphereRequest;

/**
 * Contains duration information for a particular aspect of one execution of a {@link SimpleMetricsSphereClient#execute(SphereRequest)}.
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
     * The {@link SimpleMetricsSphereClient} adds a request ID to each {@link SphereRequest}, so that {@link ObservedDuration}s belonging to the same request can be associated..
     * <p>This is necessary since a {@link SphereRequest} can be reused for multiple requests.</p>
     * @return ID for one execution of the client
     */
    String getRequestId();

    /**
     * A String to describe the topic associated with the observed duration (e.g. "ObservedSerializationDuration" for the deserialization of JSON).
     * @return topic
     */
    String getTopic();

    /**
     * The sphere request related to the observed duration.
     * @return request
     */
    SphereRequest<?> getRequest();
}
