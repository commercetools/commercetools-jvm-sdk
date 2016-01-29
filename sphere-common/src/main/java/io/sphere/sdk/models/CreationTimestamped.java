package io.sphere.sdk.models;

import java.time.ZonedDateTime;

/**
 * Interface for objects which have a timestamp for their creation time.
 */
public interface CreationTimestamped {
    ZonedDateTime getCreatedAt();
}
