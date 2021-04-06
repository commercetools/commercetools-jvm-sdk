package io.sphere.sdk.models;

import javax.annotation.Nullable;

public interface WithClientLogging {
    @Nullable
    LastModifiedBy getLastModifiedBy();
    @Nullable
    CreatedBy getCreatedBy();
}
