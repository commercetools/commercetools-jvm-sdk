package io.sphere.sdk.models;


import java.time.Instant;

public interface Timestamped extends CreationTimestamped {
    @Override
    Instant getCreatedAt();

    Instant getLastModifiedAt();
}
