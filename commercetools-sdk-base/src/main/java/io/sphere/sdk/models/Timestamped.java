package io.sphere.sdk.models;


import java.time.ZonedDateTime;

public interface Timestamped extends CreationTimestamped {
    @Override
    ZonedDateTime getCreatedAt();

    ZonedDateTime getLastModifiedAt();
}
