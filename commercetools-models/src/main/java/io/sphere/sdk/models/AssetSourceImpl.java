package io.sphere.sdk.models;

import javax.annotation.Nullable;

final class AssetSourceImpl extends Base implements AssetSource {
    private final String uri;
    @Nullable
    private final String key;
    @Nullable
    private final AssetDimensions dimensions;
    @Nullable
    private final String contentType;

    AssetSourceImpl(final String uri, @Nullable final String key, @Nullable final AssetDimensions dimensions, @Nullable final String contentType) {
        this.uri = uri;
        this.key = key;
        this.dimensions = dimensions;
        this.contentType = contentType;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    @Nullable
    public String getKey() {
        return key;
    }

    @Override
    @Nullable
    public AssetDimensions getDimensions() {
        return dimensions;
    }

    @Override
    @Nullable
    public String getContentType() {
        return contentType;
    }
}
