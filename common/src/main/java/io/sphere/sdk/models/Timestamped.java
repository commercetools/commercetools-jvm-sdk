package io.sphere.sdk.models;

import org.joda.time.DateTime;

public interface Timestamped {
    DateTime getCreatedAt();

    DateTime getLastModifiedAt();
}
