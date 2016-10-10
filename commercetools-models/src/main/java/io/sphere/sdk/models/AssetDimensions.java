package io.sphere.sdk.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = AssetDimensionsImpl.class)
public interface AssetDimensions {
    Integer getW();

    Integer getH();

    default Integer getWidth() {
        return getW();
    }

    default Integer getHeight() {
        return getH();
    }

    static AssetDimensions ofWidthAndHeight(final Integer width, final Integer height) {
        return new AssetDimensionsImpl(width, height);
    }
}
