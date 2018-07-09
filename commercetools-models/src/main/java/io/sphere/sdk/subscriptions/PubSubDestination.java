package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;

/**
 * Google Cloud Pub/Sub Destination can be used both as a pull-queue, and to push messages to e.g. Google Cloud Functions or HTTP endpoints (webhooks).
 */
@JsonDeserialize(as = PubSubDestinationImpl.class)
@ResourceValue
public interface PubSubDestination extends Destination {

    /**
     * @return The Id of the project that contains the Pub/Sub topic
     */
    String getProjectId();

    /**
     *
     * @return Name of the topic
     */
    String getTopic();


    static PubSubDestination of(final String projectId, final String topic){
        return new PubSubDestinationImpl(projectId, topic, "GoogleCloudPubSub");
    }
}
