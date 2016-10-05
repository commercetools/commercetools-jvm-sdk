package io.sphere.sdk.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;

/**
 * An AssetSource is a representation of an {@link Asset} in a specific format, e.g. a video in a certain encoding, or an image in a certain resolution.
 *
 * @see AssetSourceBuilder
 */
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
