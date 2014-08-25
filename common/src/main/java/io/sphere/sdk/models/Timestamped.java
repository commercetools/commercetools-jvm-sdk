package io.sphere.sdk.models;


import java.time.Instant;

public interface Timestamped {
    Instant getCreatedAt();

    Instant getLastModifiedAt();
}
