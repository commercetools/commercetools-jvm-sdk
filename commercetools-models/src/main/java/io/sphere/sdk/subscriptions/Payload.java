package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.projects.Project;

import javax.annotation.Nullable;

/**
 * Common interface for all subscription payloads.
 *
 * @param <T> the resource type
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "notificationType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageSubscriptionPayload.class, name  = "Message"),
        @JsonSubTypes.Type(value = ResourceCreatedPayload.class, name  = "ResourceCreated"),
        @JsonSubTypes.Type(value = ResourceUpdatedPayload.class, name  = "ResourceUpdated"),
        @JsonSubTypes.Type(value = ResourceDeletedPayload.class, name  = "ResourceDeleted")})
@ResourceValue
public interface Payload<T> {
    /**
     * The key of the project. Useful if the same queue is filled from multiple projects.
     *
     * @return the project key {@link Project#getKey()}
     */
    String getProjectKey();

    /**
     * Identifies the payload.
     *
     * @return the payload type
     */
    String getNotificationType();

    /**
     * A reference to the resource that triggered this delivery.
     *
     * @return the reference to the resource that triggered this delivery
     */
    Reference<T> getResource();

    @Nullable
    UserProvidedIdentifiers getResourceUserProvidedIdentifiers();
}
