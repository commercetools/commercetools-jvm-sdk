package io.sphere.sdk.models;


import java.time.Instant;

public interface Timestamped extends CreationTimestamped {
    Instant getLastModifiedAt();
}
