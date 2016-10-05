package io.sphere.sdk.models;

import javax.annotation.Nullable;

public final class AssetSourceBuilder extends Base implements Builder<AssetSource> {
    private String uri;
    @Nullable
    private String key;
    @Nullable
    private AssetDimensions dimensions;
    @Nullable
    private String contentType;

    AssetSourceBuilder(final String uri, @Nullable final String key, @Nullable final AssetDimensions dimensions, @Nullable final String contentType) {
        this.uri = uri;
        this.key = key;
        this.dimensions = dimensions;
        this.contentType = contentType;
    }

    public static AssetSourceBuilder ofUri(final String uri) {
        return new AssetSourceBuilder(uri, null, null, null);
    }

    public AssetSourceBuilder key(@Nullable final String key) {
        this.key = key;
        return this;
    }

    public AssetSourceBuilder dimensions(@Nullable final AssetDimensions dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public AssetSourceBuilder dimensionsOfWidthAndHeight(final Integer width, final Integer height) {
        return dimensions(AssetDimensions.ofWidthAndHeight(width, height));
    }

    public AssetSourceBuilder contentType(@Nullable final String contentType) {
        this.contentType = contentType;
        return this;
    }

    @Override
    public AssetSource build() {
        return new AssetSourceImpl(uri, key, dimensions, contentType);
    }
}
