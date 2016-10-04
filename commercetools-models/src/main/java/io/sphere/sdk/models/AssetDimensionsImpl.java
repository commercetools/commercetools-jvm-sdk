package io.sphere.sdk.models;

final class AssetDimensionsImpl extends Base implements AssetDimensions {
    private final Integer w;
    private final Integer h;

    AssetDimensionsImpl(final Integer w, final Integer h) {
        this.w = w;
        this.h = h;
    }

    @Override
    public Integer getW() {
        return w;
    }

    @Override
    public Integer getH() {
        return h;
    }

}
