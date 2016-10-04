package io.sphere.sdk.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;

@JsonDeserialize(as = AssetSourceImpl.class)
public interface AssetSource {
    String getUri();

    @Nullable
    String getKey();

    @Nullable
    AssetDimensions getDimensions();

    @Nullable
    String getContentType();
}
